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
package com.samuka.back.api.person.handler;

import com.samuka.back.api.person.exception.ExceptionResponse;
import com.samuka.back.api.person.exception.InvalidDataException;
import com.samuka.back.api.person.exception.InvalidJwtAuthenticationException;
import com.samuka.back.api.person.exception.ResourceNotFoundException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {

        ExceptionResponse exResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
        
    }
    
    @ExceptionHandler(InvalidDataException.class)
    private final ResponseEntity<ExceptionResponse> handleNotAcceptableException(Exception ex, WebRequest request) {

        ExceptionResponse exResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.NOT_ACCEPTABLE);
        
    }
    
    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    private final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(Exception ex, WebRequest request) {

        ExceptionResponse exResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
        
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    private final ResponseEntity<ExceptionResponse> handleBadCredentialsException(Exception ex, WebRequest request) {

        ExceptionResponse exResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.FORBIDDEN);
        
    }
    
}
