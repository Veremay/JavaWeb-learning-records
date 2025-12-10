package com.my2424huuu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.my2424huuu.mapper.EmpExprMapper;
import com.my2424huuu.mapper.EmpMapper;
import com.my2424huuu.pojo.*;
import com.my2424huuu.service.EmpLogService;
import com.my2424huuu.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private EmpLogService empLogService;

    // 为什么前两个注入Mapper，最后一个注入Service？
    // 前两个属于同一个业务功能：管理员工数据。emp 和 emp_expr 是关联表（一对多）。我这两个操作属于同一个业务流程，需要协调执行；
    // 日志管理是独立的业务功能：记录系统操作日志，遵循分层架构，需要通过Service来调用
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
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult(p.getTotal(), p.getResult());
    }


    @Transactional(rollbackFor = Exception.class)   // 事务管理 - 默认出现RuntimeException才会回滚，所以要加一个rollbackFor
    @Override
    public void save(Emp emp) {
        try {
            //1.补全基础属性
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            //2.保存员工基本信息
            empMapper.insert(emp);

            int i = 1 / 0;

            //3. 保存员工的工作经历信息 - 批量
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(empExpr -> empExpr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogService.insertLog(empLog);
        }
    }
}
