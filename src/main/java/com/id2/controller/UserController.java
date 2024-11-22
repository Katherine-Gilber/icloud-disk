package com.id2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id2.model.User;
import com.id2.model.Page;
import com.id2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//登录认证-查询用户
	@RequestMapping(value = "/selectuser", method = RequestMethod.POST)
	public String findalls(Map<String, Object> map, User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userName = req.getParameter("user");
		String userPwd = req.getParameter("password");

		User lists = userService.selectuser(userName, userPwd);
		if (lists != null) {
			/*map.put("lists", lists);*/
			HttpSession session = req.getSession();
			session.setAttribute("userName", userName);
			session.setAttribute("permissions", lists.getPermissions());
			return "icloud_d2";
		}
		return "redirect:index.jsp?error=yes";
	}

	//分页查询
	@RequestMapping(value = "/findByPage", method = RequestMethod.POST)
	@ResponseBody
	public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");
		int rowsInt = Integer.parseInt(req.getParameter("rows"));
		int currentPageInt = Integer.parseInt(req.getParameter("currentPage"));
		String userId = req.getParameter("userId");
		String userName = req.getParameter("userName");
		String permissions = req.getParameter("permissions");

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("rows",rowsInt);
		map.put("currentPage",currentPageInt);
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("permissions", permissions);

		Page<User> allByPage = userService.findByPage(map);
		//传给前端
		ObjectMapper mapper=new ObjectMapper();
		mapper.writeValue(resp.getWriter(), allByPage);
	}

	//修改用户信息
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userName = req.getParameter("name");
		String password = req.getParameter("password");
		int id = Integer.parseInt(req.getParameter("id"));
		String permissions = req.getParameter("permissions");
		System.out.println(userName);
		System.out.println(password);
		System.out.println(id);
		System.out.println(permissions);
		userService.updateUser(userName, password, id, permissions);
		return "redirect:main.jsp";
	}
	//删除操作
	@RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
	public String deleteUsers(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		String userName = request.getParameter("users");//从前端获取用户名
		userService.deleteUsers(userName);
		return "redirect:main.jsp";
	}
	//批量删除
	@RequestMapping("/delete")
	public String delete(Integer[] ids, Model model){
		Integer integer = userService.deletUsers2(ids);
		return "redirect:main.jsp";
	}
}
