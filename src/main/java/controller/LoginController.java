package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.User;
import service.FileService;
import service.UserService;
import webserver.entity.HttpCookie;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.session.SessionManager;

public class LoginController implements Controller {
    private static final List<String> TARGET_PATHS = List.of("/user/login", "/user/login.html");
    private final UserService userService;
    private final FileService fileService;
    private final SessionManager sessionManager;

    public LoginController(final UserService userService,
        final FileService fileService,
        final SessionManager sessionManager) {
        this.userService = userService;
        this.fileService = fileService;
        this.sessionManager = sessionManager;
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
        if (isLogIn(request)) {
            // 이미 로그인 되어있는 경우 -> 리다이렉트
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        return fileService.serveFile(request.getHeader());
    }

    private ResponseEntity doPost(final RequestEntity request) {
        try {
            final User user = logInUser(request.getBody().get());
            ResponseEntity response = ResponseEntity.redirectResponseEntity("/index.html");

            HttpCookie loginCookie = setLoginUserCookie(user);
            loginCookie.addPath("/");
            response.addCookie(loginCookie);
            return response;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.redirectResponseEntity("/user/login_failed.html");
        }
    }

    private boolean isLogIn(final RequestEntity request) {
        return request.getHeader().getCookie().getSessionIdValue()
            .map(sessionManager::getSessionBySessionId)
            .map(session -> session.getAttribute("user"))
            .map(Optional::isPresent)
            .orElse(false);
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
