package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import br.com.devdojo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTokenTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserRepository userRepository;

    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Student> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @BeforeEach
    public void configProtectedHeaders() {

        String str = "{\"username\": \"oda\", \"password\": \"devdojo\"}";

        HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();

        this.protectedHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configAdminHeaders() {
        String str = "{\"username\": \"toyo\", \"password\": \"devdojo\"}";
        HttpHeaders headers = restTemplate.postForEntity("http://localhost:8080/login", str, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void configWrongHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "11111");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @BeforeEach
    public void setup() {
        Student student = new Student(3L, "Legolas", "legolas@lotr.com");

        when(studentRepository.findById(3L)).thenReturn(java.util.Optional.of(student));
    }

    @Test
    public void listStudentsWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate.exchange("/v1/protected/students/", GET, wrongHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getStudentsByIdWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate.exchange("/v1/protected/students/1", GET, wrongHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listStudentsWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/", GET, protectedHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentsByIdWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/1", GET, protectedHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentsByIdWhenTokenIsCorrectAndStudentDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/protected/students/-1", GET, protectedHeader, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/admin/students/{id}", DELETE, adminHeader, String.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @WithMockUser(username = "xxx", password = "xxx", roles = {"ADMIN"})
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode404() throws Exception {
        doNothing().when(studentRepository).deleteById(1L);

        mockMvc.perform(delete("http://localhost:8080/v1/admin/students/{id}", 1))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(studentRepository, atLeast(1)).findById(1L);
    }

}