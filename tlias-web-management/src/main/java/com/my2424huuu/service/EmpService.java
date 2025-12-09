package com.my2424huuu.service;

import com.my2424huuu.pojo.Emp;
import com.my2424huuu.pojo.EmpQueryParam;
import com.my2424huuu.pojo.PageResult;

public interface EmpService {
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页记录数
     */
//    PageResult page(Integer page, Integer pageSize);
    /**
     * 分页查询
     */
    //PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    PageResult page(EmpQueryParam empQueryParam);

    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);
}
