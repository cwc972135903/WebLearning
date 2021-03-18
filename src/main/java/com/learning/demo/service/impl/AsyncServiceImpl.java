package com.learning.demo.service.impl;

import com.learning.demo.service.AsyncService;
import com.learning.demo.util.ThreadLocalUtil.InheritableThreadLocalUtil;
import com.learning.demo.util.ThreadLocalUtil.ThreadLocalUtil;
import com.learning.demo.util.ThreadLocalUtil.TransmittableThreadLocalUtil;
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
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    /**
     * 指定一个线程池
     * @return
     */
    @Override
    @Async("taskExecutor")
    public Future<String> executeAsyncResult() {
        try {
            TimeUnit.SECONDS.sleep(5);
            threadLocal.set(Thread.currentThread().getName());
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

    @Override
    @Async("taskExecutor")
    public Future<String> executeAsyncResultTL() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("当前运行是的同步的TL的线程：" + Thread.currentThread().getName()+":TL:" + ThreadLocalUtil.THREAD_LOCAL.get());
        return new AsyncResult<>(ThreadLocalUtil.THREAD_LOCAL.get());
    }

    @Override
    @Async("taskExecutor")
    public String executeAsyncTL() throws InterruptedException {
        String s = TransmittableThreadLocalUtil.THREAD_LOCAL.get();
        //TransmittableThreadLocalUtil.THREAD_LOCAL.remove();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("当前运行是的异步TL的线程：" + Thread.currentThread().getName()+":TL:" + s);
        TransmittableThreadLocalUtil.remove();
        return s;
    }

    @Override
    public String executeDefault() {
        System.out.println("当前运行是的默认TL的线程：" + Thread.currentThread().getName()+":TL:" + ThreadLocalUtil.THREAD_LOCAL.get());
        return ThreadLocalUtil.THREAD_LOCAL.get();
    }
}
