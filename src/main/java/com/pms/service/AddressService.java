package com.pms.service;

import com.pms.dto.AddressDto;
import com.pms.entity.Address;

import javax.validation.ValidationException;
import java.util.List;

public interface AddressService {
    Address create(AddressDto addressDto) throws ValidationException;

    List<Address> findAllAddressByPerson(Long personId) throws ValidationException;

    Address isPreferential(AddressDto dto);
}
