package com.my2424huuu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.my2424huuu.mapper.EmpExprMapper;
import com.my2424huuu.mapper.EmpMapper;
import com.my2424huuu.pojo.Emp;
import com.my2424huuu.pojo.EmpExpr;
import com.my2424huuu.pojo.EmpQueryParam;
import com.my2424huuu.pojo.PageResult;
import com.my2424huuu.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper; // 注入 EmpExprMapper

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
//    public PageResult page(Integer page, Integer pageSize) {
//        //1. 设置分页参数
//        PageHelper.startPage(page,pageSize);
//
//        //2. 执行查询
//        List<Emp> empList = empMapper.list();
//        Page<Emp> p = (Page<Emp>) empList;
//
//        //3. 封装结果
//        return new PageResult(p.getTotal(), p.getResult());
//    }

    public PageResult page(EmpQueryParam empQueryParam) {
        //1. 设置PageHelper分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        //2. 执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
        //3. 封装分页结果
        Page<Emp> p = (Page<Emp>)empList;
        return new PageResult(p.getTotal(), p.getResult());
    }


    @Override
    public void save(Emp emp) {

        //1.补全基础属性
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());

        //2.保存员工基本信息
        empMapper.insert(emp);

        //3. 保存员工的工作经历信息 - 批量
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }
}
