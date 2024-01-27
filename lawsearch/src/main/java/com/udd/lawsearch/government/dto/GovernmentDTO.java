package com.udd.lawsearch.government.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernmentDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long governmentTypeId;
    private String country;
    private String city;
    private String street;
    private String number;
    private MultipartFile contract;
    private List<MultipartFile> law;
}
