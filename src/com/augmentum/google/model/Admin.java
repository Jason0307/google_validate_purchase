/**
 * 
 */
package com.augmentum.google.model;

import com.augmentum.google.exception.GoogleException;
import com.augmentum.google.util.ErrorCodeConstant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author Jason.Zhu
 * @email jasonzhu@augmentum.com.cn
 * @date   May 13, 2014 3:30:39 PM
 */
@TableBind(tableName = "Admin" , pkName = "adminId")
public class Admin extends Model<Admin>{
	private static final long serialVersionUID = 1L;
	public static Admin dao = new Admin();
	
	/**
	 * admin user login 
	 * @param username
	 * @param password
	 * @return
	 */
	public Admin login(String username, String password) {
		
		Admin admin = Admin.dao.findFirst("SELECT * FROM Admin WHERE username = ?", username);
		if(null == admin){
			throw new GoogleException(ErrorCodeConstant.ADMIN_INVALID);
		}
		if(!password.trim().equals(admin.getStr("password"))){
			throw new GoogleException(ErrorCodeConstant.ADMIN_INVALID);
		}
		
		return admin;
	}
}
