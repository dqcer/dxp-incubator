package com.dqcer.integration.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author dongqin
 * @description Resource Util
 * @date 14:07 2021/5/18
 */
@Deprecated
public class ResourceUtils {

    private ResourceUtils(){}

    /**
     * common msg
     *
     * @param code code
     * @param arg  参数集
     * @return String
     */
    public static String get(String code, Object ... arg){
        String string = commonMsg(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getLocale(), code);
        return MessageFormat.format(string, arg);
    }

    /**
     * common msg
     *
     * @param code code
     * @return {@link String}
     */
    public static String get(String code){
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/SysMessages", request.getLocale());
        return bundle.getString(code);
    }

    /**
     * common msg
     *
     * @param locale Locale
     * @param code code
     * @return String
     */
    private static String commonMsg(Locale locale, String code){
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/SysMessages", locale);

        return bundle.getString(code);
    }

    /**
     * common msg
     *
     * @param throwable Throwable
     * @return String
     */
    public static String get(Throwable throwable) {
        String message = throwable.getMessage();
        if (null == message || "".equals(message)) {
            throwable.printStackTrace();
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            message = sw.toString();
        }
        return MessageFormat.format("{0},{1}", "999500", message);
    }

}
