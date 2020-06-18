package com.sap.api.workflow.integration;

import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port;
    }

    @Test
    void home_redirectsToSwaggerUI() {
        ResponseEntity<String> response = testRestTemplate
                .getForEntity(url + "/", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("swagger");
    }

    @Test
    void getWorkflowDefinition_returnsAListOfWorkflowDefinitions() {
        ResponseEntity<Object[]> response = testRestTemplate
                .getForEntity(url + "/workflow/definition/list", Object[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createWorkflowInstance_returnsNewlyCreatedWorkflowInstance() {
        Map<Object, Object> context = new HashMap<>();
        context.put("Id", "HT-1003");
        context.put("Quantity", "25");

        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder()
                .setContext(context)
                .setDefinitionId("orderprocess")
                .build();

        ResponseEntity<WorkflowInstanceResponse> response = testRestTemplate
                .postForEntity(url + "/workflow/instance/new", request, WorkflowInstanceResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}