package com.activiti6.mapper;

import com.activiti6.po.User;

public interface LoginMapper {
	User getpwdbyname(String name);
}
