package com.augmentum.google.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

@TableBind(tableName = "GoogleAccountInfo", pkName = "baseId")
public class GoogleAccountInfo extends Model<GoogleAccountInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static GoogleAccountInfo dao = new GoogleAccountInfo();

	public GoogleAccountInfo findByUnique() {
		List<GoogleAccountInfo> accounts = GoogleAccountInfo.dao
				.find("SELECT * FROM GoogleAccountInfo limit 0,1") ;
		if (!accounts.isEmpty()) {
			return accounts.get(0);
		}
		return null;
	}
}
