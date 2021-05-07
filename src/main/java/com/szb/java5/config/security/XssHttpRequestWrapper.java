package com.szb.java5.config.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author 石致彬
 * @since 2021-04-22 20:39
 */
public class XssHttpRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    public XssHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request=request;
    }

    @Override
    public String getParameter(String name) {
        String value = request.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String parameterValue = parameterValues[i];
            parameterValues[i] = StringEscapeUtils.escapeHtml4(parameterValue);
        }
        return parameterValues;
    }
}
