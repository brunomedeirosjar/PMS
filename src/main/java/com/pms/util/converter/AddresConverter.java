package com.pms.util.converter;

import com.pms.dto.AddressDto;
import com.pms.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddresConverter {

    public AddressDto converterEntityToDto(Address address) {
        return new AddressDto(address.getId(), address.getPlace(), address.getPostalCode(), address.getCity(), address.getNumberHome(), address.getPreferential(), address.getPerson().getId());
    }

}
