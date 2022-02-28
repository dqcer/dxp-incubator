package com.dqcer.gateway.properties;

import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongqin
 * @description 忽略path
 * @date 2022/01/13
 */
public class IgnorePathProperties {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 不需要token验证的接口
     */
    private List<String> token = new ArrayList<>();

    public List<String> getToken() {
        return token;
    }

    public void setToken(List<String> token) {
        this.token = token;
    }

    /**
     * 忽略token
     *
     * @param path 路径
     * @return boolean
     */
    public boolean isIgnoreToken(String path) {
        List<String> all = new ArrayList<>();
        all.addAll(getToken());
        return all.stream().anyMatch(url -> path.startsWith(url) || PATH_MATCHER.match(url, path));
    }
}
