package com.funny.study.nacos.controller.longpoll;

import com.funny.study.nacos.util.AsyncTaskUtil;
import com.funny.study.nacos.util.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/longPolling")
@Slf4j
public class LongPollingController {
    //发送长轮询请求示例
    @GetMapping("/poll/{requestId}")
    public void poll(HttpServletRequest request, @PathVariable String requestId) {
        //启动长轮询，设置
        AsyncTaskUtil.longPolling(request ,requestId, 10);
    }

    //触发长轮询立即回复事件
    @GetMapping("/call/{requestId}/{content}")
    public void call(@PathVariable String requestId,@PathVariable String content) {
        AsyncTaskUtil.addLongPollingRespEvent(requestId, content,(t) -> {
            return BaseResult.OK("处理后结果："+t);
        });
    }

    //模拟tomcat容器线程阻塞
    @GetMapping("/block/{resp}")
    public String test(@PathVariable String resp) throws InterruptedException {
        log.info("进入test controller---resp={},thread={}",resp,Thread.currentThread().getName());
        Thread.currentThread().sleep(10000);
        return resp;
    }

    //servlet 3.0 异步处理
    @GetMapping("/async/{resp}")
    public void async(HttpServletRequest request, HttpServletResponse response){
        AsyncContext asyncContext = request.startAsync(request,response);
        // 设置异步请求超时时间为1秒
        asyncContext.setTimeout(1000);
        asyncContext.start(()->{
            //这里休眠2秒，模拟业务耗时
            try {
                TimeUnit.SECONDS.sleep(2);
                //这里是子线程，请求在这里被处理了
                asyncContext.getResponse().getWriter().write("ok");
                //调用complete()方法，表示请求请求处理完成
                asyncContext.complete();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
