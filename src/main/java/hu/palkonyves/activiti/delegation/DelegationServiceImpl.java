package hu.palkonyves.activiti.delegation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;

public class DelegationServiceImpl implements DelegationService {

	public static final String DELEGATION_REQUEST = "delegation_req";

	TaskService tasks;

	public DelegationServiceImpl(TaskService taskService) {
		tasks = taskService;
	}

	@Override
	public List<DelegationRequest> createUserRequest(String taskId,
			Collection<String> userIds) {

		List<DelegationRequest> res = new ArrayList<DelegationRequest>();

		for (String userId : userIds) {
			tasks.addUserIdentityLink(taskId, userId, DELEGATION_REQUEST);
			res.add(new DelegationRequest(userId, null, taskId,
					DelegationRequestIdentity.USER));
		}

		return res;
	}

	@Override
	public List<DelegationRequest> createGroupRequest(String taskId,
			Collection<String> groupIds) {

		List<DelegationRequest> res = new ArrayList<DelegationRequest>();

		for (String groupId : groupIds) {
			tasks.addGroupIdentityLink(taskId, groupId, DELEGATION_REQUEST);
			res.add(new DelegationRequest(null, groupId, taskId,
					DelegationRequestIdentity.GROUP));
		}

		return res;
	}

	@Override
	public void deleteUserRequests(String taskId, Collection<String> userIds) {
		for (String userId : userIds) {
			tasks.deleteUserIdentityLink(taskId, userId, DELEGATION_REQUEST);
		}
	}

	@Override
	public void deleteGroupRequests(String taskId, Collection<String> groupIds) {
		for (String groupId : groupIds) {
			tasks.deleteGroupIdentityLink(taskId, groupId, DELEGATION_REQUEST);
		}
	}

	public void revokeRequests(String taskId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delegate(String taskId, String userId) {
		List<IdentityLink> identityLinksForTask = tasks
				.getIdentityLinksForTask(taskId);

		for (IdentityLink identityLink : identityLinksForTask) {
			String type = identityLink.getType();
			String userId2 = identityLink.getUserId();
			if (DELEGATION_REQUEST.equals(type) && userId.equals(userId2)) {
				tasks.delegateTask(taskId, userId);
				revokeRequests(taskId);
				break;
			}
		}

		throw new IllegalStateException("User '" + userId
				+ "' does not have a delegation request");
	}

	@Override
	public List<DelegationRequest> getUserRequests(String userId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DelegationRequest> getGroupRequests(String groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DelegationRequest> getTaskRequests(String taskId) {

		List<DelegationRequest> res = new ArrayList<DelegationRequest>();

		List<IdentityLink> identityLinksForTask = tasks
				.getIdentityLinksForTask(taskId);
		for (IdentityLink identityLink : identityLinksForTask) {
			DelegationRequestIdentity identityType = getIdentityType(identityLink);
			DelegationRequest req = new DelegationRequest(
					identityLink.getUserId(), identityLink.getGroupId(),
					taskId, identityType);
			res.add(req);
		}

		return res;
	}

	protected DelegationRequestIdentity getIdentityType(IdentityLink link) {
		if (link.getUserId() == null || link.getUserId().isEmpty()
				&& link.getGroupId() != null && !link.getGroupId().isEmpty()) {
			return DelegationRequestIdentity.GROUP;
		}
		return DelegationRequestIdentity.USER;
	}

}
