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
package com.samuka.back.api.person.vo.v2;

import com.samuka.back.api.person.vo.PersonVO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
public class PersonVOV2 extends PersonVO implements Serializable {

    public PersonVOV2() {
    }
    
    public PersonVOV2(Long id, String name, String gender, String mail, Date birthDate, String naturalness, String nationality, String cpf, Date registrationDate, Date changeDate) {
        super(id, name, gender, mail, birthDate, naturalness, nationality, cpf, registrationDate, changeDate);
    }
    
    public PersonVOV2(String name, String gender, String mail, Date birthDate, String naturalness, String nationality, String cpf, Date registrationDate, Date changeDate) {
        super(name, gender, mail, birthDate, naturalness, nationality, cpf, registrationDate, changeDate);
    }
    
}
