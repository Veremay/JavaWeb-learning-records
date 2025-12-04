package com.my2424huuu.service;

import com.my2424huuu.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 查询所有部门数据
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
