package com.sap.api.workflow.domain.workflow.definition;

import java.util.List;
import java.util.Objects;

public class WorkflowDefinition {
    private String id;
    private String name;
    private String version;
    private String createdBy;
    private String createdAt;
    private List<String> jobs;

    public WorkflowDefinition() {
    }

    private WorkflowDefinition(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.version = builder.version;
        this.createdBy = builder.createdBy;
        this.createdAt = builder.createdAt;
        this.jobs = builder.jobs;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public static class Builder {
        private String id;
        private String name;
        private String version;
        private String createdBy;
        private String createdAt;
        private List<String> jobs;

        private Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setJobs(List<String> jobs) {
            this.jobs = jobs;
            return this;
        }

        public Builder of(WorkflowDefinition workflowDefinition) {
            this.id = workflowDefinition.id;
            this.name = workflowDefinition.name;
            this.version = workflowDefinition.version;
            this.createdBy = workflowDefinition.createdBy;
            this.createdAt = workflowDefinition.createdAt;
            this.jobs = workflowDefinition.jobs;
            return this;
        }

        public WorkflowDefinition build() {
            return new WorkflowDefinition(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowDefinition that = (WorkflowDefinition) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(jobs, that.jobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version, createdBy, createdAt, jobs);
    }

    @Override
    public String toString() {
        return "WorkflowDefinition{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", jobs=" + jobs +
                '}';
    }
}
