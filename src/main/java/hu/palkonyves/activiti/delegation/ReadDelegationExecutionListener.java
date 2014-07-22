package hu.palkonyves.activiti.delegation;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class ReadDelegationExecutionListener implements
ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		if (execution instanceof ActivityExecution) {
			/*
			 * Execution will most probably be always ActivityExecution
			 */

			ActivityExecution activityExecution = (ActivityExecution) execution;
			PvmActivity activity = activityExecution.getActivity();
			DelegationExtension delegationExtension = (DelegationExtension) activity
					.getProperty(DelegationExtension.ACTIVITY_PROPERTY_NAME);

			if (delegationExtension != null) {
				// do things with the delegation...
			}

		}
	}

}
