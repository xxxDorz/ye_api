package com.ye.yeapiinterface;

import cn.hutool.http.HttpUtil;
import com.ye.yeapiclientsdk.YeApiClientConfig;
import com.ye.yeapiclientsdk.client.YeApiClient;
import com.ye.yeapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
class YeapiInterfaceApplicationTests {

    @Resource
    private YeApiClient yeApiClient;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("userye");
        String result1 = yeApiClient.getNameByGet("qwe");
        String result2 = yeApiClient.getNameByPost("asd");
        String result3 = yeApiClient.getUsernameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

}
