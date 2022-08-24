package com.tencent.tencentclassroom.execl;

import com.tencent.tencentclassroom.main.M3u8Main;
import com.tencent.tencentclassroom.model.M3u8Execle;
import com.tencent.tencentclassroom.utils.ExecleFileUtils;
import com.tencent.tencentclassroom.utils.SpringBootBeanUtil;
import com.tencent.tencentclassroom.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.tencent.tencentclassroom.controller.VideoController;

import java.time.LocalDate;
import java.util.List;

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
    public void getTest() throws Exception {

          String dateStr="2022-06-09";
          LocalDate a;
        a = LocalDate.parse(dateStr);
        System.out.println(a);
//        List<M3u8Execle> m3u8Execles = ExecleFileUtils.readExecle("E:\\【图灵VIP严选课程】JAVA互联网架构师专题分布式高并第四期.xlsx");
//        String mainPat="E:\\m3u8\\";
//        for (M3u8Execle m3u8Execle:m3u8Execles){
//            if (!StringUtils.isEmpty(m3u8Execle.getLink())){
//                ThreadPoolTaskExecutor mergeXyzMap4TaskExecutor= (ThreadPoolTaskExecutor) SpringBootBeanUtil.getBean("mergeXyzMap4");
//                while (mergeXyzMap4TaskExecutor.getActiveCount()<=0){
//                    M3u8Main.downloadM3u8(m3u8Execle,mainPat);
//                }
//
//            }
//        }

//        VideoController mergeXyzMap4TaskExecutor= SpringBootBeanUtil.getBean(VideoController.class);
//        ThreadPoolTaskExecutor mergeXyzMap4TaskExecutor= (ThreadPoolTaskExecutor) SpringBootBeanUtil.getBean("mergeXyzMap4");
//
//        System.out.println("初始化"+mergeXyzMap4TaskExecutor.getActiveCount());
//        mergeXyzMap4TaskExecutor.submit(new Thread(()->{
//            try {
//                Thread.sleep(1000);
//                System.out.println("执行完成");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }));
//
//        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());
//        System.out.println(mergeXyzMap4TaskExecutor.getActiveCount());
    }
}
