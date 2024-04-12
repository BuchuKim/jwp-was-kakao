package webserver.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControllerMapper {
    public static final String DEFAULT_PATH = "default";
    private final Map<String, Controller> controllers;

    public ControllerMapper(List<Controller> controllers) {
        this.controllers = new HashMap<>();
        controllers.forEach(controller -> {
            controller.getTargetPaths().forEach(path -> {
                this.controllers.put(path, controller);
            });
        });
    }

    public Controller getControllerByRequestPath(final String path) {
        return Optional.ofNullable(controllers.get(path))
            .orElse(controllers.get(DEFAULT_PATH));
    }
}
