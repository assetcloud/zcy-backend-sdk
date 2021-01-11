package cn.org.assetcloud;

import cn.org.assetcloud.entity.*;
import cn.org.assetcloud.exception.HttpProcessException;
import cn.org.assetcloud.util.AssetSignUtil;
import cn.org.assetcloud.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * @author Juxiu Ren Jianchao
 */
public class HttpRequestUtil {

    public static AssetCloudResponse<Object> send(AssetCloudRequest request) throws HttpProcessException {
        String result = "";
        if (request.getHttpMethods() == HttpMethods.POST) {
            result = AssetSignUtil.post(buildConfig(handleRequest(request)));
        } else if (request.getHttpMethods() == HttpMethods.GET) {
            result = AssetSignUtil.get(buildConfig(handleRequest(request)));
        }
        JSONObject json = JSONObject.parseObject(result);
        AssetCloudResponse<Object> r = new AssetCloudResponse<Object>();
        r.setCode(json.getIntValue("code"));
        r.setData(json.get("data"));
        r.setMsg(json.getString("msg"));
        r.setSuccess(json.getBoolean("success"));
        return r;
    }

    private static HttpConfig buildConfig(AssetCloudRequest request) {
        HttpConfig httpConfig = HttpConfig.custom();
        HttpHeader headers = HttpHeader.custom().other("key", request.getKey());
        httpConfig.headers(headers.build());
        httpConfig.url(request.getUrl());
        if (request.getHttpMethods() == HttpMethods.POST) {
            httpConfig.method(HttpMethods.POST);
            httpConfig.json(request.getBody());
        }
        return httpConfig;
    }

    private static AssetCloudRequest handleRequest(AssetCloudRequest request) throws HttpProcessException {
        String url = request.getUrl();
        String key = request.getKey();
        String secret = request.getSecret();
        //判空
        if (StringUtil.isBlank(url) || StringUtil.isBlank(key) || StringUtil.isBlank(secret)) {
            throw new HttpProcessException("url key secret can not be null or empty");
        }
        //毫秒级时间
        long localTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        if (!url.contains("?")) {
            sb.append(url).append("?timestamp=").append(localTime);
        } else {
            sb.append(url).append("&timestamp=").append(localTime);
        }
        //加签
        String shaUrl = sb.toString();
        String sha = HmacUtils.hmacSha256Hex(secret,shaUrl.substring(shaUrl.indexOf("?") + 1));
        sb.append("&sign=").append(sha);
        request.setUrl(sb.toString());
        return request;
    }

}
