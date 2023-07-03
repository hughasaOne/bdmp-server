package com.rhy.bdmp.system.modules.logging.util;

import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.logging.domain.vo.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LogUtil {
    private static final String UNKNOWN = "unknown";
    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        if (null == request){
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    public static String buildUpParams(String msg, String logType,Object params){
        BaseMessage bm = new BaseMessage();
        bm.setLogType(logType);
        bm.setOperator(WebUtils.getUserId());
        bm.setOperationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bm.setParams(params);
        bm.setIp(LogUtil.getIp(LogUtil.getHttpServletRequest()));
        return msg+":"+ JSONUtil.parseObj(bm, true).toString();
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes){
            return null;
        }
        else {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
    }
}
