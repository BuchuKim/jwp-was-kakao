package service;

import utils.FileIoUtils;
import webserver.entity.request.RequestHeader;
import webserver.entity.response.ResponseEntity;

public class FileService {
    public ResponseEntity serveFile(final RequestHeader request) {
        try {
            String path = request.getPath();
            byte[] staticFileData = FileIoUtils.loadFileFromClasspath(path);
            return ResponseEntity.fileDataOf(path, staticFileData);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }
}
