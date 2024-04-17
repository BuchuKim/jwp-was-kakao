package controller;

import java.io.IOException;
import java.util.List;

import controller.dto.UserListDto;
import service.UserService;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.session.SessionManager;
import webserver.view.ViewResolver;

public class UserController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/list", "/user/list.html");
    private final UserService userService;
    private final SessionManager sessionManager;

    public UserController(final UserService userService, final SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        try {
            checkLogIn(request);
            UserListDto foundUsers = new UserListDto(userService.findAll());
            return ResponseEntity.htmlDataOf(ViewResolver.resolve("user/list", foundUsers));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.redirectResponseEntity("/user/login.html");
        } catch (IOException e) {
            throw new IllegalArgumentException("존재하지 않는 페이지입니다!");
        }
    }

    private void checkLogIn(final RequestEntity request) {
        final String sessionId = request.getHeader().getCookie("JSESSIONID")
            .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."))
            .getValue();
        sessionManager.getSessionBySessionId(sessionId)
            .getAttribute("user").orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
