package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import webserver.RequestBody;
import webserver.RequestEntity;
import webserver.RequestHeader;

public class RequestParser {
    public static RequestEntity parse(final BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        
        List<String> headerLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            headerLines.add(line);
        }
        
        RequestHeader requestHeader = RequestHeader.fromHeaderString(requestLine, headerLines);
        int contentLength = Integer.parseInt(requestHeader.getHeaderField("Content-Length"));
        
        return new RequestEntity(
            RequestHeader.fromHeaderString(requestLine, headerLines),
            RequestBody.fromBodyString(IOUtils.readData(br, contentLength))
        );
    }
}