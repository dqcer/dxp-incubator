package com.dqcer.integration.idempotent.aspect;

import com.dqcer.dxptools.core.StrUtil;
import com.dqcer.integration.idempotent.annotation.ApiIdempotent;
import com.dqcer.integration.idempotent.service.ApiIdempotentService;
import com.dqcer.integration.idempotent.util.HttpContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dongqin
 * @description api幂等
 * @date 2021/08/19 23:08:36
 */
@Aspect
public class ApiIdempotentAspect {

	private static final Logger log = LoggerFactory.getLogger(ApiIdempotentAspect.class);

	private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

	private ThreadLocal<ApiIdempotent> IDEMPOTENT_ANNOTATION = new ThreadLocal<>();

	private static final String API_IDEMPOTENT = "api_idempotent";

	private static final String KEY = "key";

	private static final String DEL_KEY = "delKey";

	@Resource
	private Redisson redisson;

	@Resource
	private KeyResolver keyResolver;

	@Resource
	private ApiIdempotentService apiIdempotentService;

	@Pointcut("@annotation(com.dqcer.integration.idempotent.annotation.ApiIdempotent)")
	public void pointCut() {
	}

	/**
	 * 执行之前
	 *
	 * @param joinPoint 连接点
	 * @throws Exception 异常
	 */
	@Before("pointCut()")
	public void beforePointCut(JoinPoint joinPoint) throws Exception {

		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (!method.isAnnotationPresent(ApiIdempotent.class)) {
			return;
		}

		ApiIdempotent idempotent = method.getAnnotation(ApiIdempotent.class);
		IDEMPOTENT_ANNOTATION.set(idempotent);

		boolean tokenModel = idempotent.validToken();

		if (tokenModel) {
			assert request != null;
			tokenModal(request);
		}

		String key = getKey(idempotent, request, joinPoint);

		long expireTime = idempotent.expires();
		boolean delKey = idempotent.delKey();

		RMapCache<String, Object> rMapCache = redisson.getMapCache(API_IDEMPOTENT);
		String value = LocalDateTime.now().toString().replace("T", " ");
		Object object;
		if (null != rMapCache.get(key)) {
			throw new IdempotentException("努力响应中，请稍后...");
		}

		synchronized (this) {
			object = rMapCache.putIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
			if (null != object) {
				throw new IdempotentException("努力响应中，请稍后...");
			}
			else {
				if (log.isInfoEnabled()) {
					log.info("Api idempotent: has stored key={},value={},expireTime={}{},now={}", key, value,
							expireTime, TimeUnit.SECONDS, LocalDateTime.now());
				}
			}
		}

		Map<String, Object> map = CollectionUtils.isEmpty(threadLocal.get()) ? new HashMap<>(4) : threadLocal.get();
		map.put(KEY, key);
		map.put(DEL_KEY, delKey);
		threadLocal.set(map);

	}

	private void tokenModal(HttpServletRequest request) throws IdempotentException {
		String token = request.getHeader(ApiIdempotentService.API_IDEMPOTENT_TOKEN);
		if (StrUtil.isBlank(token)) {
			throw new IdempotentException("参数缺失...");
		}

		boolean existByToken = apiIdempotentService.existByToken(token);
		if (!existByToken) {
			throw new IdempotentException("参数缺失...");
		}
	}

	private String getKey(ApiIdempotent idempotent, HttpServletRequest request, JoinPoint joinPoint) {
		return keyResolver.resolver(idempotent, joinPoint);
	}

	@After("pointCut()")
	public void afterPointCut() {
		ApiIdempotent apiIdempotent = IDEMPOTENT_ANNOTATION.get();

		if (apiIdempotent.validToken()) {
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			assert request != null;
			String token = request.getHeader(ApiIdempotentService.API_IDEMPOTENT_TOKEN);
			apiIdempotentService.removeToken(token);
			IDEMPOTENT_ANNOTATION.remove();
			return;
		}


		Map<String, Object> map = threadLocal.get();
		if (CollectionUtils.isEmpty(map)) {
			return;
		}

		RMapCache<Object, Object> mapCache = redisson.getMapCache(API_IDEMPOTENT);
		if (mapCache.size() == 0) {
			return;
		}

		String key = map.get(KEY).toString();
		boolean delKey = (boolean) map.get(DEL_KEY);

		if (delKey) {
			mapCache.fastRemove(key);
			if (log.isInfoEnabled()) {

				log.info("Api idempotent:has removed key={}", key);
			}
		}
		threadLocal.remove();
	}

}
