package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author yvesguilherme on 22/07/2020.
 * @project spring-boot-essentials
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)   <- ativa testes usando o próprio bd
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

//    @Rule (junit)
//    public ExpectedException thrown = ExceptedException.none();

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Yves", "yveslopo@gmail.com");
        this.studentRepository.save(student);

        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Yves");
        assertThat(student.getEmail()).isEqualTo("yveslopo@gmail.com");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Yves", "yveslopo@gmail.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);

        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        Student student = new Student("Yves", "yveslopo@gmail.com");
        this.studentRepository.save(student);

        student.setName("Yves222");
        student.setEmail("yveslopo222@gmail.com");
        this.studentRepository.save(student);

        student = this.studentRepository.findById(student.getId()).orElse(null);

        assertThat(student.getName()).isEqualTo("Yves222");
        assertThat(student.getEmail()).isEqualTo("yveslopo222@gmail.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("yves", "yveslopo@gmail.com");
        Student student2 = new Student("yves", "yveslopo222@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("yves");

        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        Exception exception = assertThrows(ConstraintViolationException.class,
                () -> studentRepository.save(new Student("", "email@gmail.com")));

        assertTrue(exception.getMessage().contains("O campo nome do estudante é obrigatório!"));
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        Exception exception = assertThrows(ConstraintViolationException.class,
                () -> studentRepository.save(new Student("Nome estudante", "")));

        assertTrue(exception.getMessage().contains("O campo e-mail é obrigatório!"));
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
        Exception exception = assertThrows(ConstraintViolationException.class,
                () -> studentRepository.save(new Student("Nome estudante", "email.invalido.com")));

        assertTrue(exception.getMessage().contains("O campo e-mail deve ser válido!"));
    }
}