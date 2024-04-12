package webserver.entity;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.request.RequestParser;

@SuppressWarnings("NonAsciiCharacters")
public class RequestParserTest {
    @Test
    void 사용자_요청을_받아서_파싱한다() throws IOException {
        // given
        final String request = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\nConnection: keep-alive\n"
            + "Accept: */*\n"
            + "Content-Length: 92\n"
            + "\r\n"
            + "userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com";
        final StringReader stringReader = new StringReader(request);
        final BufferedReader br = new BufferedReader(stringReader);
        
        // when
        final RequestEntity requestEntity = RequestParser.parse(br);
        
        // then
        Assertions.assertAll(
            () -> assertThat(requestEntity.getHeader().getPath()).isEqualTo("/user/create"),
            () -> assertThat(requestEntity.getHeader().getMethod()).isEqualTo(HttpMethod.POST),
            () -> assertThat(requestEntity.getBody().get().get("userId")).isEqualTo("cu"),
            () -> assertThat(requestEntity.getBody().get().get("password")).isEqualTo("password"),
            () -> assertThat(requestEntity.getBody().get().get("name")).isEqualTo("이동규"),
            () -> assertThat(requestEntity.getBody().get().get("email")).isEqualTo("brainbackdoor@gmail.com")
        );
    }
}
