package com.udd.lawsearch.government;

import com.udd.lawsearch.governmentLevel.GovernmentLevel;
import com.udd.lawsearch.shared.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Government implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @ManyToOne
    private GovernmentLevel governmentLevel;
    @Embedded
    private Address governmentAddress;
}
