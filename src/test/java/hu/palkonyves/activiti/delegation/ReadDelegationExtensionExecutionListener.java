package hu.palkonyves.activiti.delegation;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class ReadDelegationExtensionExecutionListener implements
ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		if (execution instanceof ActivityExecution) {
			/*
			 * Execution will most probably be always ActivityExecution
			 */

			ActivityExecution activityExecution = (ActivityExecution) execution;
			PvmActivity activity = activityExecution.getActivity();
			DelegationExtension delegation = (DelegationExtension) activity
					.getProperty(DelegationExtension.ACTIVITY_PROPERTY_NAME);

			if (delegation != null) {
				// do things with the delegation...
			}

		}
	}

}
