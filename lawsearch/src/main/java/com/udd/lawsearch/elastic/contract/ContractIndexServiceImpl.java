package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.government.dto.GovernmentDTO;
import com.udd.lawsearch.shared.FileStorageService;
import com.udd.lawsearch.shared.GeoLocationService;
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
    private final GeoLocationService geoLocationService;
    private final PdfService pdfService;
    @Value("${bucket-name}")
    private String bucketName;
    @Async
    public void create(String filename) throws Exception {
        InputStream stream = fileStorageService.getFileFromMilio(bucketName, filename);
        PdfContractData data = pdfService.getData(stream);
        var coordinates = GeoLocationService.getCoordinates(data.getGovernmentAddress());
        assert coordinates != null;
        ContractIndex contractIndex = new ContractIndex(
                data.getName(),
                data.getLastName(),
                data.getGovernmentName(),
                data.getGovernmentLevel(),
                data.getContent(),
                coordinates.get(0),
                coordinates.get(1),
                filename
        );

        contractIndexRepository.save(contractIndex);
    }
}
