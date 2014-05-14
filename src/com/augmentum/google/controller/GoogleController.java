package com.augmentum.google.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;
import com.augmentum.google.model.GoogleAccountInfo;
import com.augmentum.google.model.User;
import com.augmentum.google.util.ConstantsUtil;
import com.augmentum.google.util.JsonResponse;
import com.augmentum.google.util.PropertiesUtil;
import com.augmentum.google.util.TokenUtil;
import com.augmentum.google.util.UUIDUtil;
import com.augmentum.google.vo.GoogleVo;
import com.augmentum.google.vo.ReceiptVo;
import com.augmentum.google.vo.VerifyReceiptVo;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey = "/admin/google", viewPath = "/page/")
public class GoogleController extends Controller {

	
	public void config() {
		GoogleAccountInfo account = GoogleAccountInfo.dao.findByUnique();
		String auth = getPara("auth");
		setAttr("account", account);
		setAttr("auth", auth);
		render("config.jsp");
	}
	
	public void auth() {

		GoogleVo googleVo = PropertiesUtil.readFromPropertiesFile();
		if (null != googleVo) {
			GoogleAccountInfo account = GoogleAccountInfo.dao.findByUnique();
			if (null != account) {
				String authUrl = googleVo.getAuthUrl();
				String scope = googleVo.getScope();
				String responseType = googleVo.getResponseType();
				String accessType = googleVo.getAccessType();
				String approval = googleVo.getApproval();
				String clientId = account.getStr("clientId");
				String redirectUrl = account.getStr("redirectUrl");
				StringBuilder sb = new StringBuilder(authUrl);
				sb.append("?scope=").append(scope);
				sb.append("&client_id=").append(clientId);
				sb.append("&redirect_uri=").append(redirectUrl);
				sb.append("&response_type=").append(responseType);
				sb.append("&access_type=").append(accessType);
				sb.append("&approval_prompt=").append(approval);
				redirect(sb.toString());
			}
		}
	}

	public void saveAccount() throws Exception {
		UploadFile file = getFile();
		File clientJsonFile = file.getFile();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		if (null != clientJsonFile) {
			rootNode = objectMapper
					.readTree(new FileInputStream(clientJsonFile));
			if (null != rootNode) {
				String clientId = rootNode.get("web").get("client_id")
						.getTextValue();
				String clientSecret = rootNode.get("web").get("client_secret")
						.getTextValue();
				String redirectUrl = rootNode.get("web").get("redirect_uris")
						.get(0).getTextValue();
				GoogleAccountInfo account = GoogleAccountInfo.dao
						.findByUnique();
				if (null == account) {
					account = new GoogleAccountInfo();
				}
				account.set("clientId", clientId)
						.set("clientSecret", clientSecret)
						.set("redirectUrl", redirectUrl).save();
			}
		}
		redirect("/google/config");
	}

	public void validatePurchase() throws Exception {
		String receiptData = getPara("receoptData");
		ReceiptVo receiptVo = JSON.parseObject(receiptData, ReceiptVo.class);
		StringBuilder url = new StringBuilder(
				"https://www.googleapis.com/androidpublisher/v1.1/applications/");
		String accessToken = TokenUtil.getTokenByRefreshToken();
		url.append(receiptVo.getPackageName()).append("/inapp/")
				.append(receiptVo.getProductId()).append("/purchases/")
				.append(receiptVo.getPurchaseToken()).append("?access_token=")
				.append(accessToken);
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpGet httpRequst = new HttpGet(url.toString());
		String result = "";
		try {
			HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == ConstantsUtil.HTTP_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
			}
		} finally {
			closeableHttpClient.close();
		}
		VerifyReceiptVo verifyReceiptVo = JSON.parseObject(result,
				VerifyReceiptVo.class);
		if (ConstantsUtil.PURCHASE_SUCCESS == verifyReceiptVo
				.getPurchaseState()) {
			renderJson("{\"success\":true}");
		}
	}

	public void generateUniqueCode() {
		long userId = getParaToInt("userId", 0);
		String code = UUIDUtil.generateShortUuid();
		User user = User.dao.findById(userId);
		if (null != user) {
			user.set("relevanceCode", code)
					.set("valid", ConstantsUtil.RELEVANCECODE_VALID).update();
			renderJson(new JsonResponse<String>(ConstantsUtil.STATUS_OK, code));
		}
	}

	public void relevanceAccount() {
		long userId = getParaToInt("userId", 0);
		String relevanceCode = getPara("relevanceCode");
		User user = User.dao.findById(userId);
		if (null != user) {
			User relevanceUser = User.dao.findByRelevanceCode(relevanceCode);
			if (null != relevanceUser) {
				relevanceUser.set("valid", ConstantsUtil.RELEVANCECODE_INVALID)
						.update();
				renderJson(new JsonResponse<User>(ConstantsUtil.STATUS_OK,
						relevanceUser));
			} else {
				renderJson(new JsonResponse<User>(ConstantsUtil.STATUS_ERROR,
						null, "The relevance code is invalid!"));
			}
		}
	}
}
