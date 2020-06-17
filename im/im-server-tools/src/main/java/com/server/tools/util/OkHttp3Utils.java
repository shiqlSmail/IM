package com.server.tools.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 1. 类的用途 封装OkHttp3的工具类 用单例设计模式
 * 2. @author shiqilong
 * 3. @date 2019/6/6 09:19
 */

public class OkHttp3Utils {
	private static final Logger logger = LoggerFactory.getLogger(OkHttp3Utils.class);



	/**
	 * 懒汉 安全 加同步
	 * 私有的静态成员变量 只声明不创建
	 * 私有的构造方法
	 * 提供返回实例的静态方法
	 */
	private static OkHttpClient okHttpClient = null;


	public OkHttp3Utils() {
	}

	public static OkHttpClient getInstance() {
		if (okHttpClient == null) {
			//加同步安全
			synchronized (OkHttp3Utils.class) {
				if (okHttpClient == null) {
					okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
							.writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).build();
					if (okHttpClient == null) {
						logger.warn("create okhttp fail");
					}
				}
			}

		}

		return okHttpClient;
	}

	/**
	 * get请求
	 * 参数1 url
	 * 参数2 回调Callback
	 */

	public static String doGet(String url) {
		logger.info("http get:" + url);
		//创建OkHttpClient请求对象
		OkHttpClient okHttpClient = getInstance();
		//创建Request
		Request request = new Request.Builder().url(url).build();
		//得到Call对象
		return handle(request);
	}



	public static String doPost(String url, Map<String, String> params) {
		//创建OkHttpClient请求对象
		OkHttpClient okHttpClient = getInstance();
		//3.x版本post请求换成FormBody 封装键值对参数
		FormBody.Builder builder = new FormBody.Builder();
		//遍历集合
		for (String key : params.keySet()) {
			builder.add(key, params.get(key));
		}
		//创建Request
		Request request = new Request.Builder().url(url).post(builder.build()).build();
		return handle(request);
	}

	private static String handle(Request request) {
		Call call = okHttpClient.newCall(request);
		try {
			Response response =  call.execute();
			String body = new String(response.body().string());
			logger.info("http resp body:" + body);
			return body;
			//JSONObject obj = JSON.parseObject(body);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * post请求上传文件
	 * 参数1 url
	 * 参数2 回调Callback
	 */
	public static String upload(String url, File file, String fileName) {
		//创建OkHttpClient请求对象
		OkHttpClient okHttpClient = getInstance();
		//创建RequestBody 封装file参数
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		//创建RequestBody 设置类型等
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
		//创建Request
		Request request = new Request.Builder().url(url).post(requestBody).build();
		return handle(request);
	}

	public static String downloadImage(final String url) {
		String fileType = "image/jpeg";
        Request request = new Request.Builder().url(url).build();
		OkHttpClient okHttpClient = getInstance();
		Call call = okHttpClient.newCall(request);
		try {
			Response response =  call.execute();
			return save(url, "/tmp", response);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String save(String url, final String saveDir, final Response response) throws IOException {
		InputStream is = null;
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		try {
			is = response.body().byteStream();
			//apk保存路径
			final String fileDir = isExistDir(saveDir);
			//文件
			String path = fileDir + "/" + UUID.randomUUID().toString().replace("-", "") + ".jpeg";
			File file = new File(path);
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}

			fos.flush();
			return path;
			//apk下载完成后 调用系统的安装方法
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (is != null) is.close();
			if (fos != null) fos.close();
		}
	}

	/**
	 * Post请求发送JSON数据
	 * 参数一：请求Url
	 * 参数二：请求的JSON
	 * 参数三：请求回调
	 */
	public static void doPostJson(String url, String jsonParams, Callback callback) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Call call = getInstance().newCall(request);
		call.enqueue(callback);
	}

	/**
	 * @param url
	 * @return 从下载连接中解析出文件名
	 */
	private static String getNameFromUrl(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

	/**
	 * 下载文件 以流的形式把apk写入的指定文件 得到file后进行安装
	 * 参数一：请求Url
	 * 参数二：保存文件的路径名
	 * 参数三：保存文件的文件名
	 */
//    public static void download(final SelectActivity context, final String url, final String saveDir) {
//        Request request = new Request.Builder().url(url).build();
//        Call call = getInstance().newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("xxx", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                    is = response.body().byteStream();
//                    //apk保存路径
//                    final String fileDir = isExistDir(saveDir);
//                    //文件
//                    File file = new File(fileDir, getNameFromUrl(url));
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, "下载成功:" + fileDir + "," + getNameFromUrl(url), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    fos = new FileOutputStream(file);
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                    }
//
//                    fos.flush();
//                    //apk下载完成后 调用系统的安装方法
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                    context.startActivity(intent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (is != null) is.close();
//                    if (fos != null) fos.close();
//
//
//                }
//            }
//        });
//
//    }

	/**
	 * @param saveDir
	 * @return
	 * @throws IOException 判断下载目录是否存在
	 */
    public static String isExistDir(String saveDir) throws IOException {
            File downloadFile = new File(saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            return savePath;
    }




	//public static void doGet(String url, Callback callback) {
	//
	//	//创建OkHttpClient请求对象
	//	OkHttpClient okHttpClient = getInstance();
	//	//创建Request
	//	Request request = new Request.Builder().url(url).build();
	//	//得到Call对象
	//	Call call = okHttpClient.newCall(request);
	//	//执行异步请求
	//	call.enqueue(callback);
	//
	//
	//}

	/**
	 * post请求
	 * 参数1 url
	 * 参数2 回调Callback
	 */

	//public static void doPost(String url, Map<String, String> params, Callback callback) {
	//
	//	//创建OkHttpClient请求对象
	//	OkHttpClient okHttpClient = getInstance();
	//	//3.x版本post请求换成FormBody 封装键值对参数
	//
	//	FormBody.Builder builder = new FormBody.Builder();
	//	//遍历集合
	//	for (String key : params.keySet()) {
	//		builder.add(key, params.get(key));
	//
	//	}
	//
	//
	//	//创建Request
	//	Request request = new Request.Builder().url(url).post(builder.build()).build();
	//
	//	Call call = okHttpClient.newCall(request);
	//	call.enqueue(callback);
	//
	//}

	//public static void uploadPic(String url, File file, String fileName) {
	//	//创建OkHttpClient请求对象
	//	OkHttpClient okHttpClient = getInstance();
	//	//创建RequestBody 封装file参数
	//	RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
	//	//创建RequestBody 设置类型等
	//	RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
	//	//创建Request
	//	Request request = new Request.Builder().url(url).post(requestBody).build();
	//
	//	//得到Call
	//	Call call = okHttpClient.newCall(request);
	//	//执行请求
	//	call.enqueue(new Callback() {
	//		@Override
	//		public void onFailure(Call call, IOException e) {
	//
	//		}
	//
	//		@Override
	//		public void onResponse(Call call, Response response) throws IOException {
	//			//上传成功回调 目前不需要处理
	//		}
	//	});
	//
	//}
}


