package br.com.devdojo.error;

/**
 * @author yvesguilherme on 19/06/2020.
 * @project spring-boot-essentials2
 */
public class CustomErrorType {

    private String errorMessage;

    public CustomErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
