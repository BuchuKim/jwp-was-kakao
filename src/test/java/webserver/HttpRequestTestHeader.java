package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestTestHeader {
    @BeforeEach
    void setUp() throws InterruptedException {
        new Thread(() -> {
            try {
                WebApplicationServer.main(new String[]{"8080"});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(500);
    }

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
