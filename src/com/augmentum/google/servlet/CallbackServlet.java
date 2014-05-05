package com.augmentum.google.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.augmentum.google.model.GoogleAccountInfo;
import com.augmentum.google.util.ConstantsUtil;
import com.augmentum.google.util.PropertiesUtil;
import com.augmentum.google.vo.GoogleVo;

/**
 * Servlet implementation class CallbackServlet
 */
@WebServlet(name = "Callback",urlPatterns = {"/oauth2callback"})
public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CallbackServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CallbackServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String url = request.getRequestURL().toString();
		String redirectUrl = url.substring(0,
				url.lastIndexOf("oauth2callback"));
		if (!StringUtils.isBlank(code)) {
			GoogleAccountInfo account = GoogleAccountInfo.dao.findByUnique();
			if (null != account) {
				String refreshToken = getRefreshToken(code,account);
				if(!StringUtils.isBlank(refreshToken)){
					account.set("code", code).set("refreshToken", refreshToken).update();
					response.sendRedirect(redirectUrl + "google/config?auth=success");
				}else{
					response.sendRedirect(redirectUrl + "google/config?auth=false");
				}
			}
		}
	}

	private String getRefreshToken(String code,GoogleAccountInfo account) {
		GoogleVo googleVo = PropertiesUtil.readFromPropertiesFile();
		String tokenUrl = googleVo.getTokenUrl();
		String grantType = googleVo.getGrantType();
		String clientId = account.getStr("clientId");
		String clientSecret = account.getStr("clientSecret");
		String redirectUrl = account.getStr("redirectUrl");
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		HttpPost httpRequst = new HttpPost(tokenUrl);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id",
				clientId));
		params.add(new BasicNameValuePair("client_secret",
				clientSecret));
		params.add(new BasicNameValuePair("grant_type", grantType));
		params.add(new BasicNameValuePair("redirect_uri",
				redirectUrl));
		params.add(new BasicNameValuePair("code",
				code));

		try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == ConstantsUtil.HTTP_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				rootNode = objectMapper.readTree(httpEntity.getContent());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		if(null != rootNode){
			JsonNode node = rootNode.get("refresh_token");
			if(null != node){
				return node.getTextValue();
			}
		}

		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
