package com.my2424huuu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.my2424huuu.mapper.EmpMapper;
import com.my2424huuu.pojo.Emp;
import com.my2424huuu.pojo.PageResult;
import com.my2424huuu.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工管理
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

//    @Override
//    public PageResult page(Integer page, Integer pageSize) {
//        //1. 获取总记录数
//        Long total = empMapper.count();
//
//        //2. 获取结果列表
//        Integer start = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.list(start, pageSize);
//
//        //3. 封装结果
//        return new PageResult(total, empList);
//    }

    /*
    以下是使用pageHelper插件的做法
     */
    @Override
    public PageResult page(Integer page, Integer pageSize) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Emp> empList = empMapper.list();
        Page<Emp> p = (Page<Emp>) empList;

        //3. 封装结果
        return new PageResult(p.getTotal(), p.getResult());
    }
}
