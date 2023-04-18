package com.yupi.project.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {


    /**
     * 调用用户Id
     */
    private Long userId;

    /**
     * 接口Id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;


}

//    private Long id;
//
//    private String name;
//
//    private String description;
//
//    private String url;
//
//    private String requestHeader;
//
//    private String responseHeader;
//
//    //private Integer status;
//
//    private String method;
//
//    //private Long userId;
//
//    //private Date createTime;
//
//    //private Date updateTime;
//
//    //private Integer isDelete;