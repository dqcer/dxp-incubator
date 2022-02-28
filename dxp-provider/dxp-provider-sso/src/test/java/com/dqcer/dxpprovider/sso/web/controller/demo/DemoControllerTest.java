package com.dqcer.dxpprovider.sso.web.controller.demo;

import com.dqcer.dxpprovider.sso.SsoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.wildfly.common.Assert;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SsoApplication.class)
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postRequest() throws Exception {
        this.mockMvc.perform(post("/demo/junit")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("操作成功")));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/demo/junit"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void postRequest1() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/demo/junit"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

}