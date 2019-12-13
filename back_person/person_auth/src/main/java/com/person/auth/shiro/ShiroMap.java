package com.person.auth.shiro;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 17:39
 */
@Component
@Configuration
public class ShiroMap {

    private static final List<String> ENABLED_URL = Arrays.asList();

    public LinkedHashMap<String, String> loadFilterMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        map.put("/api/auth/login", "anon");
        map.put("/api/auth/logout", "anon");
        for (String url : ENABLED_URL) {
            map.put(url, "anon");
        }

        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/**", "anon");
        map.put("/webjars/springfox-swagger-ui/**", "anon");
//        map.put("/**", "authc");

        return map;
    }

}
