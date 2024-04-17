package controller;

import java.util.List;
import java.util.Map;

import model.User;
import service.UserService;
import webserver.entity.HttpCookie;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.session.SessionManager;

public class LoginController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/login", "/user/login.html");
    private final UserService userService;
    private final SessionManager sessionManager;

    public LoginController(final UserService userService,
        final SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        try {
            final User user = logInUser(request.getBody().get());
            ResponseEntity response = ResponseEntity.redirectResponseEntity("/index.html");
            HttpCookie loginCookie = setLoginUserCookie(user);
            response.addCookie(loginCookie);
            loginCookie.addPath("/");
            return response;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.redirectResponseEntity("/user/login_failed.html");
        }
    }

    private User logInUser(final Map<String, String> userData) {
        User found =  userService.getUser(userData.get("userId"))
            .orElseThrow(() -> new IllegalArgumentException("잘못된 아이디 혹은 비밀번호입니다."));
        if (found.getPassword().equals(userData.get("password"))) {
            return found;
        }
        throw new IllegalArgumentException("잘못된 아이디 혹은 비밀번호입니다.");
    }

    private HttpCookie setLoginUserCookie(User loginUser) {
        HttpCookie sessionIdCookie = HttpCookie.generateSessionIdCookie();
        String generatedSessionId = sessionIdCookie.getSessionIdValue().orElseThrow();
        sessionManager.createSession(generatedSessionId);
        sessionManager.getSessionBySessionId(generatedSessionId).setAttribute("user", loginUser);
        return sessionIdCookie;
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
