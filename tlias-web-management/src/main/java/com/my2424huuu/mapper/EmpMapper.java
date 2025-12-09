package com.my2424huuu.mapper;

import com.my2424huuu.pojo.Emp;
import com.my2424huuu.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper {

    /**
     * 查询总记录数
     */
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id ")
    public Long count();

    /**
     * 查询所有的员工及其对应的部门名称
     */
//    @Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id limit #{start}, #{pageSize}")
//    public List<Emp> list(Integer start , Integer pageSize);

    /*
    以下使用pageHelper插件的做法
     */
    /**
     * 查询所有的员工及其对应的部门名称
     */
//    @Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id")
//    public List<Emp> list();
    /**
     * 根据查询条件查询员工
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工数据
     *
     * 为什么要加@Options这个注解，因为前端传入的参数是emp类型，而empid是数据库自动生成的，要保存empid需要他在插入员工之后返回一个生成的id，这样来记录工作经历是谁的
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);
}
