package com.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.httpclient.params.BaseParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * httpClient 请求方式
 * Created by huangdongbin on 2016/9/12.
 */
public class HttpClient implements ClientService {
    private Log logger = LogFactory.getLog(HttpClient.class);
    private String url = null;
    private Object baseParams;
    private CloseableHttpClient httpClient = null;
    private HttpPost method = null;
    private ObjectMapper objectMapper = new ObjectMapper();

    public HttpClient(String url, BaseParams baseParams) throws Exception {
        this.baseParams = baseParams;
        this.url = url;
        if (url == null || url.length() == 0) {
            throw new Exception("url不能为空");
        }
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(this.url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        method = httpPost;
    }

    /**
     * 发送请求
     * @return
     */
    @Override
    public Map post() {
        Map result = getResult();
        if (result == null) return null;
        if (result.containsKey("error")) {
            logger.error("error:" + result.get("error"));
            return null;
        }
        return result.containsKey("result") ? (Map) result.get("result") : new HashMap<>();
    }

    @Override
    public Object postReturnObject() {
        Map result = getResult();
        if (result == null) return null;
        if (result.containsKey("error")) {
            logger.error("error:" + result.get("error"));
            return null;
        }
        return result.get("result");
    }

    private Map getResult() {
        if (method != null) {
            logger.info("url:" + url);
            try {
                String content = objectMapper.writeValueAsString(baseParams);
                logger.info("request data:" + content);
                method.addHeader("Content-type", "application/json; charset=utf-8");
                method.setHeader("Accept", "application/json");
                method.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
                HttpResponse response = httpClient.execute(method);
                if (response == null) {
                    logger.error("response is null.");
                    return null;
                }
                logger.info("response:" + response.toString());
                int statusCode = response.getStatusLine().getStatusCode();
                logger.info("statusCode:" + statusCode);
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed:" + response.getStatusLine());
                    return null;
                }
                String body = EntityUtils.toString(response.getEntity());
                if (body == null) {
                    logger.error("body is null.");
                    return null;
                }
                Map data = objectMapper.readValue(body, Map.class);
                logger.info("response data:" + data.toString());
                return data;
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (httpClient != null) {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        logger.error("httpClient.close() IOException.", e);
                    } finally {
                        httpClient = null;
                    }
                }
            }
        }
        return null;
    }

}
