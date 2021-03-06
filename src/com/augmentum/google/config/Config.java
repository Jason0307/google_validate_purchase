package com.augmentum.google.config;

import com.augmentum.google.generate.base.BaseModel;
import com.augmentum.google.interceptor.ExceptionInterceptor;
import com.augmentum.google.interceptor.GlobalInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

public class Config extends JFinalConfig{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jfinal.config.JFinalConfig#configConstant(com.jfinal.config.Constants
	 * )
	 */
	@Override
	public void configConstant(Constants c) {
		loadPropertyFile("jdbc.properties");
		c.setDevMode(true);
	    c.setViewType(ViewType.JSP);
	  //  I18N.init("messages", null,null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jfinal.config.JFinalConfig#configHandler(com.jfinal.config.Handlers)
	 */
	@Override
	public void configHandler(Handlers me) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.
	 * Interceptors)
	 */
	@Override
	public void configInterceptor(Interceptors i) {
		i.add(new GlobalInterceptor());
		i.add(new ExceptionInterceptor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jfinal.config.JFinalConfig#configPlugin(com.jfinal.config.Plugins)
	 */
	@Override
	public void configPlugin(Plugins p) {

		// use auto table bind plugin
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"),
				getProperty("user"), getProperty("password"));
		AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(
				druidPlugin);
		autoTableBindPlugin.setShowSql(true);
		autoTableBindPlugin.addExcludeClass(BaseModel.class);
		p.add(druidPlugin).add(autoTableBindPlugin);
	//	QuartzPlugin quartzPlugin = new QuartzPlugin("job.properties");
		p.add(new EhCachePlugin());//.add(quartzPlugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jfinal.config.JFinalConfig#configRoute(com.jfinal.config.Routes)
	 */
	@Override
	public void configRoute(Routes r) {
		r.add(new AutoBindRoutes());

	}

	public static void main(String[] args) throws Exception {
		 JFinal.start("WebContent", 80, "/", 5);
	}
}
