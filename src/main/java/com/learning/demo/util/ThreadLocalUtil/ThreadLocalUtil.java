package com.learning.demo.util.ThreadLocalUtil;

/**
 * <p>Title: xCRMS </p>
 * Description: <br>
 * Copyright: CorpRights xQuant.com<br>
 * Company: xQuant.com<br>
 *
 * @author wenchao.chai
 * @version 1.1.1.RELEASE
 * @date 2021/3/18 10:53
 */
public class ThreadLocalUtil {
    public static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置值
     * @param value
     */
    public static void setValue(String value){
        THREAD_LOCAL.set(value);
    }

    /**
     * 获取值
     * @return
     */
    public static String getValue(){
        return THREAD_LOCAL.get();
    }
    /**
     * 删除值
     * 保证垃圾回收
     */
    public static void removeValue(){
        THREAD_LOCAL.remove();
    }
}
