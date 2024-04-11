package webserver.entity;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import webserver.entity.request.RequestHeader;

@SuppressWarnings("NonAsciiCharacters")
class RequestHeaderTest {
    @Test
    void 헤더_라인으로부터_객체를_생성한다() {
        final String requestLine = "GET /index.html?name=test&password=1234 HTTP/1.1";
        final List<String> headerLines = List.of(
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Accept: */*");
        
        final RequestHeader requestHeader = RequestHeader.fromHeaderString(requestLine, headerLines);
        
        Assertions.assertAll(
            () -> assertThat(requestHeader.getMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(requestHeader.getPath()).isEqualTo("/index.html"),
            () -> assertThat(requestHeader.getHeaderField("Host").isPresent()).isTrue(),
            () -> assertThat(requestHeader.getHeaderField("Host").get()).isEqualTo("localhost:8080"),
            () -> assertThat(requestHeader.getQueryParameter().get("name")).isEqualTo("test"),
            () -> assertThat(requestHeader.getQueryParameter().get("password")).isEqualTo("1234")
        );
    }
}
