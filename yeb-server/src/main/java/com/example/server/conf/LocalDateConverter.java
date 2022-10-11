package com.example.server.conf;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    /**
     * java web
     * spring mvc
     * 请求 -> mvc -> 根据url地址 -> 匹配handler -> Controoler#method
     * -> 加载方法中的参数
     * /xx?data=789
     * public void test(Integer data) {
     * <p>
     * }
     * spring框架回去挑选合适的converter 将String -> Integer
     * <p>
     * （@RequestParam
     *
     * @RequestBody
     * @PathVariable
     * @RequestHeader）
     */

    @Override
    public LocalDate convert(String source) {

        /**
         * Data
         * Calander
         */
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
