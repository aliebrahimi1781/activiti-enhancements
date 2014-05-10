package hu.palkonyves.activiti.delegation;

import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class DelegationBpmnParseHandler extends
AbstractBpmnParseHandler<UserTask> {

	public static final DelegationExtension DEFAULT_DELEGATION_EXTENSION = new DelegationExtension();

	public static final String BPMN_CUSTOM_NAMESPACE = "http://custom.org/delegation";

	public static final String BPMN_ELEMENT_DELEGATION = "delegation";

	public static final String BPMN_ATTRIBUTE_ON_RESOLVE = "onResolve";

	public static final String BPMN_ATTRIBUTE_ON_DISABLED = "disabled";

	@Override
	protected Class<? extends UserTask> getHandledType() {
		return UserTask.class;
	}

	@Override
	protected void executeParse(BpmnParse bpmnParse, UserTask serviceTask) {
		ActivityImpl activity = findActivity(bpmnParse, serviceTask.getId());
		Map<String, List<ExtensionElement>> extensionElements = serviceTask
				.getExtensionElements();

		List<ExtensionElement> list = extensionElements.get("delegation");

		DelegationExtension processDelegation = parseDelegationExtension(serviceTask);

		activity.setProperty(DelegationExtension.ACTIVITY_PROPERTY_NAME,
				processDelegation);

	}

	DelegationExtension parseDelegationExtension(BaseElement b) {
		Map<String, List<ExtensionElement>> extensionElements = b
				.getExtensionElements();

		List<ExtensionElement> list = extensionElements.get("delegation");

		DelegationExtension processDelegation = DEFAULT_DELEGATION_EXTENSION;
		for (ExtensionElement delegationElement : list) {
			processDelegation = processDelegation(delegationElement);

			/*
			 * I dunno, maybe later there will be more delegate elements?
			 */
			break;
		}

		return processDelegation;
	}

	private DelegationExtension processDelegation(
			ExtensionElement extensionElement) {

		String strOnResolve = getAttributeValue(
				extensionElement.getAttributes(), null,
				BPMN_ATTRIBUTE_ON_RESOLVE);
		String strDisabled = getAttributeValue(
				extensionElement.getAttributes(), null,
				BPMN_ATTRIBUTE_ON_DISABLED);

		boolean enabled = true;
		if (strDisabled != null) {
			// disable if attribute presents
			enabled = false;
		}

		if (strOnResolve == null) {
			// if not presented then default is to complete
			strOnResolve = DelegationOnResolve.COMPLETE.toString();
		}

		DelegationOnResolve onResolve = DelegationOnResolve
				.fromString(strOnResolve);

		DelegationExtension delegationExtension = new DelegationExtension();
		delegationExtension.setActive(enabled);
		delegationExtension.setOnResolve(onResolve);

		return delegationExtension;
	}

	public String getAttributeValue(
			Map<String, List<ExtensionAttribute>> attributeMap,
			String namespace, String name) {
		List<ExtensionAttribute> attributes = attributeMap.get(name);
		if (attributes != null && !attributes.isEmpty()) {
			for (ExtensionAttribute attribute : attributes) {
				if (namespace == attribute.getNamespace()) {
					// null == null ?
					return attribute.getValue();
				}
				if (namespace.equals(attribute.getNamespace())) {
					return attribute.getValue();
				}
			}
		}
		return null;
	}

}
