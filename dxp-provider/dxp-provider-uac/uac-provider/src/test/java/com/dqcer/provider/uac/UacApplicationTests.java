package com.dqcer.provider.uac;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class UacApplicationTests {


    @Autowired
    private MockMvc mockMvc;


    /**
     * 单元测试get接口
     * {@see https://blog.csdn.net/wangxi06/article/details/114630426}
     *
     * @throws Exception 异常
     */
    @Test
    public void get() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/")
                .param("id", "**********")
                .header("Authorization", "Bearer ********-****-****-****-************")
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }


}
