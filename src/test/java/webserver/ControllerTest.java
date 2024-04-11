package webserver;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import service.FileService;
import service.UserService;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.request.RequestHeader;
import webserver.entity.request.RequestLine;

@SuppressWarnings("NonAsciiCharacters")
public class ControllerTest {
    
    @ParameterizedTest(name = "path가 {0}일 경우")
    @ValueSource(strings = {"/index.html", "/css/styles.css"})
    void path에_맞는_파일을_응답한다(final String path) {
        // given
        final Controller controller = new Controller(new UserService(), new FileService());
        final RequestLine requestLine = RequestLine.fromRequestLineString("GET " + path + " HTTP/1.1");
        final RequestEntity request = new RequestEntity(
            new RequestHeader(requestLine, new HashMap<>(), new HashMap<>()), null);
        
        // when & then
        Assertions.assertDoesNotThrow(() -> controller.service(request));
    }
    
    @Test
    void 파일이_없을때_예외가_발생한다() {
        // given
        final Controller controller = new Controller(new UserService(), new FileService());
        final RequestLine requestLine = RequestLine.fromRequestLineString("GET /index2.html HTTP/1.1");
        final RequestEntity request = new RequestEntity(
            new RequestHeader(requestLine, new HashMap<>(), new HashMap<>()), null);
        
        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.service(request));
    }
}
