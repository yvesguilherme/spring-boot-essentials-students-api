package br.com.devdojo.model;

import javax.persistence.Entity;

/**
 * @author yvesguilherme on 19/06/2020.
 * @project spring-boot-essentials
 */
@Entity
public class Student extends AbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
