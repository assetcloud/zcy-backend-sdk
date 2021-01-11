package src.main.java.cn.org.assetcloud.entity;

import lombok.Data;

/**
 * @author JuXiu Ren Jianchao
 */
@Data
public class AssetCloudRequest {

    private static final long serialVersionUID = 1L;

    private String url;

    private String key;

    private String secret;

    private String body;

    private HttpMethods httpMethods;
}
