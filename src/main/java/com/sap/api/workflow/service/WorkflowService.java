package com.sap.api.workflow.service;

import com.sap.api.workflow.client.WorkflowClient;
import com.sap.api.workflow.domain.workflow.definition.WorkflowDefinition;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowService {

    private final WorkflowClient workflowClient;

    public WorkflowService(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    public List<WorkflowDefinition> getWorkflowDefinitions() {
        return workflowClient.getWorkflowDefinitions();
    }

    public WorkflowInstanceResponse createWorkflowInstance(WorkflowInstanceRequest workflowInstanceRequest) {
        return workflowClient.createWorkflowInstance(workflowInstanceRequest);
    }
}
