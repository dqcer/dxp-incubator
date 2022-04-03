package com.dqcer.provider.uac.api.feign;

import com.dqcer.framework.base.bean.Result;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author dongqin
 * @description 用户api
 * @date 2021/11/22
 */
public interface UserApi {

    /**
     * 租户用户
     *
     * @return {@link Result}
     */
    @PostMapping("/auth/token")
    Result tenantUsers();

}
