package controller;

import java.util.EnumMap;
import java.util.Map;

import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;

public class ControllerMapper {
    private final Map<HttpMethod, PathMapper> mapping;

    public ControllerMapper() {
        mapping = new EnumMap<>(HttpMethod.class);
        mapping.put(HttpMethod.GET, new PathMapper());
        mapping.put(HttpMethod.POST, new PathMapper());
    }

    public void addController(HttpMethod method, Controller controller) {
        mapping.get(method).addController(controller);
    }

    public Controller findControllerByRequest(final RequestEntity request) {
        return mapping.get(request.getMethod())
            .findControllerByPath(request.getHeader().getPath());
    }
}
