package com.learning.demo.controller;

import com.learning.demo.service.AsyncService;
import com.learning.demo.util.ThreadLocalUtil.InheritableThreadLocalUtil;
import com.learning.demo.util.ThreadLocalUtil.ThreadLocalUtil;
import com.learning.demo.util.ThreadLocalUtil.TransmittableThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
@RestController
public class ThreadController {

    @Autowired
    private AsyncService asyncService;

//    @Qualifier("taskExecutor")
//    @Autowired
//    private TaskExecutor taskExecutor;

    @Qualifier("taskExecutor")
    @Autowired
    private Executor taskExecutor;

    @GetMapping("/thread")
    public void thread() {
        //调用service层的任务
        asyncService.executeAsync();
    }

    @GetMapping("/thread/result")
    public String getThreadResult() throws InterruptedException, ExecutionException {
        //调用service层的任务
        Future<String> stringAsyncResult = asyncService.executeAsyncResult();
        return stringAsyncResult.get();
    }

    @GetMapping("/thread/set/{coreSize}/{maxSize}/{queueSize}")
    public void set(@PathVariable("coreSize") int coreSize,@PathVariable("maxSize") int maxSize,@PathVariable("queueSize") int queueSize ) throws InvocationTargetException, IllegalAccessException {
        //动态修改线程池参数
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)this.taskExecutor;
        Method[] declaredMethods = taskExecutor.getClass().getDeclaredMethods();
        for (Method method : declaredMethods){
            //setMaxPoolSize--setCorePoolSize--setQueueCapacity
            if("setMaxPoolSize".equals(method.getName())){
                method.invoke(taskExecutor, maxSize);
                System.out.println("动态修改最大线程数成功");
            }
            if("setCorePoolSize".equals(method.getName())){
                method.invoke(taskExecutor, coreSize);
                System.out.println("动态修改核心线程数成功");
            }
            if("setQueueCapacity".equals(method.getName())){
                method.invoke(taskExecutor, queueSize);
                System.out.println("动态修改队列容量成功");
            }
        }

    }

    @GetMapping("/tl1")
    public Object threadLocalTest1(){
        ThreadLocalUtil.THREAD_LOCAL.set(Thread.currentThread().getName());
        System.out.println("当前运行是的默认TL的线程（控制器）：" + Thread.currentThread().getName()+":TL:" + ThreadLocalUtil.THREAD_LOCAL.get());
        String s = asyncService.executeDefault();
        return s;
    }

    @GetMapping("/tl2/{value}")
    public Object threadLocalTest2(@PathVariable("value") String value) throws InterruptedException {
        //InheritableThreadLocalUtil.THREAD_LOCAL.set(value);
        TransmittableThreadLocalUtil.THREAD_LOCAL.set(value);
        System.out.println("当前运行是的异步TL的线程-控制器：" + Thread.currentThread().getName()+":TL:" + value);
        String s = asyncService.executeAsyncTL();
        return s;
    }

    @GetMapping("/tl3")
    public Object threadLocalTest3() throws ExecutionException, InterruptedException {
        ThreadLocalUtil.THREAD_LOCAL.set("tl3");
        Future<String> stringFuture = asyncService.executeAsyncResultTL();
        String s = stringFuture.get();
        return s;
    }

}