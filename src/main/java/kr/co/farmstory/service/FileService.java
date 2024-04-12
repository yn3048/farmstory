package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.FileDTO;
import kr.co.farmstory.repository.FileRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j @Service @RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Value("uploads/")
    private String fileUploadPath;

    public List<FileDTO> fileUpload(ArticleDTO articleDTO){
        
        // 파일 업로드 경로
        String path = new File(fileUploadPath).getAbsolutePath();
        
        // 파일 정보 리턴 리스트
        List<FileDTO> files = new ArrayList<>();
        log.info("fileUpload...1");

        for(MultipartFile mf : articleDTO.getFiles()){
            log.info("fileUpload...2");

            if(!mf.isEmpty()){
                log.info("fileUpload...3");
                String oName = mf.getOriginalFilename();

                log.info("fileUpload...4 : "+ oName);

                // 확장자
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                log.info("fileUpload...5 : " + sName);

                try {
                    mf.transferTo(new File(path, sName));

                    FileDTO fileDTO = FileDTO.builder()
                                            .oName(oName)
                                            .sName(sName).build();

                    files.add(fileDTO);

                }catch (IOException e){
                    log.error("fileUpload : " + e.getMessage());
                }
            }
        }

        return files;
    }

    public ResponseEntity<?> fileDownload(int fno){
        kr.co.farmstory.entity.File file = fileRepository.findById(fno).get();

        try{
            Path path = Paths.get(fileUploadPath + file.getSName());
            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(file.getOName(), StandardCharsets.UTF_8).build());

            headers.add(HttpHeaders.CONTENT_TYPE, contentType);

            Resource resource = new InputStreamResource(Files.newInputStream(path));

            file.setDownload(file.getDownload()+1);
            fileRepository.save(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }catch (Exception e){
            log.info("fileDownload : "+e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fileDownloadCount(int fno){
        kr.co.farmstory.entity.File file = fileRepository.findById(fno).get();

        Map<String, Object> result = new HashMap<>();
        result.put("count", file.getDownload());

        return ResponseEntity.ok().body(result);
    }
}
