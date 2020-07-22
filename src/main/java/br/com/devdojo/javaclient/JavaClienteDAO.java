package br.com.devdojo.javaclient;

import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @author yvesguilherme on 21/07/2020.
 * @project spring-boot-essentials
 */
public class JavaClienteDAO {
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthentication("toyo", "devdojo")
            .build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthentication("toyo", "devdojo")
            .build();

    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
//        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 1);
    }

    public List<Student> listAll() {
        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getContent();
    }

    public Student save(Student student) {
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(student, createJsonHeader()), Student.class);

        return exchangePost.getBody();
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
