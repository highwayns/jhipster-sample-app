package com.activiti6.controller;

import com.activiti6.pagemodel.Process;
import com.activiti6.pagemodel.*;
import com.activiti6.po.*;
import com.activiti6.service.ProcessInstanceService;
import com.activiti6.service.SystemService;
import com.activiti6.service.UserRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.activiti6.service.ProcessInstanceService;

@Api(value = "user_register")
@Controller
public class userRegisterController {
	@Autowired
	RepositoryService rep;
	@Autowired
	RuntimeService runservice;
	@Autowired
	FormService formservice;
	@Autowired
	IdentityService identityservice;
	@Autowired
	TaskService taskservice;
	@Autowired
	HistoryService histiryservice;
	@Autowired
	SystemService systemservice;
	@Autowired
	UserRegisterService userRegisterService;
    @Autowired
    private ProcessInstanceService processInstanceService;

	@RequestMapping(value = "/user/input_infor", method = RequestMethod.GET)
	public String inputInfo() {
		return "user/input_infor/index";
	}

	@RequestMapping(value = "/user/update_infor", method = RequestMethod.GET)
	public String updateInfo() {
		return "user/update_infor/index";
	}

	@RequestMapping(value = "/user/upload_user_certificate", method = RequestMethod.GET)
	public String uploadUserCertificate() {
		return "user/upload_user_certificate/index";
	}

	@RequestMapping(value = "/startUserRegister", method = RequestMethod.POST)
	@ResponseBody
	public MSG start_user_register(UserRegisterInfo userRegisterInfo, HttpSession session) {
		String userid = (String) session.getAttribute("username");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyuserid", userid);
		ProcessInstance ins = userRegisterService.startWorkflow(userRegisterInfo, userid, variables);
		System.out.println("user register is started. ID:" + ins.getId());
		return new MSG("sucess");
	}

