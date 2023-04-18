package com.ye.yeapicommon.service;

import com.ye.yeapicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param path
     * @param method
     *
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

}
