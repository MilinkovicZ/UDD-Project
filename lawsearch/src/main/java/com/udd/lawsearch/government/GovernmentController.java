package com.udd.lawsearch.government;

import com.udd.lawsearch.elastic.contract.ContractIndexService;
import com.udd.lawsearch.exceptions.EntityNotFoundException;
import com.udd.lawsearch.government.dto.GovernmentDTO;
import com.udd.lawsearch.governmentLevel.GovernmentLevel;
import com.udd.lawsearch.governmentLevel.GovernmentLevelService;
import com.udd.lawsearch.shared.Address;
import com.udd.lawsearch.shared.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/government")
public class GovernmentController {
    private final GovernmentService governmentService;
    private final GovernmentLevelService governmentLevelService;
    private final FileStorageService fileStorageService;
    private final ContractIndexService contractIndexService;

    @PostMapping
    public ResponseEntity<Void> createGovernment(@ModelAttribute GovernmentDTO governmentDTO) throws Exception {
        Government government = mapGovernment(governmentDTO);
        governmentService.create(government);
        fileStorageService.uploadFile(governmentDTO.getContract());
        for (MultipartFile file : governmentDTO.getLaw()) {
            fileStorageService.uploadFile(file);
        }

        contractIndexService.create(governmentDTO, government.getGovernmentLevel().getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Government mapGovernment(GovernmentDTO governmentDTO) throws EntityNotFoundException {
        Government government = new Government();
        Address address = new Address();
        GovernmentLevel governmentLevel = governmentLevelService.findById(governmentDTO.getGovernmentTypeId());

        government.setUsername(governmentDTO.getUsername());
        government.setFirstName(governmentDTO.getFirstName());
        government.setLastName(governmentDTO.getLastName());
        government.setEmail(governmentDTO.getEmail());
        government.setPhoneNumber(governmentDTO.getPhoneNumber());
        government.setGovernmentLevel(governmentLevel);

        address.setCountry(governmentDTO.getCountry());
        address.setCity(governmentDTO.getCity());
        address.setStreet(governmentDTO.getStreet());
        address.setNumber(governmentDTO.getNumber());
        address.setLatitude(30);
        address.setLongitude(30);

        government.setGovernmentAddress(address);

        return government;
    }
}
