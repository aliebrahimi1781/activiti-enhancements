package hu.palkonyves.activiti.delegation;

import static org.junit.Assert.assertEquals;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.junit.Test;

public class DelegateExtensionTest extends AbstractConverterTest {

	@Test
	public void test() throws Exception {
		BpmnModel bpmn = readXMLFile();
		FlowElement userTask = bpmn.getMainProcess()
				.getFlowElement("usertask1");

		DelegationBpmnParseHandler parseHandler = new DelegationBpmnParseHandler();

		DelegationExtension parseDelegationExtension = parseHandler
				.parseDelegationExtension(userTask);

		assertEquals(DelegationOnResolve.RESOLVE,
		        parseDelegationExtension.getOnResolve());
		assertEquals(true, parseDelegationExtension.isActive());
	}

	@Override
	protected String getResource() {
		return "delegationextensionsmodel.bpmn";
	}

}
