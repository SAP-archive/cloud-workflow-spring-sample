package com.sap.api.workflow.client;

import com.sap.api.workflow.config.SAPApiEndpoint;
import com.sap.api.workflow.domain.workflow.definition.WorkflowDefinition;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RestClientTest(WorkflowClient.class)
class WorkflowClientTest {

    private WorkflowClient workflowClient;

    @MockBean
    private OAuth2RestOperations oAuth2RestOperations;

    @MockBean
    private SAPApiEndpoint sapApiEndpoint;

    @BeforeEach
    void setUp() {
        workflowClient = new WorkflowClient(oAuth2RestOperations, sapApiEndpoint);

        when(sapApiEndpoint.getBase()).thenReturn("base-test-url/");
        when(sapApiEndpoint.getInstance()).thenReturn("instance-test-path");
        when(sapApiEndpoint.getDefinition()).thenReturn("definition-test-path");
    }

    @Test
    void getWorkflowDefinitions_MakesCorrectCallToWorkflowDefinitionEndpoint() {
        List<WorkflowDefinition> expectedResponse = singletonList(WorkflowDefinition.builder()
                .setId("test-id")
                .build());

        ResponseEntity expectedResponseEntityResponse = new ResponseEntity(expectedResponse, HttpStatus.OK);

        when(oAuth2RestOperations.exchange(
                "base-test-url/definition-test-path",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WorkflowDefinition>>() {
                })
        ).thenReturn(expectedResponseEntityResponse);

        List<WorkflowDefinition> actualResponse = workflowClient.getWorkflowDefinitions();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void createWorkflowInstance_MakesCorrectCallToWorkflowInstanceEndpoint() {
        WorkflowInstanceResponse expectedResponse = WorkflowInstanceResponse.builder().setDefinitionId("test").build();

        ResponseEntity expectedResponseEntityResponse = new ResponseEntity(
                expectedResponse,
                HttpStatus.OK
        );

        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder().build();

        when(oAuth2RestOperations.postForEntity(
                "base-test-url/instance-test-path",
                request,
                WorkflowInstanceResponse.class)
        ).thenReturn(expectedResponseEntityResponse);

        WorkflowInstanceResponse actualResponse = workflowClient.createWorkflowInstance(request);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}