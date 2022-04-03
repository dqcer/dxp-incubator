package com.dqcer.integration.audit.aspect;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dqcer.framework.base.bean.Result;
import com.dqcer.integration.audit.annotation.AuditDTO;
import com.dqcer.integration.audit.annotation.AuditLog;
import com.dqcer.integration.audit.enums.TypeEnum;
import com.dqcer.integration.audit.parser.DifferentDataDTO;
import com.dqcer.integration.audit.parser.JsonDifferenceUtil;
import com.dqcer.integration.audit.AuditProvider;
import com.dqcer.integration.audit.bean.AuditLogBean;
import com.dqcer.integration.audit.bean.AuditLogDetailBean;
import com.dqcer.integration.audit.util.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author dongqin
 * @description 审计日志方面
 * @date 2021/11/18
 */
@Aspect
@Component
public class AuditLogAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 业务日志id
     */
    public static InheritableThreadLocal<Long> LOG_BUSINESS_ID = new InheritableThreadLocal<>();

    @Resource
    private AuditProvider auditProvider;


    private ExpressionParser parser = new SpelExpressionParser();

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    @Around("@annotation(com.dqcer.integration.audit.annotation.AuditLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        AuditLogBean logEntity = new AuditLogBean();
        Collection<DifferentDataDTO> diff = new HashSet<>();
        try {
            Long uid = null;
            Long studyId = null;
            String ip, language, uri, title = null, indexName = null;

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            StringBuilder stringBuilder = new StringBuilder();
            String u = request.getHeader("uid");
            if (StrUtil.isNotEmpty(u)) {
                uid = Long.parseLong(u);
            }
            String s = request.getHeader("studyId");
            if (StrUtil.isNotEmpty(s)) {
                studyId = Long.parseLong(s);
            }
            ip = IpUtils.getIpAddr(request);
            language = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
            uri = request.getRequestURI();
            Object[] args = point.getArgs();
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            final AuditLog annotation = method.getAnnotation(AuditLog.class);
            TypeEnum type = annotation.type();

            if (ObjectUtil.isNotEmpty(args)) {
                for (Object arg : args) {
                    if (ObjectUtil.isNotEmpty(arg)) {
                        boolean execute = isExecute(arg);
                        if (execute) {
                            if (arg instanceof MultipartFile) {
                                MultipartFile multipartFile = (MultipartFile) arg;
                                Map<String, Object> map = new HashMap<>(8);
                                map.put("fileName", multipartFile.getOriginalFilename());
                                map.put("size", multipartFile.getSize());
                                stringBuilder.append(JSONUtil.toJsonStr(map));
                            }
                            else {

                                JSONObject jsonObject = JSONObject.parseObject(JSONUtil.parse(arg).toString());
                                AuditDTO audit =  jsonObject.getObject("audit", AuditDTO.class);

                                log.info("audit: {}", audit);

                                if (null == audit) {
                                    throw new RuntimeException("请在对应DTO类种添加 AuditDTO 成员变量");
                                }

                                title = audit.getTitle();
                                indexName = audit.getIndexName();
                                diff = JsonDifferenceUtil.difference(jsonObject, type, new HashSet(Arrays.asList(annotation.key())), annotation.ignore());
                                stringBuilder.append(JSONUtil.parse(arg).toString());
                            }
                        }
                    }
                }
            }

            logEntity.setCreatedBy(uid);
            logEntity.setStudyId(studyId);
            logEntity.setLanguage(language);
            logEntity.setIp(ip);
            logEntity.setRequestUrl(uri);
            logEntity.setCreatedTime(new Date());
            logEntity.setParam(stringBuilder.toString());
            logEntity.setType(type.getValue());
            logEntity.setId(IdWorker.getId());
            logEntity.setModule(title);
            logEntity.setIndexName(indexName);

            result = point.proceed();
            if (result instanceof Result) {
                Result res = (Result) result;
                logEntity.setResultCode(res.getCode());
                logEntity.setResultMsg(res.getMessage());
            } else {
                Result ok = Result.ok();
                logEntity.setResultCode(ok.getCode());
                logEntity.setResultMsg(ok.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            logEntity.setEndTime(new Date());
            logEntity.setBusinessId(LOG_BUSINESS_ID.get());
            auditProvider.save(logEntity);


            List<AuditLogDetailBean> entities = new LinkedList<>();
            Long id = logEntity.getId();

            for (DifferentDataDTO dataDTO : diff) {
                AuditLogDetailBean detailEntity = new AuditLogDetailBean();
                detailEntity.setAuditId(id);
                detailEntity.setField(dataDTO.getFieldName());
                Object oldFieldValue = dataDTO.getOldFieldValue();
                detailEntity.setOldValue(null == oldFieldValue ? null : oldFieldValue.toString());
                Object newFieldValue = dataDTO.getNewFieldValue();
                detailEntity.setNewValue(null == newFieldValue ? null : newFieldValue.toString());
                entities.add(detailEntity);
            }
            auditProvider.batchSaveSub(entities);

            LOG_BUSINESS_ID.remove();
        }
        return result;
    }

    /**
     * 是否执行
     *
     * @param object 对象
     * @return boolean
     */
    public boolean isExecute(Object object) {
        if (object instanceof HttpServletRequest) {
            return false;
        }
        return !(object instanceof HttpServletResponse);
    }

    /**
     * 解析 SPEL 表达式
     *
     * @param method    方法
     * @param arguments 参数
     * @param spel      表达式
     * @param clazz     返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    private <T> T parseSPEL(Method method, Object[] arguments, String spel, Class<T> clazz, T defaultResult) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        try {
            Expression expression = parser.parseExpression(spel);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            return defaultResult;
        }
    }


}
