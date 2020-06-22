package br.com.devdojo.error;

/**
 * @author yvesguilherme on 22/06/2020.
 * @project spring-boot-essentials
 */
public class ValidationErrorDetails extends ErrorDetail {
    private String field;
    private String fieldMessage;

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private Long timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.setDetail(detail);
            validationErrorDetails.setTimestamp(timestamp);
            validationErrorDetails.setDeveloperMessage(developerMessage);
            validationErrorDetails.setStatus(status);
            validationErrorDetails.setTitle(title);
            validationErrorDetails.field = field;
            validationErrorDetails.fieldMessage = fieldMessage;
            return validationErrorDetails;
        }
    }

    public String getField() {
        return field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }
}
