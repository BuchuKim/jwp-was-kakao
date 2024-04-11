package webserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FileExtension {
    
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    WOFF2("woff2", "font/woff2"),
    EOT("eot", "font/vnd.ms-fontobject"),
    DEFAULT("none", "text/plain");
    
    private static final Pattern EXTENSION_MATCHER = Pattern.compile("\\.([^./\\s]+)$");

    private final String extension;
    private final String contentType;
    
    FileExtension(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }
    
    public static String convertToContentType(String filePath) {
        String extension = extractExtension(filePath);
        try {
            return FileExtension.valueOf(extension.toUpperCase()).getContentType();
        } catch (IllegalArgumentException e) {
            return DEFAULT.getContentType();
        }
    }
    
    private static String extractExtension(String filePath) {
        Matcher matcher = EXTENSION_MATCHER.matcher(filePath);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return DEFAULT.extension;
    }

    public String getContentType() {
        return contentType;
    }
}
