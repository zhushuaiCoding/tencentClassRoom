package com.tencent.tencentclassroom.execl;

import com.tencent.tencentclassroom.utils.SpringBootBeanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.tencent.tencentclassroom.controller.VideoController;
/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/6/22$
 * @since com.tencent.tencentclassroom.execl
 */
@SpringBootTest
public class ThreadPoolTest {

    @Test
    public void getTest(){
//        VideoController mergeXyzMap4TaskExecutor= SpringBootBeanUtil.getBean(VideoController.class);
        ThreadPoolTaskExecutor mergeXyzMap4TaskExecutor= (ThreadPoolTaskExecutor) SpringBootBeanUtil.getBean("mergeXyzMap4");

        System.out.println("初始化"+mergeXyzMap4TaskExecutor.getActiveCount());
        mergeXyzMap4TaskExecutor.submit(new Thread(()->{
            try {
                Thread.sleep(1000);
                System.out.println("执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());
        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());
    }
}
