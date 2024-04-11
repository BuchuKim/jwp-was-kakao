package webserver.entity.request;

import java.util.Collections;
import java.util.Map;

import utils.FormDataParser;

public class RequestBody {
    private final Map<String, String> requestBody;
    
    public RequestBody(final Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }
    
    public Map<String, String> get() {
        return Collections.unmodifiableMap(requestBody);
    }
    
    public static RequestBody fromBodyString(final String bodyString) {
        final Map<String, String> body = FormDataParser.parse(bodyString);
        return new RequestBody(body);
    }
}
