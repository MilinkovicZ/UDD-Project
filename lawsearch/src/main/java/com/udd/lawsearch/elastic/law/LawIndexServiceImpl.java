package com.udd.lawsearch.elastic.law;

import com.udd.lawsearch.elastic.contract.ContractIndex;
import com.udd.lawsearch.shared.FileStorageService;
import com.udd.lawsearch.shared.PdfContractData;
import com.udd.lawsearch.shared.PdfService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LawIndexServiceImpl implements LawIndexService{
    private final LawIndexRepository lawIndexRepository;
    private final FileStorageService fileStorageService;
    @Value("${bucket-name}")
    private String bucketName;

    @Async
    public void create(List<MultipartFile> laws) throws Exception {
        for (MultipartFile law : laws) {
            String filename = law.getOriginalFilename();
            InputStream stream = fileStorageService.getFileFromMilio(bucketName, filename);
            PDDocument pdDocument = PDDocument.load(stream);
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(pdDocument);
            LawIndex lawIndex = new LawIndex(text);
            lawIndexRepository.save(lawIndex);
        }
    }
}
