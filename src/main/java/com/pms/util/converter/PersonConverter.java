package com.pms.util.converter;

import com.pms.dto.PersonDto;
import com.pms.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PersonConverter {

    @Autowired
    private AddresConverter addresConverter;

    public PersonDto converterEntityToDto(Person person) {
        return new PersonDto(person.getId(), person.getName(), person.getBirthday(), person.getAddress() == null ? null : (person.getAddress().isEmpty() ? null : person.getAddress().stream().map(p -> addresConverter.converterEntityToDto(p)).collect(Collectors.toList())));
    }

}

