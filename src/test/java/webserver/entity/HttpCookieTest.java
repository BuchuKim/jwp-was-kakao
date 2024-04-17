package webserver.entity;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class HttpCookieTest {
    @Test
    void 쿠키_헤더로부터_쿠키_객체를_생성한다() {
        // given
        final String cookieString = "JSESSIONID=1234; Path=/";

        // when
        final HttpCookie cookie = HttpCookie.fromCookieString(cookieString);

        // then
        assertThat(cookie.getSessionIdValue().orElseThrow()).isNotNull();
    }

    @Test
    void set_cookie_헤더를_생성한다() {
        // given
        final HttpCookie cookie = HttpCookie.generateSessionIdCookie();

        // when
        final String setCookieHeaderString = cookie.toSetCookieHeaderString();

        // then
        assertThat(setCookieHeaderString).contains("Set-Cookie: JSESSIONID=");
    }

    @Test
    void 쿠키_항목을_더한다() {
        // given
        final HttpCookie cookie = HttpCookie.generateSessionIdCookie();

        // when
        cookie.addPath("/");

        // then
        assertThat(cookie.toSetCookieHeaderString()).contains("Path=/");
    }
}