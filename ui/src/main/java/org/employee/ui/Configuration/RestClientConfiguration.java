package org.employee.ui.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:8080/api/v1")
            .defaultHeaders((headers) -> {
                headers.set("Content-Type", "application/json");
                headers.set("Accept", "*/*");
                headers.set("User-Agent", "ERMS/0.0.1");
                headers.set("Accept-Encoding", "gzip, deflate, br");
            })
            .build();
    }
}
