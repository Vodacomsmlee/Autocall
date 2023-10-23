package com.vdc.autocall.common.resolver;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class ArgumentResolver implements HandlerMethodArgumentResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return CommandMap.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        CommandMap commandMap = new CommandMap();

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Enumeration<?> enumeration = request.getParameterNames();

        String key ;
        String[] values ;
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowStandardUrlProtocols()
                .allowStyling()
                .allowCommonBlockElements()
                .allowCommonInlineFormattingElements()
                .allowElements("a")
                .allowAttributes("href").onElements("a")
                .toFactory();

        while (enumeration.hasMoreElements()) {
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            if (values != null && !key.equals("_")) {
                //values[0] XSS 및 injection 필터링 필요
                if (values[0].contains("@") || values[0].contains("!")) {
                    commandMap.put(key, XssFilter(values[0]) );
                }
                else {
                    commandMap.put(key, (values.length > 1) ? values:policy.sanitize(values[0]));
                }

                logger.debug("##### XSSParam key : "+key+", value : "+values[0]);

            }
        }
        return commandMap;
    }



    public String XssFilter(String value) {

//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); // <>
//        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;"); // ()
//        value = value.replaceAll("'", "&#39;");
//        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");

        return value;
    }
}