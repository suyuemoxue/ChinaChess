package com.sxj.controller;


import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.sxj.entity.GameBean;
import com.sxj.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class SearchDataController {

    @Autowired
    GameService gameService;

    // 返回文本
//		resp.setContentType("text/plain; charset=UTF-8");
    // 返回Json
//		resp.setContentType("application/json; charset=UTF-8");
    // html类型
//		resp.setContentType("text/html; charset=UTF-8");
//    @RequestMapping(value = {"/api/v1/test"}, method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/searchDataController",produces = "application/json;charset=UTF-8")
    // html类型
    /*@ResponseBody*/
//    返回Json
    @ResponseJSONP
    public List<GameBean> searchDataController(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        List<GameBean> gameBeans = gameService.searchGameData(map);

        return gameBeans;
    }
}
