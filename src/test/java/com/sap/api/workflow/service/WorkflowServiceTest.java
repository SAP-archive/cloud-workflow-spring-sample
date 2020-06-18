package com.sap.api.workflow.service;

import com.sap.api.workflow.client.WorkflowClient;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WorkflowServiceTest {

    private WorkflowService workflowService;

    private WorkflowClient workflowClient;

    @BeforeEach
    void setUp() {
        workflowClient = mock(WorkflowClient.class);
        workflowService = new WorkflowService(workflowClient);
    }

    @Test
    void getWorkflowDefinitions_returnsListOfWorkflowDefinitions() {
        workflowService.getWorkflowDefinitions();

        verify(workflowClient).getWorkflowDefinitions();
    }

    @Test
    void createWorkflowInstance_callsWorkflowClient() {
        workflowService.createWorkflowInstance(WorkflowInstanceRequest.builder().build());

        verify(workflowClient).createWorkflowInstance(any());
    }

    @Test
    void createWorkflowInstance_passesArgumentToWorkflowClient() {
        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder()
                .setDefinitionId("test-definition-id")
                .build();

        workflowService.createWorkflowInstance(request);

        verify(workflowClient).createWorkflowInstance(request);
    }

    @Test
    void createWorkflowInstance_returnsWorkflowInstanceResponse() {
        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder()
                .setDefinitionId("test-definition-id")
                .build();

        WorkflowInstanceResponse expectedResponse = WorkflowInstanceResponse.builder()
                .setId("test-id")
                .build();

        given(workflowClient.createWorkflowInstance(request)).willReturn(expectedResponse);

        WorkflowInstanceResponse actualResponse = workflowService.createWorkflowInstance(request);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}