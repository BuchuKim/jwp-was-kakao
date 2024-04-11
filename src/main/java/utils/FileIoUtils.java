package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String TEMPLATES_PREFIX = "./templates";
    private static final String STATIC_PREFIX = "./static";
    private static final String HTML_SUFFIX = ".html";
    
    public static byte[] loadFileFromClasspath(final String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(convertToEntirePath(filePath)).toURI());
        return Files.readAllBytes(path);
    }
    
    private static String convertToEntirePath(final String path) {
        if (path.endsWith(HTML_SUFFIX)) {
            return TEMPLATES_PREFIX + path;
        }
        return STATIC_PREFIX + path;
    }
}
