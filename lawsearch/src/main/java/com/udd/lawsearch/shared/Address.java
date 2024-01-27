package com.udd.lawsearch.shared;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements Serializable {
    private String country;
    private String city;
    private String street;
    private String number;
    private double latitude;
    private double longitude;
}
