package com.ycgwl.kylin.util.http;

import com.ycgwl.kylin.util.json.CommonUtil;
import com.ycgwl.kylin.util.encrypt.Base64Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {

	
	public final static int HTTP_OK = 200;
	public final static int HTTP_TIMEOUT = 408;
	public final static String METHOD_POST = "POST";
	public final static String METHOD_GET = "GET";

	private int timeout;
	private String method;
	private String urlString;
	private String parameterString;
	private String contentEncoding = "UTF-8";
	private Map<String, Object> parameters;
	private Map<String, String> properties;
	
	private String responseContent;
	
	private boolean json = false;
	
	public HttpClient() {}

	public HttpClient(int timeout) {
		this();
		this.timeout = timeout;
	}
	public HttpClient(String urlString, int timeout) {
		this();
		this.timeout = timeout;
	}
	public HttpClient(String urlString) {
		super();
		this.urlString = urlString;
	}

	public void clear(){
		setJson(false);
		if(properties != null){
			properties.clear();
		}
		if(parameters != null){
			parameters.clear();
		}
	}
	
	public void property(String pkey, String value){
		if(properties == null){
			properties = new HashMap<String, String>();
		}
		properties.put(pkey, value);
	}

	public void property(Map<String, String> _properties){
		if(_properties != null && !_properties.isEmpty()){
			if(properties == null){
				properties = new HashMap<String, String>();
			}
			for (Map.Entry<String, String> entry : _properties.entrySet()) {
				properties.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void parameter(Map<String, String> _parameters){
		if(_parameters != null && !_parameters.isEmpty()){
			if(parameters == null){
				parameters = new HashMap<String, Object>();
			}
			for (Map.Entry<String, String> entry : _parameters.entrySet()) {
				parameters.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void parameter(String pkey, Object value){
		if(parameters == null){
			parameters = new HashMap<String, Object>();
		}
		parameters.put(pkey, value);
	}

	private void setHeader(AbstractHttpMessage httpMessage){
		if (properties != null) {
			for(Map.Entry<String, String> entry: properties.entrySet()){
				httpMessage.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	private String get(CloseableHttpClient httpclient) throws ClientProtocolException, IOException{
		String lastUrl = urlString;
		StringBuilder builder = new StringBuilder();
		if(StringUtils.isNotEmpty(parameterString)){
			builder.append(parameterString);
		}
		if (parameters != null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				if(builder.length() > 0){
					builder.append("&");
				}
				builder.append(entry.getKey()).append("=").append(entry.getValue());
			}	
		}
		if (lastUrl.indexOf("?") > 0) {
			builder.insert(0, "&");
			builder.insert(0, lastUrl);
		} else {
			builder.insert(0, '?');
			builder.insert(0, lastUrl);
		}
		HttpGet httpget = new HttpGet(builder.toString());
		setHeader(httpget);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseContent = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			}
		} finally {
			response.close();
		}
		return responseContent;
	}
	
	private String json(CloseableHttpClient httpclient) throws ClientProtocolException, IOException{
		HttpPost httppost = new HttpPost(urlString);
		setHeader(httppost);
		httppost.setEntity(new StringEntity(CommonUtil.toJsonString(parameters), ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = httpclient.execute(httppost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, contentEncoding);
			}
		} finally {
			response.close();
		}
		return responseContent;
	}
	
	private String post(CloseableHttpClient httpclient) throws ClientProtocolException, IOException{
		HttpPost httppost = new HttpPost(urlString);
		setHeader(httppost);
		if(parameters != null){
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object>  entry : parameters.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
			httppost.setEntity(new UrlEncodedFormEntity(formparams, contentEncoding));
		}
		CloseableHttpResponse response = httpclient.execute(httppost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, contentEncoding);
			}
		} finally {
			response.close();
		}
		return responseContent;
	}
	
	/*
	public String requestssl(String keyStore, String password) throws IOException{
		CloseableHttpClient httpclient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = null;
			try {
				instream = new FileInputStream(new File(keyStore));
				if(password == null || password.length() <= 0){
					password = "";
				}
				trustStore.load(instream, password.toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) { }
			}
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			if(METHOD_GET.equals(method)){
				responseContent = get(httpclient);
			}else{
				responseContent = json ? json(httpclient) : post(httpclient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}
	*/
	public String get() throws IOException{
		return request(METHOD_GET, false);
	}
	
	public String post() throws IOException{
		return request(METHOD_POST, false);
	}
	
	public String json() throws IOException{
		return request(METHOD_POST, true);
	}
	
	public String request() throws IOException{
		return request(method, json);
	}

	public String request(String _method, boolean _json) throws IOException{
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.custom().build();
			if(METHOD_GET.equals(_method)){
				responseContent = get(httpclient);
			}else{
				responseContent = _json ? json(httpclient) : post(httpclient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}
	
	
	public Map<String, Object> parameters() {
		return parameters;
	}

	public String getParameterString() {
		return parameterString;
	}

	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getMethod() {
		return method;
	}

	public boolean isJson() {
		return json;
	}

	public void setJson(boolean json) {
		this.json = json;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setPost(){
		method = METHOD_POST;
	}
	
	public void setGet() {
		method = METHOD_GET;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Url[").append(urlString).append("]");
		if(parameterString != null && !"".equals(parameterString)){
			builder.append(" ParamString:").append(parameterString);
		}
		if(parameters != null && !parameters.isEmpty()){
			builder.append(" Parameters:").append(parameters);
		}
		builder.append(" Receive:").append(responseContent);
		return builder.toString();
	}

	public static void main(String[] args) {
		HttpClient client = new HttpClient();
		client.setUrlString("http://10.133.7.22/service/api/v1/authType/validateLogin");
		client.setJson(true);
		client.parameter("username", Base64Utils.encode("110686"));
		client.parameter("password", Base64Utils.encode("123456"));
		client.parameter("appid", Base64Utils.encode("123"));
		try {
			System.out.println(client.json());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(client);
	}
}
