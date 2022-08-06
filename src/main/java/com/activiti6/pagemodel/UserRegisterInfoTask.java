package com.activiti6.pagemodel;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ユーザー登録情報タスク")
public class UserRegisterInfoTask{
	@ApiModelProperty("primary key")
	int id;
	@ApiModelProperty("process_instance_id")
	String process_instance_id;
	@ApiModelProperty("username")
	String username;
	@ApiModelProperty("password")
	String password;
	@ApiModelProperty("birthday")
	String birthday;
	@ApiModelProperty("tel")
	String tel;
	@ApiModelProperty("mail")
	String mail;
	@ApiModelProperty("user_id")
	String user_id;
	@ApiModelProperty("apply_time")
	String apply_time;
	@ApiModelProperty("manager_check_time")
	String manager_check_time;
	@ApiModelProperty("superManager_check_time")
	String superManager_check_time;
	@ApiModelProperty("remarks")
	String remarks;
	@ApiModelProperty("任务id")
	String taskid;
	@ApiModelProperty("任务名")
	String taskname;
	@ApiModelProperty("流程实例id")
	String processinstanceid;
	@ApiModelProperty("流程定义id")
	String processdefid;
	@ApiModelProperty("任务创建时间")
	Date taskcreatetime;

	public int getId() {
		return id;
	}

	public String getProcess_instance_id() { return process_instance_id; }
	public void setProcess_instance_id(String process_instance_id) { this.process_instance_id = process_instance_id; }

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() { return mail; }
	public void setMail(String mail) { this.mail = mail; }

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	public String getManager_check_time() {
		return manager_check_time;
	}
	public void setManager_check_time(String manager_check_time) { this.manager_check_time = manager_check_time; }

	public String getSuperManager_check_time() {
		return superManager_check_time;
	}
	public void setSuperManager_check_time(String superManager_check_time) { this.superManager_check_time = superManager_check_time; }

	public String getRemarks() { return remarks; }
	public void setRemarks(String remarks) { this.remarks = remarks; }
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getProcessinstanceid() {
		return processinstanceid;
	}
	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}
	public String getProcessdefid() {
		return processdefid;
	}
	public void setProcessdefid(String processdefid) {
		this.processdefid = processdefid;
	}
	public Date getTaskcreatetime() {
		return taskcreatetime;
	}
	public void setTaskcreatetime(Date taskcreatetime) {
		this.taskcreatetime = taskcreatetime;
	}

}
