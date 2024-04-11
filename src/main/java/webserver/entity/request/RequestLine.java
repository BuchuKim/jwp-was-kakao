package webserver.entity.request;

import webserver.entity.HttpMethod;

public class RequestLine {
    private static final String EMPTY_SPACE = " ";

    private final HttpMethod method;
    private final String path;

    public RequestLine(final HttpMethod method, final String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine fromRequestLineString(final String requestLine) {
        final String[] line = requestLine.split(EMPTY_SPACE);
        final HttpMethod method = HttpMethod.of(line[0]);
        final String path = line[1].split("\\?")[0];
        return new RequestLine(method, path);
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
