package com.activiti6.mapper;

import com.activiti6.po.UserRegisterInfo;

public interface UserRegisterInfoMapper {
	void save(UserRegisterInfo userRegisterInfo);

	UserRegisterInfo getUserRegisterInfo(int id);

	int updateByPrimaryKey(UserRegisterInfo record);
}
