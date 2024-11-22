package com.id2.dao;

import com.id2.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
	    //查询登录信息
		public User selectuser(@Param("userName") String userName, @Param("userPwd") String userPwd);
		//查询共有多少条记录
		public int findAllRecord(HashMap<String,Object> map); //方便传参
		//分页查询
		public List<User> findByPage(HashMap<String,Object> map);

		/**删除指定id数据*/
		public void deleteUsers(@Param("userName")String userName);
		//更新用户信息
		public void updateUser(@Param("userName")String userName,@Param("password")String password,@Param("id")int id,@Param("permissions")String permissions);
		//批量删除
		Integer deletUsers2(Integer[] ids);
		//验证添加的用户是否重名
		public User Authenticate(@Param("userName")String userName);
		//添加用户
		public void insertuser(@Param("userName")String userName,@Param("userPwd")String userPwd,@Param("permissions")String permissions);
}
