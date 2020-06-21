package br.com.devdojo.error;

/**
 * @author yvesguilherme on 21/06/2020.
 * @project spring-boot-essentials
 */
public class ResourceNotFoundDetails {
    private String title;
    private int status;
    private String detail;
    private Long timestamp;
    private String developerMessage;

    private ResourceNotFoundDetails() {
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private Long timestamp;
        private String developerMessage;

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

        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.detail = this.detail;
            resourceNotFoundDetails.timestamp = this.timestamp;
            resourceNotFoundDetails.developerMessage = this.developerMessage;
            resourceNotFoundDetails.status = this.status;
            resourceNotFoundDetails.title = this.title;
            return resourceNotFoundDetails;
        }
    }
}