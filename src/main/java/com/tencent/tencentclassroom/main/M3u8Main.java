package com.tencent.tencentclassroom.main;

import com.tencent.tencentclassroom.download.M3u8Download;
import com.tencent.tencentclassroom.download.M3u8DownloadFactory;
import com.tencent.tencentclassroom.listener.DownloadListener;
import com.tencent.tencentclassroom.model.M3u8Execle;
import com.tencent.tencentclassroom.utils.Constant;
import com.tencent.tencentclassroom.utils.ExecleFileUtils;
import com.tencent.tencentclassroom.utils.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/12/14 16:02
 */

public class M3u8Main {

    private static final String M3U8URL = "https://1258712167.vod2.myqcloud.com/fb8e6c92vodtranscq1258712167/4bf587e35285890808757189956/drm/voddrm.token.dWluPTEzNzI4Nzc3MjA7c2tleT07cHNrZXk9O3Bsc2tleT0wMDA0MDAwMDE4YjI3N2RmODFkNWE3Y2Y3YTRhMzEyZGRiYmQzOTQzNTViY2RiYTQ1NmRkMzYzZDQzYzMzODBmZmQyNmI4YzI1NjExMmMyZTRjMzA0MWY4O2V4dD07dWlkX3R5cGU9MDt1aWRfb3JpZ2luX3VpZF90eXBlPTA7dWlkX29yaWdpbl9hdXRoX3R5cGU9MDtjaWQ9MjMxNTE2O3Rlcm1faWQ9MTAyNzU0MDM1O3ZvZF90eXBlPTA=.v.f745468.m3u8?t=62BBBBF9&exper=0&us=4193026370641833329&sign=617cd4d295960e0070f8991ff7fab681";

    public static void main(String[] args) throws Exception {
        List<M3u8Execle> m3u8Execles = ExecleFileUtils.readExecle("E:\\【图灵VIP严选课程】JAVA互联网架构师专题分布式高并第四期.xlsx");
        String mainPat="E:\\m3u8\\";
        for (M3u8Execle m3u8Execle:m3u8Execles){
            if (!StringUtils.isEmpty(m3u8Execle.getLink())){

                downloadM3u8(m3u8Execle,mainPat);
            }
        }
    }
    public static boolean downloadM3u8(M3u8Execle m3u8Execle,String mainPath) throws InterruptedException {
        System.out.println("请求进来了");
        if (StringUtils.isEmpty(m3u8Execle.getLink())){
            System.out.println("没有文件链接"+m3u8Execle.toString());
            return false;
        }
        M3u8Download m3u8Download = M3u8DownloadFactory.getInstance(m3u8Execle.getLink());
        //设置生成目录
        m3u8Download.setDir(mainPath+m3u8Execle.getFolderName()+"/"+m3u8Execle.getFileName());
        //设置生成目录
        m3u8Download.setMp4Dir(mainPath+m3u8Execle.getFolderName());
        //设置视频名称
        m3u8Download.setFileName(m3u8Execle.getFileName());
        //设置线程数
        m3u8Download.setThreadCount(100);
        //设置重试次数
        m3u8Download.setRetryCount(100);
        //设置连接超时时间（单位：毫秒）
        m3u8Download.setTimeoutMillisecond(10000L);
        /*
        设置日志级别
        可选值：NONE INFO DEBUG ERROR
        */
        m3u8Download.setLogLevel(Constant.INFO);
        //设置监听器间隔（单位：毫秒）
        m3u8Download.setInterval(500L);
        //添加额外请求头
      /*  Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "text/html;charset=utf-8");
        m3u8Download.addRequestHeaderMap(headersMap);*/
        //如果需要的话设置http代理
        //m3u8Download.setProxy("172.50.60.3",8090);
        //添加监听器
        m3u8Download.addListener(new DownloadListener() {
            @Override
            public void start() {
                System.out.println("开始下载！");
            }

            @Override
            public void process(String downloadUrl, int finished, int sum, float percent) {
                System.out.println("下载网址：" + downloadUrl + "\t已下载" + finished + "个\t一共" + sum + "个\t已完成" + percent + "%");
            }

            @Override
            public void speed(String speedPerSecond) {
                System.out.println("下载速度：" + speedPerSecond);
            }

            @Override
            public void end() {
                System.out.println("下载完毕");

            }
        });
        //开始下载
        m3u8Download.start();

        return false;
    }
}
