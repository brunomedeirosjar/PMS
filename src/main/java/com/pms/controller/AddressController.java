package com.pms.controller;

import com.pms.dto.AddressDto;
import com.pms.service.AddressService;
import com.pms.util.ResponseApi;
import com.pms.util.converter.AddresConverter;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    private AddresConverter addresConverter;


    @PostMapping(value = "/create")
    public ResponseEntity<ResponseApi<AddressDto>> create(@Valid @RequestBody AddressDto requestDto) {
        ResponseApi<AddressDto> response = new ResponseApi<>();
        try {
            AddressDto addressDto = addresConverter.converterEntityToDto(service.create(requestDto));
            response.setData(addressDto);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/findAllAddressByPerson/{personId}")
    public ResponseEntity<ResponseApi<List<AddressDto>>> findAllAddressByPerson(@PathVariable Long personId) {
        ResponseApi<List<AddressDto>> response = new ResponseApi<>();
        try {
            List<AddressDto> all = service.findAllAddressByPerson(personId).stream().map(a -> addresConverter.converterEntityToDto(a)).collect(Collectors.toList());
            response.setData(all);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping(value = "/isPreferential")
    public ResponseEntity<ResponseApi<AddressDto>> isPreferential(@Valid @RequestBody AddressDto requestDto) {
        ResponseApi<AddressDto> response = new ResponseApi<>();
        try {
            AddressDto addressDto = addresConverter.converterEntityToDto(service.isPreferential(requestDto));
            response.setData(addressDto);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            response.setErrors(Collections.singletonList(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
