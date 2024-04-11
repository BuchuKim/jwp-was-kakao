package webserver.entity.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import webserver.entity.HttpMethod;
import webserver.utils.FormDataParser;

public class RequestHeader {
    private static final String EMPTY_SPACE = " ";
    private static final String QUERY_DELIMETER = "\\?";
    private static final String HEADER_COLON = ": ";

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> queryParameters;
    
    public RequestHeader(final RequestLine requestLine,
        final Map<String, String> headers,
        final Map<String, String> queryParameters) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.queryParameters = queryParameters;
    }
    
    public static RequestHeader fromHeaderString(final String requestLine, final List<String> headerLines) {
        final RequestLine line = RequestLine.fromRequestLineString(requestLine);

        final String[] url = requestLine.split(EMPTY_SPACE)[1].split(QUERY_DELIMETER);
        final String queryString = url.length > 1 ? url[1] : "";
        
        final Map<String, String> headers = new HashMap<>();
        headerLines.forEach(headerLine -> {
            final String[] header = headerLine.split(HEADER_COLON);
            headers.put(header[0], header[1]);
        });
        return new RequestHeader(line, headers, FormDataParser.parse(queryString));
    }
    
    public Optional<String> getHeaderField(final String headerKey) {
        return Optional.ofNullable(headers.get(headerKey));
    }
    
    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
    
    public String getPath() {
        return requestLine.getPath();
    }
    
    public Map<String, String> getQueryParameter() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
