package br.com.devdojo.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yvesguilherme on 19/06/2020.
 * @project spring-boot-essentials
 */
@Entity
public class Student extends AbstractEntity {

    @NotEmpty(message = "O campo nome do estudante é obrigatório!")
    @NotNull
    private String name;

    @NotEmpty(message = "O campo e-mail é obrigatório!")
    @NotNull
    @Email
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student: {" + "\n" +
                " name: " + name + "\n" +
                " email: " + email + "\n" +
                "}";
    }
}
