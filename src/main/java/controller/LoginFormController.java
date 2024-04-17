package controller;

import java.util.List;
import java.util.Optional;

import service.FileService;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.session.SessionManager;

public class LoginFormController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/login", "/user/login.html");
    private final FileService fileService;
    private final SessionManager sessionManager;

    public LoginFormController(final FileService fileService, final SessionManager sessionManager) {
        this.fileService = fileService;
        this.sessionManager = sessionManager;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        if (isLogIn(request)) {
            // 이미 로그인 되어있는 경우 -> 리다이렉트
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        return fileService.serveFile(request.getHeader());
    }

    private boolean isLogIn(final RequestEntity request) {
        return request.getHeader().getCookie().getSessionIdValue()
            .map(sessionManager::getSessionBySessionId)
            .map(session -> session.getAttribute("user"))
            .map(Optional::isPresent)
            .orElse(false);
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
