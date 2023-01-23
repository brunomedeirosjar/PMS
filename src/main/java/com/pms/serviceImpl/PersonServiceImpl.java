package com.pms.serviceImpl;

import com.pms.dto.PersonDto;
import com.pms.entity.Person;
import com.pms.repository.PersonRepository;
import com.pms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person create(PersonDto personDto) {

        Person person = new Person((String) validateEmptyFieldsDto(personDto.getName()), (Date) validateEmptyFieldsDto(personDto.getBirthday()));
        person = personRepository.save(person);
        return person;
    }

    private Object validateEmptyFieldsDto(Object obj) {
        if (obj == null) {
            throw new ValidationException("Campo nao informado, por gentileza verificar dados da pessoa.");
        }
        return obj;
    }

    @Override
    public Person update(PersonDto requestDto) throws ValidationException {
        Person person = getPerson(requestDto.getId());
        person.setName(requestDto.getName());
        person.setBirthday(requestDto.getBirthday());
        personRepository.save(person);
        return person;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findPerson(Long id) throws ValidationException {
        return getPerson(id);
    }

    @Override
    public Person getPerson(Long id) throws ValidationException {
        Person person = new Person();
        person = personRepository.findById(id).orElseThrow(() -> new ValidationException("Pessoa não localizado, por gentileza verificar id informado."));
        return person;
    }

}