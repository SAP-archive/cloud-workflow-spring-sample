package com.sap.api.workflow.controller;

import com.sap.api.workflow.domain.workflow.definition.WorkflowDefinition;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceRequest;
import com.sap.api.workflow.domain.workflow.instance.WorkflowInstanceResponse;
import com.sap.api.workflow.service.WorkflowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/workflow")
public class WorkflowController {
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping(value = "/definition/list")
    public List<WorkflowDefinition> getWorkflowDefinitions() {
        return workflowService.getWorkflowDefinitions();
    }

    @PostMapping(value = "/instance/new")
    public WorkflowInstanceResponse createWorkflowInstance(@RequestBody WorkflowInstanceRequest workflowInstanceRequest) {
        return workflowService.createWorkflowInstance(workflowInstanceRequest);
    }
}
