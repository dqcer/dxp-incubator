package com.dqcer.demo.dxpdemoliteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

@TestPropertySource(value = "classpath:application.properties")
@SpringBootTest(classes = DxpDemoLiteflowApplication.class)
@EnableAutoConfiguration
class DxpDemoLiteflowApplicationTests {

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    public void testConfig(){
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
    }

    @Test
    void contextLoads() {
    }

}
