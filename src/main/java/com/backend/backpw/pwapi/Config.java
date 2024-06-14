package com.backend.backpw.pwapi;

import java.util.Map;
import java.util.HashMap;

public class Config {
    public static Map<String, Object> configs = new HashMap<>();

    static {
        configs.put("server_ip", "192.168.18.215");
        configs.put("gamedbd", 1234);
        configs.put("gdeliveryd", 5678);
        configs.put("gacd", 9101);
        configs.put("client", 1121);
        configs.put("versao", "101");
    }
}
