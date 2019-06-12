package com.githinit.springboot.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApplicationTests {

    @Value("${coach.name}")
    private String name;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAppProperties() {
        System.out.println("server port: " + name);
    }

}
