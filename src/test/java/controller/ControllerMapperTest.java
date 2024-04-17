package controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.FileService;
import service.UserService;
import webserver.entity.HttpMethod;
import webserver.entity.request.RequestEntity;
import webserver.entity.request.RequestHeader;
import webserver.entity.request.RequestLine;
import webserver.entity.session.SessionManager;

public class ControllerMapperTest {
    private ControllerMapper controllerMapper;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService();
        FileService fileService = new FileService();
        SessionManager sessionManager = new SessionManager(new HashMap<>());
        final UserCreateController userCreateController = new UserCreateController(userService);
        final LoginFormController loginFormController = new LoginFormController(fileService, sessionManager);
        final LoginController loginController = new LoginController(userService, sessionManager);
        final UserController userController = new UserController(userService, sessionManager);
        final FileController fileController = new FileController(fileService);

        ControllerMapper controllerMapper = new ControllerMapper();
        controllerMapper.addController(HttpMethod.GET, userCreateController);
        controllerMapper.addController(HttpMethod.POST, userCreateController);
        controllerMapper.addController(HttpMethod.GET, loginFormController);
        controllerMapper.addController(HttpMethod.POST, loginController);
        controllerMapper.addController(HttpMethod.GET, userController);
        controllerMapper.addController(HttpMethod.GET, fileController);

        this.controllerMapper = controllerMapper;
    }

    @Test
    void 유저_컨트롤러에_맞는_컨트롤러를_매핑한다() {
        // given
        RequestHeader header = new RequestHeader(
            new RequestLine(HttpMethod.GET, "/user/create"), new HashMap<>(), new HashMap<>());
        final RequestEntity request = new RequestEntity(header, null);

        // when
        final Controller controller = controllerMapper.findControllerByRequest(request);

        // then
        assertThat(controller).isInstanceOf(UserCreateController.class);
    }

    @Test
    void 기본적인_요청은_파일_컨트롤러로_매핑된다() {
        // given
        RequestHeader header = new RequestHeader(
            new RequestLine(HttpMethod.GET, "/index.html"), new HashMap<>(), new HashMap<>());
        final RequestEntity request = new RequestEntity(header, null);

        // when
        final Controller controller = controllerMapper.findControllerByRequest(request);

        // then
        assertThat(controller).isInstanceOf(FileController.class);
    }
}
