package com.funny.study.nacos.util;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@Component
public class AsyncTaskUtil {
   private static volatile Map<String, AsyncContext> ASYNC_CONTEXT_MAP = new ConcurrentHashMap<>();
   private static final LinkedBlockingDeque<LongPollingEvent<?>> LONG_POLLING_EVENT_QUEUE = new LinkedBlockingDeque<>();
   private static final ScheduledThreadPoolExecutor TIME_OUT_CHECKER = new ScheduledThreadPoolExecutor(4, new RejectedExecutionHandler() {
       @Override
       public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

       }
   });

   private static void writePollingResponse(String requestId,BaseResult<?> resp) throws IOException {
		if (ASYNC_CONTEXT_MAP.get(requestId) != null) {
			synchronized (requestId.intern()){
				if (ASYNC_CONTEXT_MAP.get(requestId) != null) {
					log.info("事件触发长轮询回复----,requestId={},resp={}",requestId,resp);
					AsyncContext asyncContext = ASYNC_CONTEXT_MAP.get(requestId);
					ServletResponse response = asyncContext.getResponse();
					response.setContentType("application/json;charset=utf-8");
					response.setCharacterEncoding("utf-8");
					response.getWriter().append(JSON.toJSONString(resp));
					asyncContext.complete();
					ASYNC_CONTEXT_MAP.remove(requestId);
				}else {
					log.info("长轮询已回复，超时回复终止----,requestId={},resp={}",requestId,resp);
				}
			}
		}else {
			log.info("长轮询已回复，超时回复终止----,requestId={},resp={}",requestId,resp);
		}
	}

   /**
    *
    * @author HeJun
    * @date 2020年5月14日
    * @Version V1.0
    * @Description 长轮询
    * @param request
    * @param requestId
    * @param timeOut
    */
   public static void longPolling(HttpServletRequest request, String requestId, long timeOut) {
      //请求异步域对象
      AsyncContext asyncContext = request.startAsync();
      //关闭长轮询自动超时处理
      asyncContext.setTimeout(0);
      //消息等待map添加异步域对象
      ASYNC_CONTEXT_MAP.put(requestId, asyncContext);
      //注册长轮询超时未响应兜底任务
      TIME_OUT_CHECKER.schedule(() -> {
         try {
            writePollingResponse(requestId, BaseResult.error("time out"));
         } catch (IOException e) {
            log.error(e.getMessage(), e);
         }
      }, timeOut,TimeUnit.SECONDS);
   }

   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   static class LongPollingEvent<T> {
      // AsyncContext对象id
      private String requestId;
      private T content;
      private LongPollingTask<T> task;
   }

   public static interface LongPollingTask<T> {
       BaseResult<T> execute(T content);
   }

   /**
    * 添加长轮询立即响应触发事件
    * @param <T>
    * @param requestId
    * @param content
    * @return
    */
   public static <T> boolean addLongPollingRespEvent(String requestId, T content, LongPollingTask<T> task) {
      return LONG_POLLING_EVENT_QUEUE.add(new LongPollingEvent<T>(requestId, content, task));
   }

   /**
    * 长轮询事件触发后的处理守护线程，监听长轮询立即回复事件队列
    */
   static {
      Thread thread = new Thread(() -> {
         while (true) {
            try {
               LongPollingEvent pollingEvent = LONG_POLLING_EVENT_QUEUE.take();
               runAsync(() -> {
                  try {
                     BaseResult<?> resp = pollingEvent.getTask().execute(pollingEvent.getContent());
                     writePollingResponse(pollingEvent.getRequestId(), resp);
                  } catch (Exception e) {
                     log.error(e.getMessage(), e);
                  }
               });
            } catch (InterruptedException e) {
               log.error(e.getMessage(), e);
            }
         }
      });
      thread.setDaemon(true);
      thread.start();
   }
}

