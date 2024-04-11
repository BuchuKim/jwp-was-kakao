package webserver.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormDataParser {
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    public static Map<String, String> parse(final String dataString) {
        Map<String, String> queryParameters = new HashMap<>();
        
        if (dataString == null || dataString.isEmpty()) {
            return queryParameters;
        }
        
        final String[] parameters = dataString.split(AMPERSAND);
        Arrays.stream(parameters).forEach(parameter -> {
            Map.Entry<String, String> queryEntry = getQueryEntry(parameter);
            queryParameters.put(URLDecoder.decode(queryEntry.getKey(), StandardCharsets.UTF_8),
                URLDecoder.decode(queryEntry.getValue(), StandardCharsets.UTF_8));
        });
        return queryParameters;
    }

    private static Map.Entry<String, String> getQueryEntry(String parameter) {
        int splitIndex = parameter.indexOf(EQUAL);
        if (splitIndex > 0) {
            return Map.entry(parameter.substring(0, splitIndex), parameter.substring(splitIndex + 1));
        }
        return Map.entry(parameter, "");
    }
}
