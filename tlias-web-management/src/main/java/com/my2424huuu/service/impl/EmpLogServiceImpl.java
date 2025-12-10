package com.my2424huuu.service.impl;

import com.my2424huuu.mapper.EmpLogMapper;
import com.my2424huuu.pojo.EmpLog;
import com.my2424huuu.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 无论如何这个方法都要在新的事务中运行，不支持回滚
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
