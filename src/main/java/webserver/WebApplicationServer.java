package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.FileService;
import service.UserService;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    
    public static void main(String args[]) throws Exception {
        int port = DEFAULT_PORT;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        
        final UserService userService = new UserService();
        final FileService fileService = new FileService();
        final Controller controller = new Controller(userService, fileService);
        
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, controller));
                thread.start();
            }
        }
    }
}
