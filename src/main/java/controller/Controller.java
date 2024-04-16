package controller;

import java.util.List;

import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;

public interface Controller {
    ResponseEntity service(RequestEntity request);

    List<String> getTargetPaths();
}
