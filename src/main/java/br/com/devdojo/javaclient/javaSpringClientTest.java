package br.com.devdojo.javaclient;

import br.com.devdojo.model.PageableResponse;
import br.com.devdojo.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;

/**
 * @author yvesguilherme on 07/07/2020.
 * @project spring-boot-essentials
 */
public class javaSpringClientTest {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("Gabriela Viana");
        studentPost.setEmail("gabi_viana@pencil.com");
        JavaClienteDAO dao = new JavaClienteDAO();
        System.out.println(dao.findById(59));
//        System.out.println(dao.listAll());
//        System.out.println(dao.save(studentPost));
    }


}
