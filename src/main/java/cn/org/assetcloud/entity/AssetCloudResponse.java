package src.main.java.cn.org.assetcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Juxiu Ren Jianchao
 */
@Data
public class AssetCloudResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *状态码
     */
    private int code;

    /**
     *是否成功
     */
    private Boolean success;

    /**
     * 承载数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String msg;
}
