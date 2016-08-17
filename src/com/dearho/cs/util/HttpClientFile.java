package com.dearho.cs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientFile {

	/**
	 * 上传图片
	 * @param url
	 * @param file 文件
	 * @param param 参数
	 */
	public static String SubmitPost(String url, File file, Map<String, String> param) {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			
			FileBody bin = new FileBody(file);
			StringBody filename = new StringBody(file.getName());
			StringBody quality = new StringBody(param.get("quality"));
			StringBody useType = new StringBody(param.get("useType"));
			StringBody userId = new StringBody(param.get("userId"));
			StringBody type = new StringBody(param.get("type"));

			MultipartEntity reqEntity = new MultipartEntity();

			reqEntity.addPart("filedata", bin);// file1为请求后台的File upload;属性
			reqEntity.addPart("filename", filename);// 普通参数
			reqEntity.addPart("quality", quality);// 普通参数
			reqEntity.addPart("useType", useType);// 普通参数
			reqEntity.addPart("userId", userId);// 普通参数
			reqEntity.addPart("type", type);// 普通参数
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("服务器正常响应.....");
				HttpEntity resEntity = response.getEntity();
				//result = java.net.URLDecoder.decode(resEntity.toString(),"UTF-8");
				// new String(rtn.getBytes(rtn),"UTF-8");
				//result = 
				//EntityUtils.toString(resEntity);
				//System.out.println(EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据
				StringBuffer sb = new StringBuffer();
				 BufferedReader reader = new BufferedReader(new InputStreamReader(resEntity.getContent(), "UTF-8"));
				    String line = null;
				    while ((line = reader.readLine()) != null) {
				      sb.append(line);
				 }
				 System.err.println(sb.toString());
				result = sb.toString();
				EntityUtils.consume(resEntity);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {

			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Map<String,String> param = new HashMap<String, String>();
        param.put("quality", "low"); //原图  low低质量  medium中等质量 hight高质量
        param.put("useType", "2");   //1相册  2证件  3证据 4 附件
        param.put("userId", "1");
        param.put("type", "single"); //single保存一张(服务器只保存一张图片)  double保存多张(服务器保存多张格式的压缩图片，当选择多种保存后，压缩质量选项失效) 
        
        File file = new File("D://a.jpg");
        
        SubmitPost("http://localhost:8080/cs_img/upload/imgUpload",file,param);
        
	}
	
}
