package com.sap.api.workflow.client;

import com.sap.api.workflow.config.SAPApiEndpoint;
import com.sap.api.workflow.domain.workflow.definition.WorkflowDefinition;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class WorkflowClient {

    private final OAuth2RestOperations oauth2RestTemplate;

    private final SAPApiEndpoint sapApiEndpoint;

    public WorkflowClient(OAuth2RestOperations oauth2RestTemplate, SAPApiEndpoint sapApiEndpoint) {
        this.oauth2RestTemplate = oauth2RestTemplate;
        this.sapApiEndpoint = sapApiEndpoint;
    }

    public List<WorkflowDefinition> getWorkflowDefinitions() {
        ResponseEntity<List<WorkflowDefinition>> responseEntity = oauth2RestTemplate.exchange(
                sapApiEndpoint.getBase() + sapApiEndpoint.getDefinition(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else {
            return emptyList();
        }
    }

    public WorkflowInstanceResponse createWorkflowInstance(WorkflowInstanceRequest workflowInstanceRequest) {
        ResponseEntity<WorkflowInstanceResponse> responseEntity = oauth2RestTemplate.postForEntity(
                sapApiEndpoint.getBase() + sapApiEndpoint.getInstance(),
                workflowInstanceRequest,
                WorkflowInstanceResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else {
            return WorkflowInstanceResponse.builder().build();
        }
    }
}
