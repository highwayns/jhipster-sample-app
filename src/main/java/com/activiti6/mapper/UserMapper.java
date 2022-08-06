package com.activiti6.mapper;

import java.util.List;

import com.activiti6.po.User;

public interface UserMapper {
	List<User> getusers();

	User getUserByid(int id);

	void deleteuser(int uid);

	void deleteuserrole(int uid);

	void adduser(User user);

	void updateByPrimaryKeySelective(User user);

	int getUidByusername(String username);
}
