package com.ye.yeapiinterface.controller;

import cn.hutool.http.HttpUtil;
import com.ye.yeapiclientsdk.Utils.SignUtils;
import com.ye.yeapiclientsdk.model.User;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("test")
    public String getNameByGett(String name) {
        //可单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        // http://localhost:8123/api/name/qwe
        String result = HttpUtil.get("localhost:8123" + "/api/name/", paramMap);
        System.out.println(result);
        return result;

    }
    /**
     * GET 接口
     *
     * @param name
     * @return
     */
    @GetMapping("/get")
    public String getNameByGet(String name) {
        System.out.println("yexd");
        return "GET 你的名字是" + name;
    }

    /**
     * POST 接口
     * url传参
     *
     * @param name
     * @return
     */
    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "POST, 你的名字是" + name;
    }


    /**
     * POST 接口
     * Restful，接收json
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {

        String result = "Post 用户名字是" + user.getUsername();
        return result;

//        String accessKey = request.getHeader("accessKey");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
//
//        if (!accessKey.equals("qwer")) {
//            throw new RuntimeException("无权限");
//        }
//        if (Long.parseLong(nonce) > 10000) {
//            throw new RuntimeException("无权限");
//        }
////        //时间不能超过五分钟
////        if (timestamp) {
////
////        }
//        //实际情况是从数据库中查出再赋值
//        String serverSign = SignUtils.genSign(body, "rewq");
//        if (!sign.equals(serverSign)) {
//            throw new RuntimeException("无权限");
//        }
//        return "Post 用户名字是" + user.getUsername();
    }
}
