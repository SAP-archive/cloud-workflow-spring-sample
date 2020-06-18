package com.sap.api.workflow.domain.workflow.instance;

import java.util.Objects;

public class WorkflowInstanceResponse {
    private String id;
    private String definitionId;
    private int definitionVersion;
    private String subject;
    private String status;
    private String businessKey;
    private String startedAt;
    private String startedBy;
    private String completedAt;

    public WorkflowInstanceResponse() {
    }

    private WorkflowInstanceResponse(Builder builder) {
        this.id = builder.id;
        this.definitionId = builder.definitionId;
        this.definitionVersion = builder.definitionVersion;
        this.subject = builder.subject;
        this.status = builder.status;
        this.businessKey = builder.businessKey;
        this.startedAt = builder.startedAt;
        this.startedBy = builder.startedBy;
        this.completedAt = builder.completedAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getId() {
        return id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public int getDefinitionVersion() {
        return definitionVersion;
    }

    public String getSubject() {
        return subject;
    }

    public String getStatus() {
        return status;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public static class Builder {
        private String id;
        private String definitionId;
        private int definitionVersion;
        private String subject;
        private String status;
        private String businessKey;
        private String startedAt;
        private String startedBy;
        private String completedAt;

        private Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setDefinitionId(String definitionId) {
            this.definitionId = definitionId;
            return this;
        }

        public Builder setDefinitionVersion(int definitionVersion) {
            this.definitionVersion = definitionVersion;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setBusinessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public Builder setStartedAt(String startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder setStartedBy(String startedBy) {
            this.startedBy = startedBy;
            return this;
        }

        public Builder setCompletedAt(String completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder of(WorkflowInstanceResponse workflowInstanceResponse) {
            this.id = workflowInstanceResponse.id;
            this.definitionId = workflowInstanceResponse.definitionId;
            this.definitionVersion = workflowInstanceResponse.definitionVersion;
            this.subject = workflowInstanceResponse.subject;
            this.status = workflowInstanceResponse.status;
            this.businessKey = workflowInstanceResponse.businessKey;
            this.startedAt = workflowInstanceResponse.startedAt;
            this.startedBy = workflowInstanceResponse.startedBy;
            this.completedAt = workflowInstanceResponse.completedAt;
            return this;
        }

        public WorkflowInstanceResponse build() {
            return new WorkflowInstanceResponse(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowInstanceResponse that = (WorkflowInstanceResponse) o;
        return definitionVersion == that.definitionVersion &&
                Objects.equals(id, that.id) &&
                Objects.equals(definitionId, that.definitionId) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(status, that.status) &&
                Objects.equals(businessKey, that.businessKey) &&
                Objects.equals(startedAt, that.startedAt) &&
                Objects.equals(startedBy, that.startedBy) &&
                Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, definitionId, definitionVersion, subject, status, businessKey, startedAt, startedBy, completedAt);
    }

    @Override
    public String toString() {
        return "WorkflowInstanceResponse{" +
                "id='" + id + '\'' +
                ", definitionId='" + definitionId + '\'' +
                ", definitionVersion=" + definitionVersion +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", startedBy='" + startedBy + '\'' +
                ", completedAt='" + completedAt + '\'' +
                '}';
    }
}
