package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;
import webserver.entity.request.RequestParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Controller controller;
    
    private final Socket connection;
    
    public RequestHandler(Socket connectionSocket, Controller controller) {
        this.connection = connectionSocket;
        this.controller = controller;
    }
    
    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    private void handle(final InputStream in, final OutputStream out) throws IOException {
        InputStreamReader ir = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(ir);
        DataOutputStream dos = new DataOutputStream(out);

        try (ir; br; dos) {
            ResponseEntity responseData = service(br);
            dos.writeBytes(responseData.toResponseMessage());
            dos.flush();
        }
    }
    
    private ResponseEntity service(BufferedReader br) {
        try {
            RequestEntity request = RequestParser.parse(br);
            return controller.service(request);
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException : {}", e.getMessage());
            return ResponseEntity.notFoundResponseEntity();
        } catch (Exception e) {
            logger.error("Unknown Exception : {}", e.getMessage());
            return ResponseEntity.internalServerErrorResponseEntity();
        }
    }
}
