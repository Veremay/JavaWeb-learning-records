package com.my2424huuu.mapper;

import com.my2424huuu.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询全部
     */
    @Select("select * from user")
    public List<User> findAll();

    /**
     * 根据id删除，返回值是删除了多少条数据
     */
    @Delete("delete from user where id = #{id}")
    public Integer deleteById(Integer id);

    /**
     * 添加用户
     */
    @Insert("insert into user(username,password,name,age) values(#{username},#{password},#{name},#{age})")
    public void insert(User user);

    /**
     * 根据id更新用户信息
     */
    @Update("update user set username = #{username},password = #{password},name = #{name},age = #{age} where id = #{id}")
    public void update(User user);

    /**
     * 根据用户名和密码查询用户信息
     * 当Mapper方法有多个参数时，必须使用 @Param 给每个参数命名：
     * 这里的 #{username} 和 #{password} 对应 @Param 指定的名称。
     * MyBatis底层会将带有 @Param 的参数封装成一个Map：
     */
    @Select("select * from user where username = #{username} and password = #{password}")
    public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
