package webserver.entity.response;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import webserver.FileExtension;
import webserver.entity.HttpCookie;
import webserver.entity.StatusCode;

public class ResponseEntity {
    private final StatusCode statusCode;
    private final Map<String, String> headers;
    private HttpCookie cookie;
    private final byte[] body;

    private ResponseEntity(final StatusCode statusCode, final Map<String, String> headers, final byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.cookie = new HttpCookie();
        this.body = body;
    }
    
    private ResponseEntity(final StatusCode statusCode, final Map<String, String> headers) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.cookie = new HttpCookie();
        this.body = new byte[0];
    }

    public static ResponseEntity htmlDataOf(final String body) {
        return new ResponseEntity(StatusCode.OK,
            Map.of("Content-Type", "text/html;charset=utf-8"),
            body.getBytes(StandardCharsets.UTF_8));
    }
    
    public static ResponseEntity fileDataOf(final String path, final byte[] body) {
        return new ResponseEntity(StatusCode.OK,
            Map.of("Content-Type", FileExtension.convertToContentType(path) + ";charset=utf-8",
                "Content-Length", String.valueOf(body.length)),
            body);
    }
    
    public static ResponseEntity redirectResponseEntity(final String location) {
        return new ResponseEntity(StatusCode.FOUND,
            Map.of("Location", location));
    }
    
    public static ResponseEntity notFoundResponseEntity() {
        return new ResponseEntity(StatusCode.NOT_FOUND,
            Map.of());
    }
    
    public static ResponseEntity internalServerErrorResponseEntity() {
        return new ResponseEntity(StatusCode.INTERNAL_SERVER_ERROR,
            Map.of());
    }

    public void addCookie(final HttpCookie cookie) {
        this.cookie = cookie;
    }
    
    public String toResponseMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ");
        stringBuilder.append(statusCode.generateResponseLine());
        headers.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append(cookie.toSetCookieHeaderString());
        stringBuilder.append("\r\n");
        stringBuilder.append(new String(body, StandardCharsets.ISO_8859_1));
        return stringBuilder.toString();
    }
}
