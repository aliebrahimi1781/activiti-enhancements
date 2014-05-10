package hu.palkonyves.activiti.delegation;

import java.util.Map;

import org.activiti.engine.impl.cmd.CompleteTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.DelegationState;

public class ResolveOrCompleteTaskCmd extends CompleteTaskCmd {

	public ResolveOrCompleteTaskCmd(String taskId, Map<String, Object> variables) {
		super(taskId, variables);
	}

	public ResolveOrCompleteTaskCmd(String taskId,
			Map<String, Object> variables, boolean localScope) {
		super(taskId, variables, localScope);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		if (variables != null) {
			if (localScope) {
				task.setVariablesLocal(variables);
			} else {
				task.setExecutionVariables(variables);
			}
		}

		if (task.getDelegationState() != null
				&& task.getDelegationState().equals(DelegationState.PENDING)) {
			/*
			 * If in delegation then check what was the defined behavior for in
			 * the BPMN
			 */

			ExecutionEntity execution = task.getExecution();
			ActivityImpl activity = execution.getActivity();
			DelegationExtension delegation = (DelegationExtension) activity
			        .getProperty(DelegationExtension.ACTIVITY_PROPERTY_NAME);

			DelegationOnResolve onResolve = delegation.getOnResolve();

			if (!delegation.isActive()) {
				/*
				 * Delegation extension was set to disabled this means we go
				 * with the default activity way
				 */

				task.complete();
			}
			if (DelegationOnResolve.COMPLETE.equals(onResolve)) {
				/*
				 * Resolve the task first then complete it
				 */

				String currentAssignee = task.getAssignee();
				task.resolve();
				task.setAssignee(currentAssignee);
				task.complete();
			} else if (DelegationOnResolve.RESOLVE.equals(onResolve)) {
				// resolve the task
				task.resolve();
			} else {

				/*
				 * Make sure if the DelegationOnResolve changes we will
				 * eventually handle the new case
				 */

				throw new IllegalStateException(DelegationOnResolve.class
						+ " was set to " + onResolve
						+ " but this behavior is not implemented."
						+ " Activity id: " + activity.getId()
						+ ", process key: "
						+ activity.getProcessDefinition().getKey());
			}

			return null;
		} else {
			task.complete();
			return null;
		}

	}

}
