package com.basebackend.base_backend.auth.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private static final Pattern[] XSS_PATTERNS = {
        Pattern.compile("<.*?>"),
        Pattern.compile("&.*?;"),
        Pattern.compile("%.*?%"),
        Pattern.compile("script", Pattern.CASE_INSENSITIVE),
        Pattern.compile("alert", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onerror", Pattern.CASE_INSENSITIVE),
        Pattern.compile("onload", Pattern.CASE_INSENSITIVE),
        Pattern.compile("eval\\(.*\\)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("expression\\(.*\\)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        Pattern.compile("data:", Pattern.CASE_INSENSITIVE)
    };

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }

        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    private String stripXSS(String value) {
        if (value == null) {
            return null;
        }

        value = value.replaceAll("\0", "");

        for (Pattern pattern : XSS_PATTERNS) {
            value = pattern.matcher(value).replaceAll("");
        }

        return value;
    }
}
