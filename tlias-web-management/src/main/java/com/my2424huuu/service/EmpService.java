package com.my2424huuu.service;

import com.my2424huuu.pojo.PageResult;

public interface EmpService {
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页记录数
     */
    PageResult page(Integer page, Integer pageSize);
}
