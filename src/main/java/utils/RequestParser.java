package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import webserver.entity.request.RequestBody;
import webserver.entity.request.RequestEntity;
import webserver.entity.request.RequestHeader;

public class RequestParser {
    public static RequestEntity parse(final BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        
        List<String> headerLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            headerLines.add(line);
        }
        
        RequestHeader requestHeader = RequestHeader.fromHeaderString(requestLine, headerLines);
        int contentLength = Integer.parseInt(requestHeader.getHeaderField("Content-Length").orElse("0"));
        
        return new RequestEntity(
            RequestHeader.fromHeaderString(requestLine, headerLines),
            RequestBody.fromBodyString(IOUtils.readData(br, contentLength))
        );
    }
}
