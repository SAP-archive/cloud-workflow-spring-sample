package com.sap.api.workflow.domain.workflow.instance;

import java.util.Map;
import java.util.Objects;

public class WorkflowInstanceRequest {
    private String definitionId;
    private Map<Object, Object> context;

    public WorkflowInstanceRequest() {
    }

    private WorkflowInstanceRequest(Builder builder) {
        this.definitionId = builder.definitionId;
        this.context = builder.context;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getDefinitionId() {
        return definitionId;
    }

    public Map<Object, Object> getContext() {
        return context;
    }

    public static class Builder {
        private String definitionId;
        private Map<Object, Object> context;

        private Builder() {
        }

        public Builder setDefinitionId(String definitionId) {
            this.definitionId = definitionId;
            return this;
        }

        public Builder setContext(Map<Object, Object> context) {
            this.context = context;
            return this;
        }

        public Builder of(WorkflowInstanceRequest workflowInstanceRequest) {
            this.definitionId = workflowInstanceRequest.definitionId;
            this.context = workflowInstanceRequest.context;
            return this;
        }

        public WorkflowInstanceRequest build() {
            return new WorkflowInstanceRequest(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowInstanceRequest that = (WorkflowInstanceRequest) o;
        return Objects.equals(definitionId, that.definitionId) &&
                Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(definitionId, context);
    }

    @Override
    public String toString() {
        return "WorkflowInstanceRequest{" +
                "definitionId='" + definitionId + '\'' +
                ", context=" + context +
                '}';
    }
}
