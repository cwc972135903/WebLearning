package com.learning.demo.util.ThreadLocalUtil;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * <p>Title: xCRMS </p>
 * Description: <br>
 * Copyright: CorpRights xQuant.com<br>
 * Company: xQuant.com<br>
 *
 * @author wenchao.chai
 * @version 1.1.1.RELEASE
 * @date 2021/3/18 10:54
 */
public class TransmittableThreadLocalUtil {
    public static final TransmittableThreadLocal<String> THREAD_LOCAL = new TransmittableThreadLocal<>();
    /**
     * 设置线程需要保存的值
     */
    public static void setValue (String str) {
        THREAD_LOCAL.set(str);
    }

    /**
     * 获取线程中保存的值
     * @return
     */
    public static String getValue() {
        return THREAD_LOCAL.get();
    }

    /**
     * 移除线程中保存的值
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
