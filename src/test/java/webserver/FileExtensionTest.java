package webserver;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
public class FileExtensionTest {
    @ParameterizedTest
    @CsvSource(value = {"./static/index.html, text/html", "/sadf/asdf.css, text/css", "/sadf/as.df.css, text/css",
        "/asd.sa/asdf, text/plain"})
    void 파일_경로에서_확장자를_추출한다(final String path, final String expected) {
        String actual = FileExtension.convertToContentType(path);
        assertThat(actual).isEqualTo(expected);
    }
}
