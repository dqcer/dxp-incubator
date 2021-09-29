package com.dqcer.integration.jwt.core;

public class TokenUtil {

    public static TokenLogic tokenLogic = new TokenLogic();


    public void login(Long id) {
        tokenLogic.login(id);
    }
}
