package com.id2.service.impl;


import com.id2.dao.UserDao;
import com.id2.model.Page;
import com.id2.model.User;
import com.id2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {
	@Autowired
	private UserDao userDao;
	//查询用户和密码
	public User selectuser(String userName, String userPwd) {
		// TODO Auto-generated method stub
		return userDao.selectuser(userName, userPwd);
	}

	//查询共有多少条记录
	public int findAllRecord(HashMap<String, Object> map) {
 		return userDao.findAllRecord(map);
	}

	//分页查询，进行逻辑整合
	public Page<User> findByPage(HashMap<String, Object> map) {
		Page<User> page=new Page<>();

		int rows = (int)map.get("rows");
		int currentPage = (int)map.get("currentPage");
		//查询当前搜索中共有多少条记录
		 int allRecord = findAllRecord(map);
		//计算总共的页数
		int totalPage;
		if(allRecord%rows==0){
			totalPage=allRecord/rows;
		}else{
			totalPage=allRecord/rows+1;
		}

		/**分页查询:返回用户数据，及总页数，当前页数，一页的行数
		 * 每一页的开始位置= (当前页数-1) * 行数
		 * 第一页开始位置：（1-1）*5 = 0
		 * 第一页开始位置：（2-1）*5 = 5*/
		int start=(currentPage-1)*rows;
		map.put("startIndex",start);//起始点
		List<User> users = userDao.findByPage(map);//将查询到的users进行封装，在controller层给前端
		page.setCurrentPage(currentPage);
		page.setList(users);
		page.setRows(rows);
		page.setTotalCount(allRecord);
		page.setTotalPage(totalPage);
		return page;
	}

	//删除用户信息
	public void deleteUsers(String userName) {
		// TODO Auto-generated method stub
		userDao.deleteUsers(userName);
	}
	//更新用户信息
	public void updateUser(String userName, String password, int id, String permissions) {
		// TODO Auto-generated method stub
		userDao.updateUser(userName, password, id, permissions);
	}
	//批量删除用户信息
	@Override
	public Integer deletUsers2(Integer[] ids) {
		if (ids!=null && ids.length>0){
			Integer re = userDao.deletUsers2(ids);
			return re;
		}
		return 0;
	}
	//验证添加的用户是否重名
	public User Authenticate(String userName) {
		// TODO Auto-generated method stub
		return userDao.Authenticate(userName);
	}
	//添加用户
	public void insertuser(String userName, String userPwd, String permissions) {
		// TODO Auto-generated method stub
		userDao.insertuser(userName, userPwd, permissions);
	}
}
