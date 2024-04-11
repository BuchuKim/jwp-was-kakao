package utils;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import webserver.utils.FormDataParser;

@SuppressWarnings("NonAsciiCharacters")
class FormDataParserTest {
    @Test
    void 쿼리스트링을_파싱할_수_있다() {
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
    
    @ParameterizedTest
    @NullAndEmptySource
    void 쿼리스트링이_없을때_쿼리파라미터_정보가_없다(final String input) {
        assertThat(FormDataParser.parse(input).keySet().isEmpty()).isTrue();
    }

    @Test
    void 첫번째_equal싸인까지가_쿼리파라미터의_key이다() {
        // given
        final String userId = "a=b";
        final String name = "이동규";
        final String formDataString = "userId=" + userId + "&name=" + name;
        System.out.println(formDataString);

        // when
        final Map<String, String> parsedData = FormDataParser.parse(formDataString);

        assertAll(
            () -> assertThat(parsedData.get("userId")).isEqualTo(userId),
            () -> assertThat(parsedData.get("name")).isEqualTo(name)
        );
    }

    @Test
    void equal싸인이_없으면_키만_추가한다() {
        // given
        final String key1 = "key1";
        final String key2 = "key2";
        final String formDataString = key1 + "&" + key2;
        System.out.println(formDataString);

        // when
        final Map<String, String> parsedData = FormDataParser.parse(formDataString);
        parsedData.forEach((k,v) -> System.out.println(k + " : " + v));

        // then: 키만 추가한 값의 value는 empty string이다.
        assertAll(
            () -> assertThat(parsedData.get(key1)).isEmpty(),
            () -> assertThat(parsedData.get(key2)).isEmpty()
        );
    }
}
