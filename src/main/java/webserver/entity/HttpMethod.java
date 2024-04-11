package webserver.entity;

public enum HttpMethod {
    GET, POST;
    
    public static HttpMethod of(final String name) {
        return HttpMethod.valueOf(name);
    }
}
