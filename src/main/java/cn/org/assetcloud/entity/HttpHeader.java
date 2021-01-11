package src.main.java.cn.org.assetcloud.entity;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Juxiu Ren Jianchao
 */
public class HttpHeader {

    /**
     * Http头信息
     *
     */
    private static class HttpReqHead {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String COOKIE = "Cookie";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String DATE= "Date";
        public static final String EXPECT = "Expect";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match ";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String KEEP_ALIVE = "Keep-Alive";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String TE = "TE";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
    }

    /**
     * 常用头信息配置
     *
     *
     */
    public static class Headers{
        public static final String APP_FORM_URLENCODED="application/x-www-form-urlencoded";
        public static final String TEXT_PLAIN="text/plain";
        public static final String TEXT_HTML="text/html";
        public static final String TEXT_XML="text/xml";
        public static final String TEXT_JSON="text/json";
        public static final String CONTENT_CHARSET_ISO_8859_1 = Consts.ISO_8859_1.name();
        public static final String CONTENT_CHARSET_UTF8 = Consts.UTF_8.name();
        public static final String DEF_PROTOCOL_CHARSET = Consts.ASCII.name();
        public static final String CONN_CLOSE = "close";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String EXPECT_CONTINUE = "100-continue";
    }

    private HttpHeader() {};

    public static HttpHeader custom() {
        return new HttpHeader();
    }


    /**
     * head信息
     */
    private Map<String, Header> headerMaps = new HashMap<String, Header>();

    /**
     * 自定义header头信息
     *
     * @param key	header-key
     * @param value	header-value
     * @return 返回当前对象
     */
    public HttpHeader other(String key, String value) {
        headerMaps.put(key, new BasicHeader(key, value));
        return this;
    }
    /**
     * 指定客户端能够接收的内容类型
     * 例如：Accept: text/plain, text/html
     *
     * @param accept accept
     * @return 返回当前对象
     */
    public HttpHeader accept(String accept) {
        headerMaps.put(HttpReqHead.ACCEPT,
                new BasicHeader(HttpReqHead.ACCEPT, accept));
        return this;
    }

    /**
     * 浏览器可以接受的字符编码集
     * 例如：Accept-Charset: iso-8859-5
     *
     * @param acceptCharset accept-charset
     * @return 返回当前对象
     */
    public HttpHeader acceptCharset(String acceptCharset) {
        headerMaps.put(HttpReqHead.ACCEPT_CHARSET,
                new BasicHeader(HttpReqHead.ACCEPT_CHARSET, acceptCharset));
        return this;
    }

    /**
     * 指定浏览器可以支持的web服务器返回内容压缩编码类型
     * 例如：Accept-Encoding: compress, gzip
     *
     * @param acceptEncoding accept-encoding
     * @return 返回当前对象
     */
    public HttpHeader acceptEncoding(String acceptEncoding) {
        headerMaps.put(HttpReqHead.ACCEPT_ENCODING,
                new BasicHeader(HttpReqHead.ACCEPT_ENCODING, acceptEncoding));
        return this;
    }

    /**
     * 浏览器可接受的语言
     * 例如：Accept-Language: en,zh
     *
     * @param acceptLanguage accept-language
     * @return 返回当前对象
     */
    public HttpHeader acceptLanguage(String acceptLanguage) {
        headerMaps.put(HttpReqHead.ACCEPT_LANGUAGE,
                new BasicHeader(HttpReqHead.ACCEPT_LANGUAGE, acceptLanguage));
        return this;
    }

    /**
     * 可以请求网页实体的一个或者多个子范围字段
     * 例如：Accept-Ranges: bytes
     *
     * @param acceptRanges accept-ranges
     * @return 返回当前对象
     */
    public HttpHeader acceptRanges(String acceptRanges) {
        headerMaps.put(HttpReqHead.ACCEPT_RANGES,
                new BasicHeader(HttpReqHead.ACCEPT_RANGES, acceptRanges));
        return this;
    }

    /**
     * HTTP授权的授权证书
     * 例如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param authorization authorization
     * @return 返回当前对象
     */
    public HttpHeader authorization(String authorization) {
        headerMaps.put(HttpReqHead.AUTHORIZATION,
                new BasicHeader(HttpReqHead.AUTHORIZATION, authorization));
        return this;
    }
    /**
     * 获取head信息
     *
     * @return String
     */
    private String get(String headName) {
        if (headerMaps.containsKey(headName)) {
            return headerMaps.get(headName).getValue();
        }
        return null;
    }

    /**
     * 返回header头信息
     *
     * @return	返回构建的header头信息数组
     */
    public Header[] build() {
        Header[] headers = new Header[headerMaps.size()];
        int i = 0;
        for (Header header : headerMaps.values()) {
            headers[i] = header;
            i++;
        }
        headerMaps.clear();
        headerMaps = null;
        return headers;
    }
}
