package com.pms.controller;

import com.pms.dto.PersonDto;
import com.pms.entity.Address;
import com.pms.entity.Person;
import com.pms.service.AddressService;
import com.pms.service.PersonService;
import com.pms.util.ResponseApi;
import com.pms.util.converter.PersonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonConverter converterPerson;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseApi<PersonDto>> createPerson(@Valid @RequestBody PersonDto requestDto) {
        ResponseApi<PersonDto> response = new ResponseApi<>();
        try {
            Person person = service.create(requestDto);
            newAddress(requestDto, person);
            response.setData(converterPerson.converterEntityToDto(person));
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseApi<PersonDto>> updatePerson(@Valid @RequestBody PersonDto requestDto) {
        ResponseApi<PersonDto> response = new ResponseApi<>();
        try {
            Person person = service.update(requestDto);
            newAddress(requestDto, person);
            response.setData(converterPerson.converterEntityToDto(person));
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/findPerson/{id}")
    public ResponseEntity<ResponseApi<PersonDto>> findPerson(@PathVariable Long id) {
        ResponseApi<PersonDto> response = new ResponseApi<>();
        try {
            PersonDto person = converterPerson.converterEntityToDto(service.findPerson(id));
            response.setData(person);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<ResponseApi<List<PersonDto>>> findAll() {
        ResponseApi<List<PersonDto>> response = new ResponseApi<>();
        try {
            List<PersonDto> person = service.getAll().stream().map(p -> converterPerson.converterEntityToDto(p)).collect(Collectors.toList());
            response.setData(person);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private void newAddress(PersonDto requestDto, Person person) {
        if (requestDto.getAddressDtos() != null && !requestDto.getAddressDtos().isEmpty()) {
            List<Address> address = requestDto.getAddressDtos().stream().map(a -> {
                a.setPerson(person.getId());
                return addressService.create(a);
            }).collect(Collectors.toList());
            person.setAddress(address);
        }
    }

}

