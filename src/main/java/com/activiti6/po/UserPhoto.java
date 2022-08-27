package com.activiti6.po;

import java.io.Serializable;

import org.activiti.engine.task.Task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ユーザーphoto情報")
public class UserPhoto implements Serializable {
    @ApiModelProperty("id")
    int id;
    @ApiModelProperty("photo")
    String photo;
    @ApiModelProperty("IDCardphoto")
    String IDCardphoto;
    Task task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIDCardphoto() {
        return IDCardphoto;
    }

    public void setIDCardphoto(String IDCardphoto) {
        this.IDCardphoto = IDCardphoto;
    }

}
