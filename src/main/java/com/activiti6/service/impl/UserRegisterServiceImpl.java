package com.activiti6.service.impl;

import com.activiti6.mapper.UserRegisterInfoMapper;
import com.activiti6.po.UserRegisterInfo;
import com.activiti6.service.UserRegisterService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service
public class UserRegisterServiceImpl implements UserRegisterService{
	@Autowired
	UserRegisterInfoMapper userRegisterInfoMapper;
	@Autowired
	IdentityService identityservice;
	@Autowired
	RuntimeService runtimeservice;
	@Autowired
	TaskService taskservice;
	
	public ProcessInstance startWorkflow(UserRegisterInfo userRegisterInfo, String userid, Map<String, Object> variables) {
		userRegisterInfo.setApply_time(new Date().toString());
		userRegisterInfo.setUser_id(userid);
		userRegisterInfoMapper.save(userRegisterInfo);
		String businesskey=String.valueOf(userRegisterInfo.getId());//使用user_register_info表的主键作为businesskey,连接业务数据和流程数据
		identityservice.setAuthenticatedUserId(userid);
		ProcessInstance instance=runtimeservice.startProcessInstanceByKey("userregiste",businesskey,variables);
		System.out.println(businesskey);
		String instanceid=instance.getId();
		userRegisterInfo.setProcess_instance_id(instanceid);
		userRegisterInfoMapper.updateByPrimaryKey(userRegisterInfo);
		return instance;
	}

	public List<UserRegisterInfo> getPageManagerTask(String userid,int firstrow,int rowcount) {
		List<UserRegisterInfo> results=new ArrayList<UserRegisterInfo>();
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateGroup("ManagerGroup").listPage(firstrow, rowcount);
		for(Task task:tasks){
			String instanceid=task.getProcessInstanceId();
			ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
			String businesskey=ins.getBusinessKey();
			UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
			a.setTask(task);
			results.add(a);
		}
		return results;
	}
	
	public int getAllManagertask(String userid) {
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateGroup("ManagerGroup").list();
		return tasks.size();
	}

	public UserRegisterInfo getUserRegisterInfo(int id) {
		UserRegisterInfo userRegisterInfo=userRegisterInfoMapper.getUserRegisterInfo(id);
		return userRegisterInfo;
	}

	public List<UserRegisterInfo> getPagerSeniorManagertask(String userid,int firstrow,int rowcount) {
		List<UserRegisterInfo> results=new ArrayList<UserRegisterInfo>();
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateGroup("SeniorManagerGroup").listPage(firstrow, rowcount);
		for(Task task:tasks){
			String instanceid=task.getProcessInstanceId();
			ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
			String businesskey=ins.getBusinessKey();
			UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
			a.setTask(task);
			results.add(a);
		}
		return results;
	}

	public int getAllSeniortask(String userid) {
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateGroup("SeniorManagerGroup").list();
		return tasks.size();
	}
	
	public List<UserRegisterInfo> getPageUpdatetask(String userid,int firstrow,int rowcount) {
		List<UserRegisterInfo> results=new ArrayList<UserRegisterInfo>();
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateOrAssigned(userid).taskName("upload user certificate").listPage(firstrow, rowcount);
		for(Task task:tasks){
			String instanceid=task.getProcessInstanceId();
			ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
			String businesskey=ins.getBusinessKey();
			UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
			a.setTask(task);
			results.add(a);
		}
		return results;
	}

	public int getAllUpdatetask(String userid) {
		List<Task> tasks=taskservice.createTaskQuery().taskCandidateOrAssigned(userid).taskName("Update").list();
		return tasks.size();
	}
	
	public void completeByManager(String taskid, String manager_check_time) {
		Task task=taskservice.createTaskQuery().taskId(taskid).singleResult();
		String instanceid=task.getProcessInstanceId();
		ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		String businesskey=ins.getBusinessKey();
		UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
		a.setManager_check_time(manager_check_time);
		userRegisterInfoMapper.updateByPrimaryKey(a);
		taskservice.complete(taskid);
	}

	public void completeBySeniorManager(String taskid, String seniorManager_check_time) {
		Task task=taskservice.createTaskQuery().taskId(taskid).singleResult();
		String instanceid=task.getProcessInstanceId();
		ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		String businesskey=ins.getBusinessKey();
		UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
		a.setSuperManager_check_time(seniorManager_check_time);
		userRegisterInfoMapper.updateByPrimaryKey(a);
		taskservice.complete(taskid);
	}

	public void updateReapply(String taskid, UserRegisterInfo userRegisterInfo,String reapply) {
		Task task=taskservice.createTaskQuery().taskId(taskid).singleResult();
		String instanceid=task.getProcessInstanceId();
		ProcessInstance ins=runtimeservice.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
		String businesskey=ins.getBusinessKey();
		UserRegisterInfo a=userRegisterInfoMapper.getUserRegisterInfo(Integer.parseInt(businesskey));
		a.setUsername(userRegisterInfo.getUsername());
		a.setPassword(userRegisterInfo.getPassword());
		a.setBirthday(userRegisterInfo.getBirthday());
		a.setTel(userRegisterInfo.getTel());
		a.setMail(userRegisterInfo.getMail());
		a.setUser_id(userRegisterInfo.getUser_id());
		a.setApply_time(userRegisterInfo.getApply_time());
		a.setRemarks(userRegisterInfo.getRemarks());
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("reapply", reapply);
		if(reapply.equals("true")){
			userRegisterInfoMapper.updateByPrimaryKey(a);
			taskservice.complete(taskid,variables);
		}else
			taskservice.complete(taskid,variables);
	}
	
	public List<String> getHighLightedFlows(  
	        ProcessDefinitionEntity processDefinitionEntity,  
	        List<HistoricActivityInstance> historicActivityInstances) {  
	  
	    List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
	    /*for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
	        ActivityImpl activityImpl = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i)  
	                        .getActivityId());// 得 到节点定义的详细信息  
	        List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
	        if ((i + 1) >= historicActivityInstances.size()) {  
	            break;  
	        }  
	        ActivityImpl sameActivityImpl1 = processDefinitionEntity  
	                .findActivity(historicActivityInstances.get(i + 1)  
	                        .getActivityId());// 将后面第一个节点放在时间相同节点的集合里  
	        sameStartTimeNodes.add(sameActivityImpl1);  
	        for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
	            HistoricActivityInstance activityImpl1 = historicActivityInstances  
	                    .get(j);// 后续第一个节点  
	            HistoricActivityInstance activityImpl2 = historicActivityInstances  
	                    .get(j + 1);// 后续第二个节点  
	            if (activityImpl1.getStartTime().equals(  
	                    activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存  
	                ActivityImpl sameActivityImpl2 = processDefinitionEntity  
	                        .findActivity(activityImpl2.getActivityId());  
	                sameStartTimeNodes.add(sameActivityImpl2);  
	            } else {// 有不相同跳出循环  
	                break;  
	            }  
	        }  
	        List<PvmTransition> pvmTransitions = activityImpl  
	                .getOutgoingTransitions();// 取出节点的所有出去的线  
	        for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历  
	            ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
	                    .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
	            if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
	                highFlows.add(pvmTransition.getId());  
	            }  
	        }  
	    }*/
	    return highFlows;  
	}  
}
