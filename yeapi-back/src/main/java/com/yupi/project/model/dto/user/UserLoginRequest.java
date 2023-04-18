package com.yupi.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author yupi
 *
 * DTO: data transfer object, 数据传输对象，将数据封装成普通javabeans，在j2ee多个层次之间进行传输
 *
 * dto一般继承entity， 在dto中放一些业务字段，并提供getter、setter方法
 *
 * vo进一步对dto进行拓展， vo用于展示
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
