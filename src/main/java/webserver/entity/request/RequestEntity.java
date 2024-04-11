package webserver.entity.request;

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
}
