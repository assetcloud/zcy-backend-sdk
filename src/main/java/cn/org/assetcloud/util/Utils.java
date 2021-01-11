package src.main.java.cn.org.assetcloud.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Juxiu Ren Jianchao
 */
public class Utils {
    public static final String ENTITY_STRING="$ENTITY_STRING$";
    public static final String ENTITY_JSON="$ENTITY_JSON$";
    public static final String ENTITY_FILE="$ENTITY_FILEE$";
    public static final String ENTITY_BYTES="$ENTITY_BYTES$";
    public static final String ENTITY_INPUTSTREAM="$ENTITY_INPUTSTREAM$";
    public static final String ENTITY_SERIALIZABLE="$ENTITY_SERIALIZABLE$";
    public static final String ENTITY_MULTIPART="$ENTITY_MULTIPART$";
    private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_JSON, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE, ENTITY_MULTIPART);

    /**
     * 检测url是否含有参数，如果有，则把参数加到参数列表中
     *
     * @param url	资源地址
     * @param nvps	参数列表
     * @param encoding	编码
     * @return	返回去掉参数的url
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding) throws UnsupportedEncodingException {
        // 检测url中是否存在参数
        if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
            Map<String, Object> map = buildParas(url.substring(url.indexOf("?") + 1));
            map2HttpEntity(nvps, map, encoding);
            url = url.substring(0, url.indexOf("?"));
        }
        return url;
    }
    /**
     *
     * 参数转换，将map中的参数，转到参数列表中
     *
     * @param nvps				参数列表
     * @param map				参数列表（map）
     * @param encoding			编码
     * @return					返回HttpEntity
     * @throws UnsupportedEncodingException  不支持的编码异常
     */
    public static HttpEntity map2HttpEntity(List<NameValuePair> nvps, Map<String, Object> map, String encoding) throws UnsupportedEncodingException {
        HttpEntity entity = null;
        if(map!=null && map.size()>0){
            boolean isSpecial = false;
            // 拼接参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(SPECIAL_ENTITIY.contains(entry.getKey())){
                    //判断是否在之中
                    isSpecial = true;
                    if(ENTITY_STRING.equals(entry.getKey())){
                        //string
                        entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
                        break;
                    }else if(ENTITY_JSON.equals(entry.getKey())){
                        entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
                        String contentType = "application/json";
                        if (encoding != null) {
                            contentType += ";charset=" + encoding;
                        }
                        ((StringEntity) entity).setContentType(contentType);
                        break;
                    }else if(ENTITY_BYTES.equals(entry.getKey())){
                        //file
                        entity = new ByteArrayEntity((byte[])entry.getValue());
                        break;
                    }else if(ENTITY_FILE.equals(entry.getKey())){
                        //file
                        if(File.class.isAssignableFrom(entry.getValue().getClass())){
                            entity = new FileEntity((File)entry.getValue(), ContentType.APPLICATION_OCTET_STREAM);
                        }else if(entry.getValue().getClass()==String.class){
                            entity = new FileEntity(new File((String) entry.getValue()), ContentType.create("text/plain", "UTF-8"));
                        }
                        break;
                    }else if(ENTITY_INPUTSTREAM.equals(entry.getKey())){
                        //inputstream
//						entity = new InputStreamEntity();
                        break;
                    }else if(ENTITY_SERIALIZABLE.equals(entry.getKey())){
                        //serializeable
//						entity = new SerializableEntity()
                        break;
                    }else {
                        nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                    }
                }else{
                    nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
            }
            if(!isSpecial) {
                entity = new UrlEncodedFormEntity(nvps, encoding);
            }
        }
        return entity;
    }

    /**
     * 生成参数
     * 参数格式：k1=v1&amp;k2=v2
     *
     * @param paras				参数列表
     * @return					返回参数列表（map）
     */
    public static Map<String,Object> buildParas(String paras){
        String[] p = paras.split("&");
        String[][] ps = new String[p.length][2];
        int pos = 0;
        for (int i = 0; i < p.length; i++) {
            pos = p[i].indexOf("=");
            ps[i][0]=p[i].substring(0,pos);
            ps[i][1]=p[i].substring(pos+1);
            pos = 0;
        }
        return buildParas(ps);
    }
    /**
     * 生成参数
     * 参数类型：{{"k1","v1"},{"k2","v2"}}
     *
     * @param paras 				参数列表
     * @return						返回参数列表（map）
     */
    public static Map<String,Object> buildParas(String[][] paras){
        // 创建参数队列
        Map<String,Object> map = new HashMap<String, Object>();
        for (String[] para: paras) {
            map.put(para[0], para[1]);
        }
        return map;
    }
}
