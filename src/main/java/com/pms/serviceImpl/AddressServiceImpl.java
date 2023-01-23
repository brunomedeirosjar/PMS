package com.pms.serviceImpl;


import com.pms.dto.AddressDto;
import com.pms.dto.ViaCepDto;
import com.pms.entity.Address;
import com.pms.entity.Person;
import com.pms.repository.AddressRepository;
import com.pms.rest.ViaCep;
import com.pms.service.AddressService;
import com.pms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ViaCep viaCep;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonService personService;

    @Override
    public Address create(AddressDto addressDto) throws ValidationException {
        Person person = personService.getPerson(addressDto.getPerson());
        ViaCepDto viaCepDto = getViaCep(addressDto);
        return addressRepository.save(newAddress(addressDto, person, viaCepDto));
    }

    private Address newAddress(AddressDto addressDto, Person person, ViaCepDto viaCepDto) {
        Address address = new Address();
        if (validateEmptyFieldsViaCep(viaCepDto)) {
            address = new Address(null, viaCepDto.getLogradouro(), viaCepDto.getCep(), (Long) validateEmptyFieldsDto(addressDto.getNumberHome()), viaCepDto.getLocalidade(), addressDto.getPreferential() != null && addressDto.getPreferential(), person);
        } else {
            address = new Address(null, (String) validateEmptyFieldsDto(addressDto.getPlace()), addressDto.getPostalCode(), (Long) validateEmptyFieldsDto(addressDto.getNumberHome()), (String) validateEmptyFieldsDto(addressDto.getCity()), addressDto.getPreferential() != null && addressDto.getPreferential(), person);
        }
        return address;
    }

    private boolean validateEmptyFieldsViaCep(ViaCepDto viaCepDto) {
        return viaCepDto != null && viaCepDto.getLogradouro() != null && !viaCepDto.getLogradouro().isEmpty();
    }

    private Object validateEmptyFieldsDto(Object obj) {
        if (obj == null) {
            throw new ValidationException("Campo nao informado, por gentileza verificar dados de endereco.");
        }
        return obj;
    }

    private ViaCepDto getViaCep(AddressDto addressDto) throws ValidationException {
        ViaCepDto viaCepDto;
        try {
            viaCepDto = viaCep.search(addressDto.getPostalCode());
        } catch (Exception e) {
            throw new ValidationException("Cep Não localizado");
        }
        return viaCepDto;
    }

    @Override
    public List<Address> findAllAddressByPerson(Long personId) throws ValidationException {
        Person person = personService.getPerson(personId);
        List<Address> addresses = addressRepository.findByPersonId(person.getId());
        if (addresses.isEmpty()) {
            throw new ValidationException("Está pessoa não possui endereço cadastrado.");
        }
        return addresses;
    }

    @Override
    public Address isPreferential(AddressDto dto) throws ValidationException {
        Address address;
        address = addressRepository.findById(dto.getId()).orElseThrow(() -> new ValidationException("Não foi localizado endereço, favor verificar id informado."));
        address.setPreferential(dto.getPreferential());
        addressRepository.save(address);
        return address;
    }

}
