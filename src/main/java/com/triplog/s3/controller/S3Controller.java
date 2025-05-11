package com.triplog.s3.controller;

import com.triplog.s3.dto.FileUploadResponse;
import com.triplog.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/api/storage")
@Tag(name = "S3", description = "S3(Simple Storage Service) 관련 API입니다.")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "파일 업로드", description = "파일을 업로드하여 URL을 생성합니다.")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {

        FileUploadResponse response = s3Service.saveFile(multipartFile);

        return ResponseEntity.ok(response);
    }
}