	@ApiOperation("get manager task list")
	@RequestMapping(value = "/managertasklist", produces = {
			"application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserRegisterInfoTask> getManagerTaskList(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		DataGrid<UserRegisterInfoTask> grid = new DataGrid<UserRegisterInfoTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(0);
		grid.setRows(new ArrayList<UserRegisterInfoTask>());
		String userid = (String) session.getAttribute("username");
		int uid = systemservice.getUidByusername(userid);
		User user = systemservice.getUserByid(uid);
		List<User_role> userroles = user.getUser_roles();
		if (userroles == null)
			return grid;
		boolean flag = false;// 默认没有权限
		for (int k = 0; k < userroles.size(); k++) {
			User_role ur = userroles.get(k);
			Role r = ur.getRole();
			int roleid = r.getRid();
			Role role = systemservice.getRolebyid(roleid);
			List<Role_permission> p = role.getRole_permission();
			for (int j = 0; j < p.size(); j++) {
				Role_permission rp = p.get(j);
				Permission permission = rp.getPermission();
				if (permission.getPermissionname().equals("Manager"))
					flag = true;
				else
					continue;
			}
		}
		if (flag == false)// 无权限
		{
			return grid;
		} else {
			int firstrow = (current - 1) * rowCount;
			List<UserRegisterInfo> results = userRegisterService.getPageManagerTask(userid, firstrow, rowCount);
			int totalsize = userRegisterService.getAllManagertask(userid);
			List<UserRegisterInfoTask> tasks = new ArrayList<UserRegisterInfoTask>();
			for (UserRegisterInfo apply : results) {
				UserRegisterInfoTask task = new UserRegisterInfoTask();
				task.setUsername(apply.getUsername());
				task.setPassword(apply.getPassword());
				task.setBirthday(apply.getBirthday());
				task.setTel(apply.getTel());
				task.setMail(apply.getMail());
				task.setUser_id(apply.getUser_id());
				task.setApply_time(apply.getApply_time());
				task.setRemarks(apply.getRemarks());
				task.setTaskcreatetime(apply.getTask().getCreateTime());
				task.setTaskid(apply.getTask().getId());
				task.setTaskname(apply.getTask().getName());
				tasks.add(task);
			}
			grid.setRowCount(rowCount);
			grid.setCurrent(current);
			grid.setTotal(totalsize);
			grid.setRows(tasks);
			return grid;
		}
	}

	@ApiOperation("get senior manager task list")
	@RequestMapping(value = "/seniormanagertasklist", produces = {
			"application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserRegisterInfoTask> getSeniorManagerTaskList(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		DataGrid<UserRegisterInfoTask> grid = new DataGrid<UserRegisterInfoTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(0);
		grid.setRows(new ArrayList<UserRegisterInfoTask>());
		String userid = (String) session.getAttribute("username");
		int uid = systemservice.getUidByusername(userid);
		User user = systemservice.getUserByid(uid);
		List<User_role> userroles = user.getUser_roles();
		if (userroles == null)
			return grid;
		boolean flag = false;// 默认没有权限
		for (int k = 0; k < userroles.size(); k++) {
			User_role ur = userroles.get(k);
			Role r = ur.getRole();
			int roleid = r.getRid();
			Role role = systemservice.getRolebyid(roleid);
			List<Role_permission> p = role.getRole_permission();
			for (int j = 0; j < p.size(); j++) {
				Role_permission rp = p.get(j);
				Permission permission = rp.getPermission();
				if (permission.getPermissionname().equals("SeniorManager"))
					flag = true;
				else
					continue;
			}
		}
		if (flag == false)// 无权限
		{
			return grid;
		} else {
			int firstrow = (current - 1) * rowCount;
			List<UserRegisterInfo> results = userRegisterService.getPagerSeniorManagertask(userid, firstrow, rowCount);
			int totalsize = userRegisterService.getAllSeniortask(userid);
			List<UserRegisterInfoTask> tasks = new ArrayList<UserRegisterInfoTask>();
			for (UserRegisterInfo apply : results) {
				UserRegisterInfoTask task = new UserRegisterInfoTask();
				task.setUsername(apply.getUsername());
				task.setPassword(apply.getPassword());
				task.setBirthday(apply.getBirthday());
				task.setTel(apply.getTel());
				task.setMail(apply.getMail());
				task.setUser_id(apply.getUser_id());
				task.setApply_time(apply.getApply_time());
				task.setRemarks(apply.getRemarks());
				task.setTaskcreatetime(apply.getTask().getCreateTime());
				task.setTaskid(apply.getTask().getId());
				task.setTaskname(apply.getTask().getName());
				tasks.add(task);
			}
			grid.setRowCount(rowCount);
			grid.setCurrent(current);
			grid.setTotal(totalsize);
			grid.setRows(tasks);
			return grid;
		}
	}

	@RequestMapping(value = "/getupdatetasklist", produces = {
			"application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserRegisterInfoTask> getUpdateTasklist(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		int firstrow = (current - 1) * rowCount;
		String userid = (String) session.getAttribute("username");
		List<UserRegisterInfo> results = userRegisterService.getPageUpdatetask(userid, firstrow, rowCount);
		int totalsize = userRegisterService.getAllUpdatetask(userid);
		List<UserRegisterInfoTask> tasks = new ArrayList<UserRegisterInfoTask>();
		for (UserRegisterInfo apply : results) {
			UserRegisterInfoTask task = new UserRegisterInfoTask();
			task.setUsername(apply.getUsername());
			task.setPassword(apply.getPassword());
			task.setBirthday(apply.getBirthday());
			task.setTel(apply.getTel());
			task.setMail(apply.getMail());
			task.setUser_id(apply.getUser_id());
			task.setApply_time(apply.getApply_time());
			task.setRemarks(apply.getRemarks());
			task.setTaskcreatetime(apply.getTask().getCreateTime());
			task.setTaskid(apply.getTask().getId());
			task.setTaskname(apply.getTask().getName());
			tasks.add(task);
		}
		DataGrid<UserRegisterInfoTask> grid = new DataGrid<UserRegisterInfoTask>();
		grid.setRowCount(rowCount);
		grid.setCurrent(current);
		grid.setTotal(totalsize);
		grid.setRows(tasks);
		return grid;
	}

	@RequestMapping(value = "/gettaskbyid", method = RequestMethod.POST)
	@ResponseBody
	public UserRegisterInfo gettaskbyid(@RequestParam("taskid") String taskid, HttpServletResponse response) {
		Task task = taskservice.createTaskQuery().taskId(taskid).singleResult();
		ProcessInstance process = runservice.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.singleResult();
		UserRegisterInfo userRegisterInfo = userRegisterService.getUserRegisterInfo(new Integer(process.getBusinessKey()));
		return userRegisterInfo;
	}

	@RequestMapping(value = "/manager/check_user_infor", method = RequestMethod.GET)
	String manager_url() {
		return "/manager/check_user_infor/index";
	}

	@RequestMapping(value = "/task/completebyuser/{taskid}", method = RequestMethod.POST)
	@ResponseBody
	public MSG userComplete(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req) {
		String userid = (String) session.getAttribute("username");
		Map<String, Object> variables = new HashMap<String, Object>();
		taskservice.claim(taskid, userid);
		taskservice.complete(taskid, variables);
		return new MSG("success");
	}

	@RequestMapping(value = "/task/userCertificateUpload/{taskid}", method = RequestMethod.POST)
	@ResponseBody
	public MSG userCertificateUpload(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req) {
		String userid = (String) session.getAttribute("username");
		Map<String, Object> variables = new HashMap<String, Object>();
		processInstanceService.initProcessConfig();
		taskservice.claim(taskid, userid);
		taskservice.complete(taskid, variables);
		return new MSG("success");
	}

	@RequestMapping(value = "/task/completebymanager/{taskid}", method = RequestMethod.POST)
	@ResponseBody
	public MSG managerComplete(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req) {
		String userid = (String) session.getAttribute("username");
		Map<String, Object> variables = new HashMap<String, Object>();
		String approve = req.getParameter("managerapprove");
		variables.put("managerapprove", approve);
		taskservice.claim(taskid, userid);
		taskservice.complete(taskid, variables);
		return new MSG("success");
	}

	@RequestMapping(value = "/senior_manager/check_user_infor", method = RequestMethod.GET)
	String senior_manager_url() {
		return "/senior_manager/check_user_infor/index";
	}

	@RequestMapping(value = "/task/completebysenior/{taskid}", method = RequestMethod.POST)
	@ResponseBody
	public MSG seniorComplete(HttpSession session, @PathVariable("taskid") String taskid, HttpServletRequest req) {
		String userid = (String) session.getAttribute("username");
		Map<String, Object> variables = new HashMap<String, Object>();
		String approve = req.getParameter("seniorapprove");
		variables.put("seniorapprove", approve);
		taskservice.claim(taskid, userid);
		taskservice.complete(taskid, variables);
		return new MSG("success");
	}

	@RequestMapping(value = "/task/updatereapply/{taskid}", method = RequestMethod.POST)
	@ResponseBody
	public MSG updateReapply(@PathVariable("taskid") String taskid, @ModelAttribute("userRegisterInfo") UserRegisterInfo userRegisterInfo,
			@RequestParam("reapply") String reapply) {
				userRegisterService.updateReapply(taskid, userRegisterInfo, reapply);
		return new MSG("success");
	}

	@RequestMapping(value = "/involvedprocesslist", method = RequestMethod.POST) //
	@ResponseBody
	public DataGrid<RunningProcess> allexeution(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		int firstrow = (current - 1) * rowCount;
		String userid = (String) session.getAttribute("username");
		ProcessInstanceQuery query = runservice.createProcessInstanceQuery();
		int total = (int) query.count();
		List<ProcessInstance> a = query.processDefinitionKey("userregiste").involvedUser(userid).listPage(firstrow, rowCount);
		List<RunningProcess> list = new ArrayList<RunningProcess>();
		for (ProcessInstance p : a) {
			RunningProcess process = new RunningProcess();
			process.setActivityid(p.getActivityId());
			process.setBusinesskey(p.getBusinessKey());
			process.setExecutionid(p.getId());
			process.setProcessInstanceid(p.getProcessInstanceId());
			list.add(process);
		}
		DataGrid<RunningProcess> grid = new DataGrid<RunningProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}

	@RequestMapping(value = "/getfinishprocesslist", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<HistoryProcess> getHistory(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		String userid = (String) session.getAttribute("username");
		HistoricProcessInstanceQuery process = histiryservice.createHistoricProcessInstanceQuery()
				.processDefinitionKey("userregiste").startedBy(userid).finished();
		int total = (int) process.count();
		int firstrow = (current - 1) * rowCount;
		List<HistoricProcessInstance> info = process.listPage(firstrow, rowCount);
		List<HistoryProcess> list = new ArrayList<HistoryProcess>();
		for (HistoricProcessInstance history : info) {
			HistoryProcess his = new HistoryProcess();
			String bussinesskey = history.getBusinessKey();
			UserRegisterInfo userRegisterInfo = userRegisterService.getUserRegisterInfo(Integer.parseInt(bussinesskey));
			his.setUserRegisterInfo(userRegisterInfo);
			his.setBusinessKey(bussinesskey);
			his.setProcessDefinitionId(history.getProcessDefinitionId());
			list.add(his);
		}
		DataGrid<HistoryProcess> grid = new DataGrid<HistoryProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}

	@RequestMapping(value = "/historyprocesslist", method = RequestMethod.GET)
	public String history() {
		return "activiti/historyprocess";
	}

	@RequestMapping(value = "/getprocessinfo", method = RequestMethod.POST)
	@ResponseBody
	public List<HistoricActivityInstance> processinfo(@RequestParam("instanceid") String instanceid) {
		List<HistoricActivityInstance> his = histiryservice.createHistoricActivityInstanceQuery()
				.processInstanceId(instanceid).orderByHistoricActivityInstanceStartTime().asc().list();
		return his;
	}

	@RequestMapping(value = "/processhislist", method = RequestMethod.POST)
	@ResponseBody
	public List<HistoricActivityInstance> processhis(@RequestParam("ywh") String ywh) {
		String instanceid = histiryservice.createHistoricProcessInstanceQuery().processDefinitionKey("userregiste")
				.processInstanceBusinessKey(ywh).singleResult().getId();
		List<HistoricActivityInstance> his = histiryservice.createHistoricActivityInstanceQuery()
				.processInstanceId(instanceid).orderByHistoricActivityInstanceStartTime().asc().list();
		return his;
	}

	@RequestMapping(value = "traceprocesslist/{executionid}", method = RequestMethod.GET)
	public void traceprocess(@PathVariable("executionid") String executionid, HttpServletResponse response)
			throws Exception {
		ProcessInstance process = runservice.createProcessInstanceQuery().processInstanceId(executionid).singleResult();
		BpmnModel bpmnmodel = rep.getBpmnModel(process.getProcessDefinitionId());
		List<String> activeActivityIds = runservice.getActiveActivityIds(executionid);
		DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();
		// 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
		List<HistoricActivityInstance> historicActivityInstances = histiryservice.createHistoricActivityInstanceQuery()
				.executionId(executionid).orderByHistoricActivityInstanceStartTime().asc().list();
		// 计算活动线
		List<String> highLightedFlows = userRegisterService
				.getHighLightedFlows(
						(ProcessDefinitionEntity) ((RepositoryServiceImpl) rep)
								.getDeployedProcessDefinition(process.getProcessDefinitionId()),
						historicActivityInstances);

		InputStream in = gen.generateDiagram(bpmnmodel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null,
				null, 1.0);
		// InputStream in=gen.generateDiagram(bpmnmodel, "png",
		// activeActivityIds);
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(in, output);
	}


	@RequestMapping(value = "setupprocesslist", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RunningProcess> setupprocess(HttpSession session, @RequestParam("current") int current,
			@RequestParam("rowCount") int rowCount) {
		int firstrow = (current - 1) * rowCount;
		String userid = (String) session.getAttribute("username");
		System.out.print(userid);
		ProcessInstanceQuery query = runservice.createProcessInstanceQuery();
		int total = (int) query.count();
		List<ProcessInstance> a = query.processDefinitionKey("userregiste").involvedUser(userid).listPage(firstrow, rowCount);
		List<RunningProcess> list = new ArrayList<RunningProcess>();
		for (ProcessInstance p : a) {
			RunningProcess process = new RunningProcess();
			process.setActivityid(p.getActivityId());
			process.setBusinesskey(p.getBusinessKey());
			process.setExecutionid(p.getId());
			process.setProcessInstanceid(p.getProcessInstanceId());
			UserRegisterInfo l = userRegisterService.getUserRegisterInfo(Integer.parseInt(p.getBusinessKey()));
			if (l.getUser_id().equals(userid))
				list.add(process);
			else
				continue;
		}
		DataGrid<RunningProcess> grid = new DataGrid<RunningProcess>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		grid.setRows(list);
		return grid;
	}
}
