package com.dqcer.dxpprovider.sso.web.controller;

import org.springframework.core.convert.converter.Converter;

//@Component
public   class DateConverter implements Converter<String, String> {
    @Override
    public String convert(String s) {
        System.out.println("dddddddddddddd");
//        return StringUtils.parseTimeZoneString(s);
        return s + "dddd";
    }
}