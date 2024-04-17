package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PathMapper {
    public static final String DEFAULT_PATH = "default";
    private final Map<String, Controller> pathMapper;

    public PathMapper() {
        this.pathMapper = new HashMap<>();
    }

    public void addController(final Controller controller) {
        controller.getTargetPaths().forEach(path -> {
            pathMapper.put(path, controller);
        });
    }

    public Controller findControllerByPath(String path) {
        return Optional.ofNullable(pathMapper.get(path))
            .orElse(pathMapper.get(DEFAULT_PATH));
    }
}
