package com.sap.api.workflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.api.workflow.domain.workflow.definition.WorkflowDefinition;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import com.sap.api.workflow.service.WorkflowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkflowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkflowService workflowService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getWorkflowDefinition_callsWorkflowService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/workflow/definition/list"))
                .andExpect(status().isOk());

        verify(workflowService).getWorkflowDefinitions();
    }

    @Test
    void getWorkflowDefinition_returnsListOfWorkflowDefinitionFromWorkflowService() throws Exception {
        List<WorkflowDefinition> expectedResponse = Collections.singletonList(
                WorkflowDefinition.builder().setId("test-id").build()
        );

        given(workflowService.getWorkflowDefinitions()).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/workflow/definition/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".[0].id").value("test-id"));
    }

    @Test
    void createWorkflowInstance_callsWorkflowService() throws Exception {
        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder().build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/workflow/instance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        verify(workflowService).createWorkflowInstance(any());
    }

    @Test
    void createWorkflowInstance_returnsNewlyCreatedWorkflowInstanceResponse() throws Exception {
        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder().build();
        String jsonRequest = objectMapper.writeValueAsString(request);

        WorkflowInstanceResponse expectedResponse = WorkflowInstanceResponse.builder()
                .setId("test-response-id")
                .build();

        given(workflowService.createWorkflowInstance(any())).willReturn(expectedResponse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/workflow/instance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        WorkflowInstanceResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WorkflowInstanceResponse.class);

        verify(workflowService).createWorkflowInstance(any());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void createWorkflowInstance_passesParameterToWorkflowService() throws Exception {
        WorkflowInstanceRequest request = WorkflowInstanceRequest.builder()
                .setDefinitionId("test")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/workflow/instance/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        verify(workflowService).createWorkflowInstance(request);
    }
}