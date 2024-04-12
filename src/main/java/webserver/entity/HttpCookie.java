package webserver.entity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpCookie {
    private static final String COOKIE_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private final String key;
    private final String value;

    private HttpCookie(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public static HttpCookie generateSessionIdCookie() {
        return new HttpCookie("JSESSIONID", UUID.randomUUID().toString());
    }

    public static List<HttpCookie> fromCookieString(String cookieString) {
        return Arrays.stream(cookieString.split(COOKIE_DELIMITER))
            .map(cookie -> {
                final String[] cookiePair = cookie.split(COOKIE_KEY_VALUE_DELIMITER);
                return new HttpCookie(cookiePair[0], cookiePair[1]);
            })
            .collect(Collectors.toList());
    }

    public String toSetCookieHeaderString() {
        return "Set-Cookie: " + toCookieString() + "; Path=/";
    }

    public String toCookieString() {
        return key + COOKIE_KEY_VALUE_DELIMITER + value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
