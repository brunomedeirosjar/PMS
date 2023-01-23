package com.pms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDto {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Brazil/East")
    private Date birthday;

    private List<AddressDto> addressDtos;

}