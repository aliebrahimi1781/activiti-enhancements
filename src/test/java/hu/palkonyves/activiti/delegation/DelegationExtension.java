package hu.palkonyves.activiti.delegation;

public class DelegationExtension {
	public static final String ACTIVITY_PROPERTY_NAME = "delegation_extension";

	private boolean active = true;
	private DelegationOnResolve onResolve = DelegationOnResolve.COMPLETE;

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
}