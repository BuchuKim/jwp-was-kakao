package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.ControllerMapper;
import controller.FileController;
import controller.LoginController;
import controller.LoginFormController;
import controller.UserController;
import controller.UserCreateController;
import service.FileService;
import service.UserService;
import webserver.entity.HttpMethod;
import webserver.entity.session.SessionManager;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    
    public static void main(String args[]) throws Exception {
        int port = DEFAULT_PORT;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        final ControllerMapper controllerMapper = createControllerMapper();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, controllerMapper));
                thread.start();
            }
        }
    }

    private static ControllerMapper createControllerMapper() {
        UserService userService = new UserService();
        FileService fileService = new FileService();
        SessionManager sessionManager = new SessionManager(new HashMap<>());
        final UserCreateController userCreateController = new UserCreateController(userService);
        final LoginController loginController = new LoginController(userService, sessionManager);
        final LoginFormController loginFormController = new LoginFormController(fileService, sessionManager);
        final UserController userController = new UserController(userService, sessionManager);
        final FileController fileController = new FileController(fileService);

        ControllerMapper controllerMapper = new ControllerMapper();
        controllerMapper.addController(HttpMethod.GET, userCreateController);
        controllerMapper.addController(HttpMethod.POST, userCreateController);
        controllerMapper.addController(HttpMethod.GET, loginFormController);
        controllerMapper.addController(HttpMethod.POST, loginController);
        controllerMapper.addController(HttpMethod.GET, userController);
        controllerMapper.addController(HttpMethod.GET, fileController);
        return controllerMapper;
    }
}
