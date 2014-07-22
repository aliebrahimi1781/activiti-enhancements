package hu.palkonyves.activiti.delegation;

import org.activiti.engine.delegate.Expression;

/**
 * Extension used for PVM
 *
 * @author Pali
 *
 */
public class DelegationExtension {
	public static final String ACTIVITY_PROPERTY_NAME = "delegation_extension";

	private boolean active = true;
	private DelegationOnResolve onResolve = DelegationOnResolve.COMPLETE;
	private Expression isDelegatableExpression;

	public DelegationExtension() {
	}

	public DelegationExtension(boolean active, DelegationOnResolve onResolve) {
		this.setActive(active);
		this.setOnResolve(onResolve);
	}

	public DelegationOnResolve getOnResolve() {
		return onResolve;
	}

	public void setOnResolve(DelegationOnResolve onResolve) {
		this.onResolve = onResolve;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Expression getIsDelegatableExpression() {
		return isDelegatableExpression;
	}

	public void setIsDelegatableExpression(Expression isDelegatableExpression) {
		this.isDelegatableExpression = isDelegatableExpression;
	}
}