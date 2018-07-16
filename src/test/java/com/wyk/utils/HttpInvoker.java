package com.wyk.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpInvoker {
	
	private static Log log = LogFactory.getLog(HttpInvoker.class);
	
	// 客户端
	private CloseableHttpClient client;
	
	// 主机地址
	private String host;
	
	// 应答处理
	private ResponseHandler<String> handler;
	
	// 是否进行Agent伪装
	private boolean wrap;
	
	private Integer timeout = 60000;
	
	private static class Holder {
		private static HttpInvoker instance = new HttpInvoker();
	}

	private HttpInvoker() {
		handler = new BasicResponseHandler();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(10);
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(cm);
		client = builder.build();
	}

	/**
	 * 获取模拟器实例
	 *
	 * @return
	 */
	public static HttpInvoker getInstance() {
		return Holder.instance;
	}

	/**
	 * 设置基本路径,如http://localhost:8080/report
	 *
	 * @param host
	 */
	public HttpInvoker setHost(String host) {
		if (!host.endsWith("/")) {
			host += "/";
		}
		this.host = host;
		return this;
	}

	/**
	 * 进行User-Agent伪装
	 */
	public HttpInvoker setWrap(boolean wrap) {
		this.wrap = wrap;
		return this;
	}

	public HttpInvoker setTimeout(Integer timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * 请求地址和host拼接成完整url
	 *
	 * @param action
	 * @return
	 */
	private String makeURL(String action) {
		if (action == null) {
			action = "";
		}
		if (action.startsWith("/")) {
			action = action.substring(1);
		}
		return (this.host == null ? "" : this.host) + action;
	}

	/**
	 * 伪装成FF8
	 *
	 * @param request
	 */
	private void agent(HttpRequestBase request) {
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json,image/png,image/*;q=0.8,*/*;q=0.5");
		request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:8.0) Gecko/20100101 Firefox/8.0");
		request.setHeader("Accept-Encoding", "gzip, deflate");
		request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		request.setHeader("Connection", "Keep-Alive");
	}

	/**
	 * 模拟get操作
	 *
	 * @param action
	 *            请求地址,如果设置了host,则只需要action即可
	 * @return server返回结果
	 */
	public String get(String action, Map<String, String> params) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (null == entry.getValue()) {
					continue;
				}

				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairs.add(nvp);
			}
		}
		try {
			@SuppressWarnings("deprecation")
			HttpResponse response = doGet(action, new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			if (response == null) {
				return null;
			}
			return handler.handleResponse(response);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 模拟get
	 *
	 * @param action
	 * @return
	 */
	private HttpResponse doGet(String action, HttpEntity entity) {
		HttpGet get = new HttpGet(makeURL(action));
		if (wrap) {
			agent(get);
		}
		buildRequestConfig(get);
		try {
			get.setURI(new URI(get.getURI().toString() + "?" + EntityUtils.toString(entity)));
			log.debug(get.getURI());
			return client.execute(get);
		} catch (URISyntaxException e) {
			// log.error(e.getMessage(), e);
		} catch (IOException e) {
			// log.error(e.getMessage(), e);
		}
		return null;
	}

	private void buildRequestConfig(HttpGet get) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.timeout)
				.setConnectTimeout(this.timeout).build();
		get.setConfig(requestConfig);
	}

	/**
	 * 模拟post操作
	 *
	 * @param action
	 *            请求地址,如果设置了host,则只需要action即可
	 * @param params
	 *            post的参数,允许为null
	 * @return server返回结果
	 */
	public String post(String action, Map<String, Object> params) {
		JSONObject jsondata = new JSONObject();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			jsondata.put(entry.getKey(), entry.getValue());
		}

		try {
			StringEntity entity = new StringEntity(jsondata.toString(), "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			return doPost(action, entity);
		} catch (UnsupportedCharsetException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 模拟post
	 *
	 * @param action
	 * @param entity
	 * @return
	 */
	private String doPost(String action, HttpEntity entity) {
		HttpPost post = new HttpPost(makeURL(action));
		if (wrap) {
			agent(post);
		}
		try {
			post.setEntity(entity);
			return client.execute(post, new BasicResponseHandler());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 断开所有连接
	 */
	@SuppressWarnings("deprecation")
	public void destroy() {
		if (client != null) {
			client.getConnectionManager().shutdown();
		}
	}
}
