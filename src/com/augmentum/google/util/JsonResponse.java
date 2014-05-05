/**
 * 
 */
package com.augmentum.google.util;

/**
 * @author Jason.Zhu
 * @email jasonzhu@augmentum.com.cn
 * @date 2014年4月30日 上午9:35:38
 */
public class JsonResponse<T> {

	private T response;
	private int statusCode;
	private String additionalInfo;

	public JsonResponse() {
		super();
	}

	public JsonResponse(int statusCode, T response) {
		this.statusCode = statusCode;
		this.response = response;
	}

	public JsonResponse(int statusCode, T response, String additionalInfo) {
		this.statusCode = statusCode;
		this.response = response;
		this.additionalInfo = additionalInfo;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}


}
