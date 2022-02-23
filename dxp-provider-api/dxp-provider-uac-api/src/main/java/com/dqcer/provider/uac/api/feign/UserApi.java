package com.dqcer.provider.uac.api.feign;

import com.dqcer.dxpframework.api.Result;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author dongqin
 * @description 用户api
 * @date 2021/11/22
 */
public interface UserApi {

    @PostMapping("/auth/token")
    Result tenantUsers();

}
