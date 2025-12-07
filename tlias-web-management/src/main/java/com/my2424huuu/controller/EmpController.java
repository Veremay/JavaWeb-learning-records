package com.my2424huuu.controller;

import com.my2424huuu.pojo.EmpQueryParam;
import com.my2424huuu.pojo.PageResult;
import com.my2424huuu.pojo.Result;
import com.my2424huuu.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理
 */
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page ,
//                       @RequestParam(defaultValue = "10") Integer pageSize){
//        log.info("查询员工信息, page={}, pageSize={}", page, pageSize);
//        PageResult pageResult = empService.page(page, pageSize);
//        return Result.success(pageResult);
//    }

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("查询请求参数： {}", empQueryParam);
        PageResult pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

}
