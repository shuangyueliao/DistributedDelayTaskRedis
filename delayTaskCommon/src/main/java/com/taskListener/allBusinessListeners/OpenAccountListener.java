package com.taskListener.allBusinessListeners;

import com.IAddDelayedTask;
import com.annotation.DelayTaskType;
import com.enums.BusinessTypeEnum;
import com.taskListener.IDelayedTaskLisenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 开户延时任务监听
 */
@Slf4j
@Service
@DelayTaskType(topic= BusinessTypeEnum.opendAccount,topicDesc = "开户任务")
public class OpenAccountListener implements IDelayedTaskLisenter {

    @Override
    public boolean execute(String topic, String key,IAddDelayedTask addDelayedTask) {
      log.info("延时任务开始执行，topic：{} ，key ：{}",topic,key);
      try {
          Thread.sleep(500);
          log.info("延时任务执行成功，topic：{} ，key ：{}",topic,key);
          return true;
      }catch (Exception e){
          log.error("执行失败！！！",e);
          log.info("延时任务模块，topic：{} ,key：{}, addDelayedTask :{}, 延时任务执行失败，将要重新添加，下一次执行将会是300s以后",
                  topic,key,addDelayedTask.getClass().getSimpleName());
          addDelayedTask.addDelayedTask(topic,key,300, TimeUnit.SECONDS);
      }
      return false;
    }
}