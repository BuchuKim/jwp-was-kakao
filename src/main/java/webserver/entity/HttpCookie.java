package webserver.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HttpCookie {
    public static final String JSESSIONID_KEY = "JSESSIONID";
    public static final String PATH_KEY = "Path";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> cookieData;

    public HttpCookie() {
        this.cookieData = new HashMap<>();
    }

    public static HttpCookie generateSessionIdCookie() {
        HttpCookie cookie = new HttpCookie();
        cookie.cookieData.put(JSESSIONID_KEY, UUID.randomUUID().toString());
        return cookie;
    }

    public static HttpCookie fromCookieString(String cookieString) {
        HttpCookie parsed = new HttpCookie();
        Arrays.stream(cookieString.split(COOKIE_DELIMITER))
            .forEach(cookie -> {
                final String[] cookiePair = cookie.split(COOKIE_KEY_VALUE_DELIMITER);
                parsed.cookieData.put(cookiePair[0], cookiePair[1]);
            });
        return parsed;
    }

    public String toSetCookieHeaderString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Set-Cookie: ");
        cookieData.forEach((key, value) ->
            builder.append(key).append(COOKIE_KEY_VALUE_DELIMITER).append(value).append("; "));
        builder.append("\r\n");
        return builder.toString();
    }

    public void addPath(String path) {
        cookieData.put(PATH_KEY, path);
    }

    public Optional<String> getSessionIdValue() {
        return Optional.ofNullable(cookieData.get(JSESSIONID_KEY));
    }
}
