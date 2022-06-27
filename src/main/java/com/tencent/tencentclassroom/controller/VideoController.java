package com.tencent.tencentclassroom.controller;

import com.tencent.tencentclassroom.download.M3u8DownloadFactory;
import com.tencent.tencentclassroom.main.M3u8Main;
import com.tencent.tencentclassroom.model.M3u8Execle;
import com.tencent.tencentclassroom.utils.ExecleFileUtils;
import com.tencent.tencentclassroom.utils.SpringBootBeanUtil;
import com.tencent.tencentclassroom.utils.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/6/21$
 * @since com.tencent.tencentclassroom.controller
 */
@RestController
public class VideoController {

    @RequestMapping(value = "uploadCovePdf", method = RequestMethod.POST)
    public void uploadCovePdf(@RequestParam(required = false, value = "file") MultipartFile file) throws Exception {
        List<M3u8Execle> m3u8Execles = ExecleFileUtils.readExecleFile(file);
        String mainPat="/opt/roomVideo/";
        ThreadPoolTaskExecutor mergeXyzMap4TaskExecutor= (ThreadPoolTaskExecutor) SpringBootBeanUtil.getBean("mergeXyzMap4");
        FutureTask<Boolean> callable;
        File file1=new File("E:\\失败文件.txt");

        for (M3u8Execle m3u8Execle:m3u8Execles){
            if (!StringUtils.isEmpty(m3u8Execle.getLink())){
                try {
                    System.out.println("开始执行文件"+m3u8Execle.toString());
                    M3u8Main.downloadM3u8(m3u8Execle,mainPat);
                } catch (Exception e) {
                    writeError(m3u8Execle.toString());
                    M3u8DownloadFactory.destroied();
                    e.printStackTrace();
                }

            }
        }
        System.out.println();
    }


    private void writeError(String fileName){
        FileWriter noFile;
        BufferedWriter bufferedWriter = null;
        try {

            noFile=new FileWriter("E:\\失败文件.txt",true);
            bufferedWriter=new BufferedWriter(noFile,1024);
            bufferedWriter.write("\n"+fileName);
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("写入不能处理文件内容失败"+e);
        }
        finally {
            try {
                assert bufferedWriter != null;
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
