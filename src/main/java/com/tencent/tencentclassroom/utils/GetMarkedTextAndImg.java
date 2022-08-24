package com.tencent.tencentclassroom.utils;

import com.spire.doc.*;
import com.spire.doc.documents.CommentMark;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.Comment;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/7/27$
 * @since com.tencent.tencentclassroom.utils
 */
public class GetMarkedTextAndImg {
    public static void main(String[] args)throws IOException {
        //加载Word文档
        Document doc = new Document();
        doc.loadFromFile("C:\\Users\\binar\\Documents\\WeChat Files\\wxid_qxssx0el7b0022\\FileStorage\\File\\2022-07\\违禁词.docx");

        //获取文档中批注
        for(int a = 0;a<doc.getComments().getCount();a++)
        {
            Comment comment = doc.getComments().get(a);
            //获取批注的开始标记和结束标记
            Paragraph para = comment.getOwnerParagraph();
            CommentMark start = comment.getCommentMarkStart();
            CommentMark end = comment.getCommentMarkEnd();

            //获取开始标记和结束标记在段落中的索引
            int indexOfStart = para.getChildObjects().indexOf(start);
            int indexOfEnd = para.getChildObjects().indexOf(end);

            String markedText = "";
            ArrayList images = new ArrayList();
            //根据索引获取批注的开始标记和结束标记之间的文字、图片
            for (int i = indexOfStart + 1; i < indexOfEnd; i++)
            {
                if (para.getChildObjects().get(i) instanceof TextRange)
                {
                    TextRange range = (TextRange) para.getChildObjects().get(i);
                    markedText += range.getText();
                }

                if (para.getChildObjects().get(i) instanceof DocPicture)
                {
                    DocPicture picture = (DocPicture) para.getChildObjects().get(i);
                    images.add(picture.getImage());
                }

            }

            //打印批注标记的文本
            System.out.println(markedText+111);

            //提取批注标记的图片
            for (int z = 0; z< images.size(); z++)
            {
                File file = new File(String.format("MarkedImg.png", z));
                ImageIO.write((RenderedImage) images.get(z), "PNG", file);
            }
        }

    }


}
