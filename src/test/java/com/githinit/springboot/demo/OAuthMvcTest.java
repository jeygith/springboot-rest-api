package com.githinit.springboot.demo;


import com.githinit.springboot.demo.config.AdditionalWebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = RestApplication.class)
@ActiveProfiles("mvc")
public class OAuthMvcTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AdditionalWebConfig additionalWebConfig;

    private MockMvc mockMvc;

    private static final String CLIENT_ID = "fooClientIdPassword";
    private static final String CLIENT_SECRET = "secret";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final String EMAIL = "jim@yahoo.com";
    private static final String NAME = "Jim";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        // @formatter:off

        ResultActions result = mockMvc.perform(post(CLIENT_ID + ":" + CLIENT_SECRET + "@/oauth/token")
                .params(params)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        // @formatter:on

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized1() throws Exception {
        final String accessToken = obtainAccessToken("admin", "nimda");

        System.out.println("\n access token: " + accessToken);

        mockMvc.perform(get("/api/employees").param("email", EMAIL)).andExpect(status().isOk());
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {

        final String accessToken = obtainAccessToken("admin", "nimda");

        System.out.println("\n access token: " + accessToken);


        MvcResult result = mockMvc.perform(get("/employees")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("[{'id': 1,'name': 'Stock 1';'price': 1}]"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        System.out.println("\n content: " + content);

    }

    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        final String accessToken = obtainAccessToken("user1", "pass");
    }
}
