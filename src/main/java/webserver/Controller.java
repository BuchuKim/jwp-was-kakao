package webserver;

import service.FileService;
import service.UserService;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;

public class Controller {
    private static final String USER_CREATE_PATH = "/user/create";
    private final UserService userService;
    private final FileService fileService;
    
    public Controller(final UserService userService, final FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }
    
    public ResponseEntity service(final RequestEntity request) {
        if (request.getHeader().getPath().equals(USER_CREATE_PATH)) {
            return createUser(request);
        }
        
        try {
            return fileService.serveFile(request.getHeader());
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }
    
    private ResponseEntity createUser(final RequestEntity request) {
        if (request.getHeader().getMethod().equals(HttpMethod.GET)) {
            userService.createUser(request.getHeader().getQueryParameter());
        }
        userService.createUser(request.getBody().get());
        return ResponseEntity.redirectResponseEntity("/index.html");
    }
}
