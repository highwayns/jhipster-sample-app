package com.activiti6.service;

import com.activiti6.po.UserRegisterInfo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;


public interface UserRegisterService {
	public ProcessInstance startWorkflow(UserRegisterInfo userRegisterInfo,String userid,Map<String,Object> variables);
	public List<UserRegisterInfo> getPageManagerTask(String userid,int firstrow,int rowcount);
	public int getAllManagertask(String userid);
	public UserRegisterInfo getUserRegisterInfo(int id);
	public List<UserRegisterInfo> getPagerSeniorManagertask(String userid,int firstrow,int rowcount);
	public int getAllSeniortask(String userid);
	public List<UserRegisterInfo> getPageUpdatetask(String userid,int firstrow,int rowcount);
	public int getAllUpdatetask(String userid);
	public void completeByManager(String taskid, String manager_check_time);
	public void completeBySeniorManager(String taskid, String seniorManager_check_time);
	public void updateReapply(String taskid, UserRegisterInfo userRegisterInfo,String reapply);
	public List<String> getHighLightedFlows(ProcessDefinitionEntity deployedProcessDefinition,List<HistoricActivityInstance> historicActivityInstances);
}
