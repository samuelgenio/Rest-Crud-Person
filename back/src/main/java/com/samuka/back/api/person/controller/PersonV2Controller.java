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

package com.samuka.back.api.person.controller;

import com.samuka.back.api.person.exception.ResourceNotFoundException;
import com.samuka.back.api.person.services.PersonServices;
import com.samuka.back.api.person.vo.PersonVO;
import com.samuka.back.api.person.vo.v2.PersonVOV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
@Api(value = "Person endpoint V2", tags = "version2")
@RestController
@RequestMapping("api/v2/person")
@CrossOrigin
public class PersonV2Controller {

    @Autowired
    private PersonServices services;
    
    @ApiOperation(value = "Find all persons")
    @GetMapping
    public List<PersonVO> findAll() throws Exception {
        return services.findAll();
    }
   
    @ApiOperation(value = "Create person")
    @PostMapping
    public PersonVO create(
            @RequestBody PersonVOV2 person
    ) throws Exception {
        return services.createV2(person);
    }
  
    @ApiOperation(value = "Update person")
    @PutMapping(value = "/{id}")
    public PersonVO update(
            @PathVariable("id") Long id,
            @RequestBody PersonVOV2 person
    ) throws Exception {
        
        if (id <= 0) {
            throw new ResourceNotFoundException("Id not found");
        }
        
        return services.update(id, person);
    }
    
    @ApiOperation(value = "Delete person")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) throws Exception {
        
        if (id <= 0) {
            throw new ResourceNotFoundException("Id not found");
        }
        
        services.delete(id);
        return ResponseEntity.ok(id);
    }
    
}
