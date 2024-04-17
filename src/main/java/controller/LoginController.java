package controller;

import java.util.List;
import java.util.Map;

import model.User;
import service.UserService;
import webserver.entity.HttpCookie;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.session.SessionManager;

public class LoginController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/login");
    private final UserService userService;
    private final SessionManager sessionManager;

    public LoginController(final UserService userService, final SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        if (request.getMethod().equals(HttpMethod.POST)) {
            return doPost(request);
        }
        throw new IllegalArgumentException("지원하지 않는 HTTP Method 입니다.");
    }

    private ResponseEntity doPost(final RequestEntity request) {
        try {
            final User user = logInUser(request.getBody().get());
            HttpCookie loginCookie = setLoginUserCookie(user);
            ResponseEntity response = ResponseEntity.redirectResponseEntity("/index.html");
            response.addCookie(loginCookie);
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
        sessionManager.createSession(sessionIdCookie.getValue());
        sessionManager.getSessionBySessionId(sessionIdCookie.getValue()).setAttribute("user", loginUser);
        return sessionIdCookie;
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}