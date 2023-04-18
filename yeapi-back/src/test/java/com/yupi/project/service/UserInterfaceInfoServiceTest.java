package com.yupi.project.service;

//import org.junit.Test;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
public class UserInterfaceInfoServiceTest {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    private static final String GATEWAY_HOST = "http://localhost:8090";


    @Test
    public void invokeCount() {
        boolean result = userInterfaceInfoService.invokeCount(1L,1L);
        Assertions.assertTrue(result);
    }

}