package com.augmentum.google.model;

import com.augmentum.google.util.ConstantsUtil;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
/**
 * 
 * @author Jason.Zhu
 * @email jasonzhu@augmentum.com.cn
 * @date   2014年4月30日
 */
@TableBind(tableName = "User",pkName = "userId")
public class User extends Model<User>{

	private static final long serialVersionUID = 1L;
    public static User dao = new User();
	/**
	 * @param relevanceCode
	 * @return
	 */
	public User findByRelevanceCode(String relevanceCode) {
		User user = User.dao.findFirst("SELECT * FROM User WHERE relevanceCode = ? AND valid = ?",relevanceCode,ConstantsUtil.RELEVANCECODE_VALID);
		return user;
	}
	
}
