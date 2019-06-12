package com.githinit.springboot.demo;


import com.githinit.springboot.demo.config.AdditionalWebConfig;
import com.githinit.springboot.demo.config.AuthorizationServerConfig;
import com.githinit.springboot.demo.config.ResourceServerConfig;
import com.githinit.springboot.demo.config.SecurityConfig;
import com.githinit.springboot.demo.controller.EmployeeController;
import com.githinit.springboot.demo.entity.Employee;
import com.githinit.springboot.demo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest()
@ContextConfiguration(classes = {AdditionalWebConfig.class, AuthorizationServerConfig.class, ResourceServerConfig.class, SecurityConfig.class})
public class EmployeeAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    @Qualifier("employeeServiceImpl")
    private EmployeeService employeeService;

    @MockBean
    private UserDetailsService userDetailsService;


    @MockBean
    private EmployeeController employeeController;

/*
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
*/

    @WithMockUser(username = "spring")
    @Test
    public void findAll() throws Exception {
        // given
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Jeff");
        employee.setLastName("Gith");
        employee.setEmail("githireh@gmail.com");


        List<Employee> employees = Arrays.asList(employee);

        System.out.println(employees);

        given(employeeController.findAll()).willReturn(employees);

        // when + then
        String base64ForTestUser = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiam9obi5kb2UiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTYwNDEzNDYxLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIl0sImp0aSI6ImZiODQwOTNiLTQxMDgtNGFmYS1hMGNhLTE3NDI3YjNmZmJmZCIsImNsaWVudF9pZCI6InRlc3Rqd3RjbGllbnRpZCJ9.rRCkDlCcGGOUogfvdumvM6L3mArO_JPOsa-u0QUmr5U";
        MvcResult result = this.mockMvc.perform(get("localhost:8080/api/employees").header("Authorization", base64ForTestUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("[{'id': 1,'name': 'Stock 1';'price': 1}]"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        System.out.println("\n content: " + content);
    }
}
