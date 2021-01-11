package src.main.java.cn.org.assetcloud.builder;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Juxiu Ren Jianchao
 */
public class Hcb extends HttpClientBuilder {

    public boolean isSetPool=false;


    private Hcb(){}
    public static Hcb custom(){
        return new Hcb();
    }

    /**
     * 设置超时时间
     *
     * @param timeout		超市时间，单位-毫秒
     * @return	返回当前对象
     */
    @Deprecated
    public Hcb timeout(int timeout){
        return timeout(timeout, true);
    }

    /**
     * 设置超时时间以及是否允许网页重定向（自动跳转 302）
     *
     * @param timeout		超时时间，单位-毫秒
     * @param redirectEnable		自动跳转
     * @return	返回当前对象
     */
    @Deprecated
    public Hcb timeout(int timeout,  boolean redirectEnable){
        // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(redirectEnable)
                .build();
        return (Hcb) this.setDefaultRequestConfig(config);
    }
}
