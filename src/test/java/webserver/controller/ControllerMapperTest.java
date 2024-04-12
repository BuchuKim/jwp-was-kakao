package webserver.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.FileService;
import service.UserService;

public class ControllerMapperTest {
    private ControllerMapper controllerMapper;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(new UserService());
        FileController fileController = new FileController(new FileService());
        this.controllerMapper = new ControllerMapper(List.of(userController, fileController));
    }

    @Test
    void 유저_컨트롤러에_맞는_컨트롤러를_매핑한다() {
        // given
        final String userPath = "/user/create";

        // when
        final Controller controller = controllerMapper.getControllerByRequestPath(userPath);

        // then
        assertThat(controller).isInstanceOf(UserController.class);
    }

    @Test
    void 기본적인_요청은_파일_컨트롤러로_매핑된다() {
        // given
        final String userPath = "/index.html";

        // when
        final Controller controller = controllerMapper.getControllerByRequestPath(userPath);

        // then
        assertThat(controller).isInstanceOf(FileController.class);
    }
}
