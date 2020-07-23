package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

/**
 * @author yvesguilherme on 22/07/2020.
 * @project spring-boot-essentials
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("toyo", "devdojo");
        }
    }

    @Test
    public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        System.out.println(port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        System.out.println(port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/1", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findStudentsByNameWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/v1/protected/findByName/{name}", String.class, "Yves Guilherme");
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() throws Exception {
        List<Student> students = asList(new Student(1L, "Legolas", "legolas@lotr.com"), new Student(2L, "Aragorn", "aragorn@lotr.com"));
        Page<Student> pagedStudents = new PageImpl<>(students);

        when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

        mockMvc.perform(get("/v1/protected/students/"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(studentRepository).findAll(isA(Pageable.class));
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() throws Exception {
        Student student = new Student(1L, "Legolas", "legolas@lotr.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/v1/protected/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Legolas")))
                .andExpect(jsonPath("$.email", is("legolas@lotr.com")));

        verify(studentRepository, times(2)).findById(1L);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
    public void getStudentsByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode404() throws Exception {
        Student student = new Student(3L, "Legolas", "legolas@lotr.com");
        when(studentRepository.findById(3L)).thenReturn(java.util.Optional.of(student));

        mockMvc.perform(get("/v1/protected/students/{id}", 6))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(studentRepository).findById(6L);
    }
}
