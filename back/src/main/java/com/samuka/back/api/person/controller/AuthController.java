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

import com.samuka.back.api.person.repo.UserRepository;
import com.samuka.back.api.person.security.AccountCredentialsVO;
import com.samuka.back.api.person.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    UserRepository repository;
    
    @ApiOperation(value = "Authenticate user by credentials")
    @PostMapping(value="/signin", produces = {"application/json"})
    public ResponseEntity create(
            @RequestBody AccountCredentialsVO account
    ) throws Exception {
        
        try {
        
            var username = account.getUsername();
            var password = account.getPassword();
            
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            var user = repository.findByUsername(username);
            
            var token = "";
            
            if (user != null) {
                token = tokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found.");
            }
            
            Map<Object, Object> mapData = new HashMap<>();
            mapData.put("username", username);
            mapData.put("token", token);
            
            return ResponseEntity.ok(mapData);
            
        } catch (Exception e) {
            throw  new BadCredentialsException("Invalid username/password.");
        }
        
        
    }
    
}
