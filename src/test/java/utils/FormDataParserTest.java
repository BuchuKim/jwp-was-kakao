package utils;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import webserver.RequestEntity;

@SuppressWarnings("NonAsciiCharacters")
class FormDataParserTest {
    @Test
    void 쿼리스트링을_파싱할_수_있다() throws IOException {
        // given
        final String userId = URLEncoder.encode("cu", StandardCharsets.UTF_8);
        final String name = URLEncoder.encode("이동규", StandardCharsets.UTF_8);
        final String formDataString = "userId=" + userId + "&name=" + name;
        
        // when
        final Map<String, String> parsedData = FormDataParser.parse(formDataString);
        
        // then
        assertThat(parsedData)
            .isEqualTo(new HashMap<String, String>() {{
                put("userId", "cu");
                put("name", "이동규");
            }});
    }
    
    // @Test
    // void 쿼리스트링이_없을때_쿼리파라미터_정보가_없다() throws IOException {
    //     // given
    //     // when
    //
    //     // then
    //     assertThat(RequestParser.parse(null)).isTrue();
    // }
}