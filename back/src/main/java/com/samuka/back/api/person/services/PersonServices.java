/*
 * Copyright 2021 Samuka <samuelgenio28@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samuka.back.api.person.services;

import com.samuka.back.api.person.converter.DozerConverter;
import com.samuka.back.api.person.exception.InvalidDataException;
import com.samuka.back.api.person.exception.ResourceNotFoundException;
import com.samuka.back.api.person.model.Person;
import com.samuka.back.api.person.repo.PersonRepository;
import com.samuka.back.api.person.vo.PersonVO;
import com.samuka.back.api.person.vo.v2.PersonVOV2;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
@Service
public class PersonServices extends DefaultServices {

    @Autowired
    PersonRepository repo;

    public PersonVO create(PersonVO person) {
        
        this.validateModel(person);
        
        var entity = DozerConverter.parseObject(person, Person.class);
        
        entity.create();
        
        var vo = DozerConverter.parseObject(repo.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        
        this.validateModel(person, 2);
        
        var entity = DozerConverter.parseObject(person, Person.class);
        
        entity.create();
        
        var vo = DozerConverter.parseObject(repo.save(entity), PersonVOV2.class);
        return vo;
    }

    public PersonVO update(Long id, PersonVO person) {

        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No data found"));
        
        entity.update(person);
      
        return DozerConverter.parseObject(repo.save(entity), PersonVO.class);
    }

    public PersonVO delete(Long id) {

        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No data found"));

        repo.delete(entity);

        return DozerConverter.parseObject(entity, PersonVO.class);
    }

    public PersonVO findById(Long id) {

        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No data found"));

        return DozerConverter.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        return DozerConverter.parseListObjects(repo.findAll(), PersonVO.class);
    }

    private void validateModel(PersonVO person) {
        this.validateModel(person, 1);
    }
    
    /**
     * Validate the model
     * @param person 
     */
    private void validateModel(PersonVO person, int version) {
    
        if (person.getName() == null || person.getName().isEmpty()) {
            throw new InvalidDataException("Invalid Name.");
        }
        
        if (person.getMail() == null || !person.getMail().isEmpty() && !isValidEmail(person.getMail())) {
            throw new InvalidDataException("Invalid mail.");
        }
        
        if (person.getGender() != null && person.getGender().length() > 1) {
            throw new InvalidDataException("Invalid gender, use M or F.");
        }
        
        if (person.getCpf() == null || !isValidCPF(person.getCpf())) {
            throw new InvalidDataException("Invalid CPF.");
        } else if (repo.findByCPF(person.getCpf()) != null) {
            throw new InvalidDataException("CPF already registered.");
        }
        
        if (version == 2) {
            
            if (person.getNaturalness().isEmpty()) {
                throw new InvalidDataException("Invalid naturalness.");
            }
            
            if (person.getNationality().isEmpty()) {
                throw new InvalidDataException("Invalid nationality.");
            }
            
        }
        
    }
    
}
