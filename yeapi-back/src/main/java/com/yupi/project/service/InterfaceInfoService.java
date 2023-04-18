package com.yupi.project.service;

import com.ye.yeapicommon.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xxxd
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-13 10:34:27
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
