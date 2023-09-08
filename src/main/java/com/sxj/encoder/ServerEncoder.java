package com.sxj.encoder;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.HashMap;

public class ServerEncoder implements Encoder.Text<HashMap> {
    private static final Logger log = LoggerFactory.getLogger(ServerEncoder.class);

    /**
     * 这里的参数 hashMap 要和  Encoder.Text<T>保持一致
     * @param hashMap
     * @return
     * @throws EncodeException
     */
    @Override
    public String encode(HashMap hashMap) throws EncodeException {
        /*
         * 这里是重点，只需要返回Object序列化后的json字符串就行
         * 也可以使用gosn，fastJson来序列化。
         * 此处使用fastjson
         */
        try {
            return  JSONObject.toJSONString(hashMap);
        }catch (Exception e){
            log.error("",e);
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //可忽略
    }

    @Override
    public void destroy() {
        //可忽略
    }
}

