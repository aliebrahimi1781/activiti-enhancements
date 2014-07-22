package hu.palkonyves.activiti.delegation;

import java.util.Collection;
import java.util.List;

public interface DelegationService {

	public List<DelegationRequest> createUserRequest(String taskId,
			Collection<String> userId);

	public List<DelegationRequest> createGroupRequest(String taskId,
			Collection<String> groupId);

	public void deleteUserRequests(String taskId, Collection<String> userId);

	public void deleteGroupRequests(String taskId, Collection<String> groupId);

	public void delegate(String taskId, String userId);

	public List<DelegationRequest> getUserRequests(String userId);

	public List<DelegationRequest> getGroupRequests(String groupId);

	public List<DelegationRequest> getTaskRequests(String taskId);
}
