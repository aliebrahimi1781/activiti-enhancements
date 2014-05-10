package hu.palkonyves.activiti.delegation;

public enum DelegationOnResolve {
	RESOLVE("resolve"), COMPLETE("complete");

	private String value;

	private DelegationOnResolve(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static DelegationOnResolve fromString(String val) {
		if (RESOLVE.value.equals(val)) {
			return RESOLVE;
		}
		if (COMPLETE.value.equals(val)) {
			return COMPLETE;
		}
		throw new IllegalArgumentException(val + " is not a valid "
				+ DelegationOnResolve.class);
	}
}