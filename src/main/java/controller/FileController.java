package controller;

import java.util.List;

import service.FileService;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;

public class FileController implements Controller {
    private static final List<String> TARGET_PATHS = List.of(ControllerMapper.DEFAULT_PATH);
    private final FileService fileService;

    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        try {
            return fileService.serveFile(request.getHeader());
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
