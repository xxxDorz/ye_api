package com.ye.yeapiclientsdk;

import com.ye.yeapiclientsdk.client.YeApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("yeapi.client")
@ComponentScan
public class YeApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public YeApiClient yeApiClient(){
        return new YeApiClient(accessKey, secretKey);
    }
}
