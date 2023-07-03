package com.rhy.bdmp.open.modules.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author: lipeng
 * @Date: 2021/8/2
 * @description: 全局的拦击器，解决feign调用丢失head参数问题
 * @version: 1.0
 */
@Slf4j
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    // 解决length和body不一致导致报错的问题
                    if (name.equals("content-length")){
                        continue;
                    }
                    // 解决服务端配置compression,但客户端解析json异常的问题
                    if (name.equals("accept-encoding")){
                        continue;
                    }
                    requestTemplate.header(name, value);
                }
            }
        }
//        Enumeration<String> bodyNames = request.getParameterNames();
//        StringBuffer body =new StringBuffer();
//        if (bodyNames != null) {
//            while (bodyNames.hasMoreElements()) {
//                String name = bodyNames.nextElement();
//                String values = request.getParameter(name);
//                requestTemplate.query(name, values);
//                body.append(name).append("=").append(values).append("&");
//            }
//        }
//        if(body.length()!=0) {
//            body.deleteCharAt(body.length()-1);
//            requestTemplate.body(body.toString());
//
//            log.info("feign interceptor body:{}",body.toString());
//        }
    }
}
