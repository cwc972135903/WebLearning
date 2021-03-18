package com.learning.demo.service;

import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * <p>Title: xCRMS </p>
 * Description: <br>
 * Copyright: CorpRights xQuant.com<br>
 * Company: xQuant.com<br>
 *
 * @author wenchao.chai
 * @version 1.1.1.RELEASE
 * @date 2021/3/17 12:15
 */
public interface AsyncService {
    /**
     * 异步线程池执行任务
     * @return
     */
    Future<String> executeAsyncResult();

    /**
     * 异步线程池执行任务
     * @return
     */
    String executeAsync();
}
