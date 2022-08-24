package com.tencent.tencentclassroom.utils;

/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/7/27$
 * @since com.tencent.tencentclassroom.utils
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.model.XWPFCommentsDecorator;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class GetPiZhuFromWord {

    private static XWPFDocument comments;
    public static File getFile(String sampleFileName) {
        File f = new File(sampleFileName);
        return f;
    }
    public static InputStream openResourceAsStream(String sampleFileName) {
        File f = getFile(sampleFileName);
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static XWPFDocument openSampleDocument(String sampleName) throws IOException {
        InputStream is = openResourceAsStream(sampleName);
        return new XWPFDocument(is);
    }
    public static void set() throws IOException {
        comments = openSampleDocument("C:\\Users\\binar\\Documents\\WeChat Files\\wxid_qxssx0el7b0022\\FileStorage\\File\\2022-07\\违禁词.docx");
    }
    public static void main(String[] args) throws IOException{
        set();
        testComments();
    }
    public static void testComments() {
        int numComments = 0;
        Map<String,String> str=new HashMap<>();
        for (XWPFParagraph p : comments.getParagraphs()) {
            XWPFCommentsDecorator d = new XWPFCommentsDecorator(p, null);
            if (d.getCommentText().length() > 0) {
                numComments++;
                System.out.println(d.getCommentText());
            }
        }
        for (XWPFComment p : comments.getComments()) {

        }



    }
}
