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
package com.samuka.back.api.person.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.samuka.back.api.person.vo.PersonVO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Samuka
 */
@Entity(name = "person")
@Table(name="person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String gender;
    
    private String mail;
    
    @Column(name = "birth_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    
    private String naturalness;
    
    private String nationality;
    
    private String cpf;
    
    @Column(name = "registration_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date registrationDate;
    
    @Column(name = "change_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date changeDate;
    
    public Person() {
    }

    public Person(Long id, String name, String gender, String mail, Date birthDate, String naturalness, String nationality, String cpf, Date registrationDate, Date changeDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.birthDate = birthDate;
        this.naturalness = naturalness;
        this.nationality = nationality;
        this.cpf = cpf;
        this.registrationDate = registrationDate;
        this.changeDate = changeDate;
    }
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the naturalness
     */
    public String getNaturalness() {
        return naturalness;
    }

    /**
     * @param naturalness the naturalness to set
     */
    public void setNaturalness(String naturalness) {
        this.naturalness = naturalness;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the changeDate
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * @param changeDate the changeDate to set
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public void create() {
    
        setRegistrationDate(new Date());
        setChangeDate(new Date());
        
    }
    
    public void update(PersonVO toUpdate) {
    
        setName(toUpdate.getName());
        setGender(toUpdate.getGender());
        setMail(toUpdate.getMail());
        setBirthDate(toUpdate.getBirthDate());
        setNaturalness(toUpdate.getNaturalness());
        setNationality(toUpdate.getNationality());
        setCpf(toUpdate.getCpf());
        setChangeDate(new Date());

    }
    
}
