package com.udd.lawsearch.documents;

import java.io.InputStream;
import java.io.OutputStream;

import com.udd.lawsearch.elastic.contract.ContractIndexService;
import com.udd.lawsearch.elastic.law.LawIndexService;
import com.udd.lawsearch.shared.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/documents")
public class DocumentsController {
    private final FileStorageServiceImpl fileStorageService;
    private final ContractIndexService contractIndexService;
    private final LawIndexService lawIndexService;
    @Value("${bucket-name}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadDocuments(@ModelAttribute UploadDocumentsDTO dto) throws Exception {
        fileStorageService.uploadFile(dto.getContract());
        for (MultipartFile file : dto.getLaws()) {
            fileStorageService.uploadFile(file);
        }
        contractIndexService.create(dto.getContract().getOriginalFilename());
        lawIndexService.create(dto.getLaws());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/download/{objectName}")
    public void downloadFile(@PathVariable String objectName, HttpServletResponse response) {

        try (InputStream inputStream = fileStorageService.getFileFromMilio(bucketName, objectName)) {
            response.addHeader("Content-disposition", "attachment;filename=\"" + objectName + "\"");
            response.setContentType("application/octet-stream");

            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}