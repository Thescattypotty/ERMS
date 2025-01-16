package org.employee.ui.Configuration;

import org.employee.ui.Service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:8080/api/v1")
            .defaultHeader("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .build();
    }
}
