package webserver.entity.request;

import webserver.entity.HttpMethod;

public class RequestEntity {
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    
    public RequestEntity(final RequestHeader requestHeader, final RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }
    
    public RequestHeader getHeader() {
        return requestHeader;
    }
    
    public RequestBody getBody() {
        return requestBody;
    }

    public HttpMethod getMethod() {
        return requestHeader.getMethod();
    }
}
