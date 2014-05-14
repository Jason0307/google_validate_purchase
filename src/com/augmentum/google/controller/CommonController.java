/**
 * 
 */
package com.augmentum.google.controller;

import com.augmentum.google.model.Admin;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

/**
 * @author Jason.Zhu
 * @email jasonzhu@augmentum.com.cn
 * @date   May 13, 2014 3:02:05 PM
 */
@ControllerBind(controllerKey = "/admin",viewPath = "/page/common/")
public class CommonController extends Controller{

	public void index(){
		render("index.jsp");
	}
	
	public void login(){
		render("login.jsp");
	}
	
	public void doLogin(){
		String username = getPara("username");
		String password = getPara("password");
		String message = null;
		Admin admin = null;
		try {
			admin = Admin.dao.login(username,password);
		} catch (Exception e) {
			message = e.getMessage();
			setAttr("message", message);
			render("login.jsp");
			return;
		}
		getSession().setAttribute("_ADMIN_USER", admin);
		redirect("/admin/index");
	}
}
