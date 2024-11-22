package com.id2.controller;

import com.id2.model.User;
import com.id2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UsersManagement {
	@Autowired
	private UserService userService;

	//添加用户
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userName = req.getParameter("username");
		String userPwd = req.getParameter("password");
		String permissions = req.getParameter("permissions");
		User user1 = userService.Authenticate(userName);//验证用户名是否重复
		if (user1 != null) {
			return "redirect:main.jsp?exist=yes";
		}
		userService.insertuser(userName, userPwd,permissions);
		return "redirect:main.jsp?add=yes";
	}

}