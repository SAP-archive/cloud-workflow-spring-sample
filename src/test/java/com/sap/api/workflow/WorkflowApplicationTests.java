package com.sap.api.workflow;

import com.sap.api.workflow.controller.WorkflowController;
import com.sap.api.workflow.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WorkflowApplicationTests {

	@Autowired
	private WorkflowController workflowController;

	@Autowired
	private HomeController homeController;

	@Test
	void contextLoads() {
		assertThat(workflowController).isNotNull();
		assertThat(homeController).isNotNull();
	}
}
