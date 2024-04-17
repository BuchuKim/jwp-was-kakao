package controller;

import java.util.List;

import service.FileService;
import webserver.entity.request.RequestEntity;
import webserver.entity.response.ResponseEntity;

public class FileController implements Controller {
    private static final List<String> TARGET_PATHS = List.of(PathMapper.DEFAULT_PATH);
    private final FileService fileService;

    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity service(RequestEntity request) {
        return fileService.serveFile(request.getHeader());
    }

    @Override
    public List<String> getTargetPaths() {
        return TARGET_PATHS;
    }
}
