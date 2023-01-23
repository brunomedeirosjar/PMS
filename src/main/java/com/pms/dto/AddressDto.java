package com.pms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

    private Long id;
    private String place;
    private String postalCode;
    private String city;
    private Long numberHome;
    private Boolean preferential;
    private Long person;
}
