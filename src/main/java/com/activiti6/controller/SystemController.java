package com.activiti6.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activiti6.pagemodel.DataGrid;
import com.activiti6.pagemodel.Userinfo;
import com.activiti6.po.Permission;
import com.activiti6.po.Role;
import com.activiti6.po.User;
import com.activiti6.po.UserRole;
import com.activiti6.service.SystemService;
import io.swagger.annotations.Api;

@Api(value = "System_Manager")
@Controller
public class SystemController {
	@Autowired
	SystemService systemservice;
	
	@RequestMapping(value="/system_manager/user",method=RequestMethod.GET)
	String useradmin(){
		return "system_manager/user/index";
	}
	
	@RequestMapping(value="/system_manager/role",method=RequestMethod.GET)
	String roleadmin(){
		return "system_manager/role/index";
	}
	
	@RequestMapping(value="/system_manager/user_role",method=RequestMethod.GET)
	String permissionadmin(){
		return "system_manager/user_role/index";
	}
	
	@RequestMapping(value="/userlist",method=RequestMethod.GET)
	@ResponseBody
	DataGrid<Userinfo> userlist(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		int total=systemservice.getallusers().size();
		List<User> userlist=systemservice.getpageusers(current,rowCount);
		List<Userinfo> users=new ArrayList<Userinfo>();
		for(User user:userlist){
			Userinfo u=new Userinfo();
			int userid = user.getUid();
			u.setId(userid);
			u.setAge(user.getAge());
			u.setPassword(user.getPassword());
			u.setTel(user.getTel());
			u.setUsername(user.getUsername());
			String rolename="";
			List<UserRole> ur = systemservice.listRolesByUserid(userid);
			if( ur != null && ur.size() > 0 ){
				for( UserRole userole : ur ){
					int roleid = userole.getRoleid();
					Role r = systemservice.getRolebyid(roleid);
					rolename=rolename+","+r.getRolename();
				}
				if(rolename.length()>0)
					rolename=rolename.substring(1,rolename.length());
				u.setRolelist(rolename);
			}
			users.add(u);
		}
		DataGrid<Userinfo> grid=new DataGrid<Userinfo>();
		grid.setCurrent(current);
		grid.setRows(users);
		grid.setRowCount(rowCount);
		grid.setTotal(total);
		return grid;
	}
	
	@RequestMapping(value="/user/{uid}",method=RequestMethod.GET)
	@ResponseBody
	User getuserinfo(@PathVariable("uid") int userid){
		return systemservice.getUserByid(userid);
	}
	
	@RequestMapping(value="/rolelist",method=RequestMethod.GET)
	@ResponseBody
	List<Role> getroles(){
		return systemservice.getRoles();
	}
	
	@RequestMapping(value="/roles",method=RequestMethod.GET)
	@ResponseBody
	DataGrid<Role> getallroles(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		List<Role> roles=systemservice.getRoleinfo();
		List<Role> rows=systemservice.getpageRoleinfo(current, rowCount);
		DataGrid<Role> grid=new DataGrid<Role>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(roles.size());
		grid.setRows(rows);
		return grid;
	}
	
	@RequestMapping(value="/deleteuser/{uid}",method=RequestMethod.GET)
	String deleteuser(@PathVariable("uid")int uid){
		systemservice.deleteuser(uid);
		return "system_manager/user/index";
	}
	
	@RequestMapping(value="/adduser",method=RequestMethod.GET)
	String adduser(@ModelAttribute("user")User user,@RequestParam(value="rolename[]",required = false)String[] rolename){
		if(rolename==null)
			systemservice.adduser(user);
		else
			systemservice.adduser(user, rolename);
		return "system_manager/user/index";
	}
	
	@RequestMapping(value="/updateuser/{uid}",method=RequestMethod.POST)
	String updateuser(@PathVariable("uid")int uid,@ModelAttribute("user")User user,@RequestParam(value="rolename[]",required = false)String[] rolename){
		systemservice.updateuser(uid, user, rolename);
		return "system_manager/user/index";
	}
	
	
	@RequestMapping(value="/permissionlist",method=RequestMethod.GET)
	@ResponseBody
	List<Permission> getPermisions(){
		return systemservice.getPermisions();
	}
	
	@RequestMapping(value="/addrole",method=RequestMethod.GET)
	String addrole(@RequestParam("rolename") String rolename,@RequestParam(value="permissionname[]")String[] permissionname){
		Role r=new Role();
		r.setRolename(rolename);
		systemservice.addrole(r, permissionname);
		return "system_manager/role/index";
	}
	
	@RequestMapping(value="/deleterole/{rid}",method=RequestMethod.GET)
	String deleterole(@PathVariable("rid")int rid){
		systemservice.deleterole(rid);
		return "system_manager/role/index";
	}
	
	@RequestMapping(value="/roleinfo/{rid}",method=RequestMethod.GET)
	@ResponseBody
	Role getRolebyrid(@PathVariable("rid")int rid){
		return systemservice.getRolebyid(rid);
	}
	
	@RequestMapping(value="/updaterole/{rid}",method=RequestMethod.POST)
	String updaterole(@PathVariable("rid")int rid,@RequestParam(value="permissionname[]")String[] permissionnames){
		systemservice.deleterolepermission(rid);
		systemservice.updaterole(rid, permissionnames);
		return "system_manager/role/index";
	}
	
	
	@RequestMapping(value="/permissions",method=RequestMethod.GET)
	@ResponseBody
	DataGrid<Permission> getpermissions(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		List<Permission> p=systemservice.getPermisions();
		List<Permission> list=systemservice.getPagePermisions(current, rowCount);
		DataGrid<Permission> grid=new DataGrid<Permission>();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setTotal(p.size());
		grid.setRows(list);
		return grid;
	}
	
	@RequestMapping(value="/addpermission",method=RequestMethod.POST)
	String addpermission(@RequestParam("permissionname") String permissionname){
		systemservice.addPermission(permissionname);
		return "system_manager/user_role/index";
	}
	
	
	@RequestMapping(value="/deletepermission/{pid}",method=RequestMethod.GET)
	String deletepermission(@PathVariable("pid") int pid){
		systemservice.deletepermission(pid);
		return "system_manager/user_role/index";
	}
}
