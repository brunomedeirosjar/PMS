package com.pms.service;

import com.pms.dto.PersonDto;
import com.pms.entity.Person;

import java.util.List;

public interface PersonService {

    Person create(PersonDto personDto);

    Person update(PersonDto requestDto) throws RuntimeException;

    List<Person> getAll();

    Person findPerson(Long id) throws RuntimeException;

    Person getPerson(Long id) throws RuntimeException;
}
