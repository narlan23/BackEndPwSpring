package com.backend.backpw.pwapi;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {
    public static Map<String, Object> config(String data) {
        Map<String, Object> serverInf = new HashMap<>();
        Map<String, Integer> ports = new HashMap<>();
        ports.put("gamedbd", (Integer) Config.configs.get("gamedbd"));
        ports.put("gdeliveryd", (Integer) Config.configs.get("gdeliveryd"));
        ports.put("gacd", (Integer) Config.configs.get("gacd"));
        ports.put("client", (Integer) Config.configs.get("client"));

        Map<String, Object> pwApi = new HashMap<>();
        pwApi.put("local", Config.configs.get("server_ip"));
        pwApi.put("ports", ports);
        pwApi.put("game_version", Config.configs.get("versao"));
        pwApi.put("maxbuffer", 65536);
        pwApi.put("s_block", false);
        pwApi.put("s_readtype", 3);

        serverInf.put("pw-api", pwApi);

        String[] keys = data.split("\\.");
        Map<String, Object> r = serverInf;
        for (String key : keys) {
            r = (Map<String, Object>) r.get(key);
        }
        return r;
    }

    public static Object settings(String key, Object valueIfNull) {
        if (key.equals("server_version")) {
            return config("pw-api.game_version");
        } else if (key.equals("server_ip")) {
            return config("pw-api.local");
        } else {
            return valueIfNull;
        }
    }
}

