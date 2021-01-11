package src.main.java.cn.org.assetcloud.util;

/**
 * @author Juxiu Ren Jianchao
 */
public class StringUtil {
    public static boolean isBlank(String s) {
        return s == null || "".equals(s.trim());
    }
}
