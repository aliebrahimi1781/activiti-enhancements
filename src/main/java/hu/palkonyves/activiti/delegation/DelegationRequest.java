package hu.palkonyves.activiti.delegation;

public class DelegationRequest {

	protected String userId;
	protected String groupId;
	protected String taskId;
	protected DelegationRequestIdentity identityType;

	public DelegationRequest(String userId, String groupId, String taskId,
	        DelegationRequestIdentity identityType) {
		this.userId = userId;
		this.groupId = groupId;
		this.taskId = taskId;
		this.identityType = identityType;
	}

	public static DelegationRequest createForUser(String userId, String taskId) {
		return new DelegationRequest(userId, null, taskId,
		        DelegationRequestIdentity.USER);
	}

	public static DelegationRequest createForGroup(String groupId, String taskId) {
		return new DelegationRequest(null, groupId, taskId,
		        DelegationRequestIdentity.GROUP);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public DelegationRequestIdentity getIdentityType() {
		return identityType;
	}

	public void setIdentityType(DelegationRequestIdentity identityType) {
		this.identityType = identityType;
	}
}
