package com.activiti6.po;

import java.io.Serializable;

import org.activiti.engine.task.Task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ユーザー登録情報")
public class UserRegisterInfo implements Serializable{
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
	Task task;

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

	public Task getTask() { return task; }
	public void setTask(Task task) { this.task = task; }

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
}
