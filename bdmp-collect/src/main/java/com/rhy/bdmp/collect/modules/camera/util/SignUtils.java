package com.rhy.bdmp.collect.modules.camera.util;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class SignUtils {
    /**
     * 生成签名（SHA256）
     *
     * @param data   待签名数据
     * @param appKey API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, Object> data, String appKey) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if ("sign".equals(k) ||data.get(k) == null) {
                continue;
            }
            if (data.get(k) instanceof String) {
                // 参数值为空，则不参与签名
                if (((String)data.get(k)).trim().length() > 0) {
                    sb.append(k).append("=").append(((String)data.get(k)).trim()).append("&");
                }
            }
        }
        sb.append("key=").append(appKey);
        return SHA256Utils.getSHA256(sb.toString());
    }

    /**
     * 生成sy的sign
     * @param params 发送的请求参数，为空不参与
     * @param key 账号的key
     */
    public static String generateSignatureSY(final Map<String, Object> params, String key) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (null != value){
                keys.add(entry.getKey());
            }
        }
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();

        if (CollUtil.isNotEmpty(keys)){
            for (String mapKey : keys) {
                Object value = params.get(mapKey);
                sb.append(mapKey);
                sb.append("=");
                sb.append(value);
                sb.append("&");
            }
        }

        sb.append("key=").append(key);
        return SHA256Utils.getSHA256(sb.toString());
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
