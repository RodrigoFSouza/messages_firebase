package com.example.mess.config;

import com.google.api.client.util.Value;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class ApplicationConfig {

    @Value("${fireBase.dbBaseUrl}")
    private String dbBaseUrl;

    @Value("${fireBase.collection}")
    private String collectionName;

    @Value("${fireBase.authFileLocation}")
    private String authFileLocation;

}
