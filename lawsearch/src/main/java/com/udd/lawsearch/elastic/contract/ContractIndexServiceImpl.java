package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.government.dto.GovernmentDTO;
import com.udd.lawsearch.shared.FileStorageService;
import com.udd.lawsearch.shared.PdfContractData;
import com.udd.lawsearch.shared.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ContractIndexServiceImpl implements ContractIndexService{
    private final ContractIndexRepository contractIndexRepository;
    private final FileStorageService fileStorageService;
    private final PdfService pdfService;
    @Value("${bucket-name}")
    private String bucketName;
    @Async
    public void create(GovernmentDTO governmentDTO, String governmentTypeName) throws Exception {
        String filename = governmentDTO.getContract().getOriginalFilename();
        InputStream stream = fileStorageService.getFileFromMilio(bucketName, filename);
        PdfContractData data = pdfService.getData(stream);
        ContractIndex contractIndex = new ContractIndex(
                data.getName(),
                data.getLastName(),
                governmentDTO.getUsername(),
                governmentTypeName,
                data.getContent()
        );

        contractIndexRepository.save(contractIndex);
    }
}
