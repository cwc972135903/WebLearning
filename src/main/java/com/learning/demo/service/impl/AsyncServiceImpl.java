package com.learning.demo.service.impl;

import com.learning.demo.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
@Service
public class AsyncServiceImpl implements AsyncService {
    /**
     * 指定一个线程池
     * @return
     */
    @Override
    @Async("taskExecutor")
    public Future<String> executeAsyncResult() {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("当前运行的线程名称(有返回结果)：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AsyncResult<>(Thread.currentThread().getName());
    }

    /**
     * 指定一个线程池
     * @return
     */
    @Override
    @Async("taskExecutor")
    public String executeAsync() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("当前运行的线程名称(无返回结果)：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName();
    }
}
