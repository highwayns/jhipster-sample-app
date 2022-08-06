package com.activiti6.mapper;

import java.util.List;

import com.activiti6.po.Permission;


public interface PermissionMapper {
	List<Permission> getPermissions();
	Permission getPermissionByname(String permissionname);
	void addpermission(String permissionname);
	void deletepermission(int pid);
	void deleteRole_permission(int permissionid);
}
