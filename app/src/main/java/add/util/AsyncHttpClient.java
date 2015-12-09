package add.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.SharedPreferences;
import android.util.Log;

public class AsyncHttpClient {

    /**
     * 采用get方式的异步请求
     *
     * @param urlStr
     * @param isNetError
     * @return
     */
    public String get(String urlStr, Boolean isNetError) {

        HttpURLConnection conn = null;
        // DataInputStream dis = null;
        String result = "";
        String readerLine;

        try {
            URL url  =new URL(urlStr);// new URL(URLEncoder.encode(urlStr, "utf-8"));//

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                isNetError = true;

            } else {
                while ((readerLine = reader.readLine()) != null) {
                    // lines = new String(lines.getBytes(), "utf-8");
                    result += readerLine;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            isNetError = true;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public String post(String requestUrl, Map<String, Object> requestParamsMap,
                       Boolean isNetError) {

		/*
		 * Map<String, Object> requestParamsMap = new HashMap<String, Object>();
		 * requestParamsMap.put("areaCode", "001");
		 * requestParamsMap.put("areaCode1", "中国");
		 */

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        // BufferedReader bufferedReader = null;
        StringBuffer responseResult = new StringBuffer();
        StringBuffer param = new StringBuffer();
        HttpURLConnection httpURLConnection = null;
        // 组织请求参数
        Iterator it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            param.append(element.getKey());
            param.append("=");
            param.append(element.getValue());
            param.append("&");
        }
        if (param.length() > 0) {
            param.deleteCharAt(param.length() - 1);
        }

        try {
            URL realUrl = new URL(requestUrl);
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(param.length()));
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());

            // Log.i("param.toString()-----------------", param.toString());
            // 发送请求参数
            printWriter.write(param.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 根据ResponseCode判断连接是否成功
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                // Log.e(" Error===", String.valueOf(responseCode));
                isNetError = false;
            }
            isNetError = true;
            // 定义BufferedReader输入流来读取URL的ResponseData
            bufferedReader = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // responseResult.append("/n").append(line);
                responseResult.append(line);
            }
        } catch (Exception e) {
            // Log.e("erro", "send post request error!" + e);
        } finally {
            httpURLConnection.disconnect();
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        // Log.i("responseResult", responseResult.toString());
        return responseResult.toString();
    }


}
