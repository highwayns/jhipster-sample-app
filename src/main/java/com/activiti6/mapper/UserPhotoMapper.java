package com.activiti6.mapper;

import com.activiti6.po.UserPhoto;

public interface UserPhotoMapper {
    void save(UserPhoto userPhoto);

    UserPhoto getUserPhoto(int id);

    int updateByPrimaryKey(UserPhoto record);
}
