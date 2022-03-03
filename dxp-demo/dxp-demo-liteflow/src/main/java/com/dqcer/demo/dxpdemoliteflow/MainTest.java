package com.dqcer.demo.dxpdemoliteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MainTest {

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    public void testConfig(){
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
    }
}
