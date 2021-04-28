package com.samuka.back.api.person;

import com.samuka.back.api.person.exception.InvalidDataException;
import com.samuka.back.api.person.services.PersonServices;
import com.samuka.back.api.person.vo.PersonVO;
import com.samuka.back.api.person.vo.v2.PersonVOV2;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Simples classe para testar processos internos.
 * Foram executados os testes diretamente acessando os métodos.
 * Não foi implementado MockMvc.
 * @author Samuka <samuelgenio28@gmail.com>
 */
@SpringBootTest
class BackApplicationTests {

    @Autowired
    private PersonServices services;
    
    /**
    * Teste de inserção e remoção de pessoa.
    */
    @Test
    @DisplayName("Valida API V1 Completa.")
    void testAPIV1Complete() {
    
        PersonVO person = null;
        
        try {
            person = services.create(getObjectPerson(1));
        } catch (Exception ex) {
        }
        
        Assertions.assertNotNull(person, "Person inserted");
        
        person.setName("New Name");
        
        PersonVO personUpdated = null;
        
        try {
            personUpdated = services.update(person.getId(), person);
        } catch (Exception ex) {
        }
        
        Assertions.assertSame(person.getName(), personUpdated.getName(), "Person updated");
        
        try {
            
            PersonVO deleted = services.delete(person.getId());
            
            Assertions.assertEquals(deleted.getId().equals(person.getId()), true, "Person deleted");
        } catch (Exception ex) {
            Logger.getLogger(BackApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @DisplayName("Valida API V2 Completa.")
    void testAPIV2Complete() {
    
        final PersonVOV2 personFinal = getObjectPersonV2(1);
        
        //inválid adress
        Assertions.assertThrows(InvalidDataException.class, () -> { services.createV2(personFinal); }, "Invalid address");
        
        PersonVOV2 person = null;
        
        try {
            
            PersonVOV2 personInsert = getObjectPersonV2(1);
            personInsert.setNaturalness("SC - Tubarão");
            
            person = services.createV2(personInsert);
        } catch (Exception ex) {
        }
        
        Assertions.assertNotNull(person, "PersonV2 inserted");
        
        person.setName("New Name");
        
        PersonVO personUpdated = null;
        
        try {
            personUpdated = services.update(person.getId(), person);
        } catch (Exception ex) {
        }
        
        Assertions.assertSame(person.getName(), personUpdated.getName(), "Person updated");
        
        try {
            
            PersonVO deleted = services.delete(person.getId());
            
            Assertions.assertEquals(deleted.getId().equals(person.getId()), true, "Person deleted");
        } catch (Exception ex) {
            Logger.getLogger(BackApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Simple mock
     * @param number
     * @return 
     */
    private PersonVO getObjectPerson(int number) {
    
        PersonVO obj = new PersonVO(
                "Person " + number,
                number % 2 == 0 ? "M" : "F",
                "mail" + number + "@email.com",
                new Date(),
                "SC - Tubarão",
                number % 2 == 0 ? "Brasileiro" : "Brasileira",
                "880.267.697-67",
                null,
                null
        );
        
        return obj;
    }
    
    /**
     * Simple mock
     * @param number
     * @return 
     */
    private PersonVOV2 getObjectPersonV2(int number) {
    
        PersonVOV2 obj = new PersonVOV2 (
                "PersonV2 " + number,
                number % 2 == 0 ? "M" : "F",
                "mail" + number + "@email.com",
                new Date(),
                "",
                number % 2 == 0 ? "Brasileiro" : "Brasileira",
                "510.291.415-41",
                null,
                null
        );
        
        return obj;
    }
    
}
