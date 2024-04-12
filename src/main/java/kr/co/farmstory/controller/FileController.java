package kr.co.farmstory.controller;

import kr.co.farmstory.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j @RequiredArgsConstructor @Controller
public class FileController {
    private final FileService fileService;

    @GetMapping("/file/download/{fno}")
    public ResponseEntity<?> fileDownload(@PathVariable("fno") int fno){
        log.info("fileDownload" + fno);
        return fileService.fileDownload(fno);

    }

    public ResponseEntity<?> fileDownloadCount(int fno){
        return fileService.fileDownloadCount(fno);
    }
}
