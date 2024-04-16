package controller;

import java.util.List;

import service.UserService;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;

public class UserCreateController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/create");
    private final UserService userService;
    
    public UserCreateController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            return doGet(request);
        }

        if (request.getMethod().equals(HttpMethod.POST)) {
            return doPost(request);
        }

        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }

    private ResponseEntity doGet(final RequestEntity request) {
        userService.createUser(request.getHeader().getQueryParameter());
        return ResponseEntity.redirectResponseEntity("/index.html");
    }

    private ResponseEntity doPost(final RequestEntity request) {
        userService.createUser(request.getBody().get());
        return ResponseEntity.redirectResponseEntity("/index.html");
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
