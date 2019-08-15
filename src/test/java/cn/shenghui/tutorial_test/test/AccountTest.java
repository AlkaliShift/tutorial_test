package cn.shenghui.tutorial_test.test;

import cn.shenghui.tutorial.TutorialApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/8/14 15:57
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TutorialApplication.class)
public class AccountTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @Rollback
    public void testCreateAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/createAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"accountName\": \"testName\",\n" +
                        "  \"payPassword\": \"password\"\n" +
                        "}")
        )
                .andExpect(jsonPath("$.statusCode").value(1))
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testQueryAccountById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/queryAccount")
                .param("accountId", "9527")
        )
                .andExpect(jsonPath("$.statusCode").value(2))
                .andReturn();
    }

    @Test
    @Transactional
    @Rollback
    public void testDeduction() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/deduction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"accountId\": \"BX000\",\n" +
                        "  \"amount\": \"100\"\n" +
                        "}")
        )
                .andExpect(jsonPath("$.statusCode").value(1))
                .andExpect(jsonPath("$.accountId").isEmpty())
                .andReturn();
    }
}