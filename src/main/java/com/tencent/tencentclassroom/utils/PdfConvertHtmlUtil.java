package com.tencent.tencentclassroom.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
/**
 * 功能描述:
 *
 * @author zhushuai$
 * 创建日期 2022/6/23$
 * @since com.tencent.tencentclassroom.utils
 */
@Slf4j
public class PdfConvertHtmlUtil {

    /**
     * PDF文档流转Png
     *
     * @param input
     * @return BufferedImage
     */
    public static BufferedImage pdfStreamToPng(byte[] input) {
        PDDocument doc = null;
        PDFRenderer renderer = null;
        try {
            doc = PDDocument.load(input);
            renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            BufferedImage image = null;
            for (int i = 0; i < pageCount; i++) {
                if (image != null) {
                    image = combineBufferedImages(image, renderer.renderImage(i));
                }

                if (i == 0) {
                    // 设置图片的分辨率
                    image = renderer.renderImage(i); // Windows native DPI
                    // 如果是PNG图片想要背景透明的话使用下面这个
                    // BufferedImage image = render.renderImageWithDPI(i, 296, ImageType.ARGB);
                }
            }
            // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
            return combineBufferedImages(image);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * PDF文档流转Png
     *
     * @param pdfFileInputStream
     * @return BufferedImage
     */
    public static BufferedImage pdfStreamToPng(InputStream pdfFileInputStream) {
        PDDocument doc = null;
        PDFRenderer renderer = null;
        try {
            doc = PDDocument.load(pdfFileInputStream);
            renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            BufferedImage image = null;            for (int i = 0; i < pageCount; i++) {
                if (image != null) {
                    image = combineBufferedImages(image, renderer.renderImageWithDPI(i, 144));
                }

                if (i == 0) {
                    image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                }
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图

            }
            return combineBufferedImages(image);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 压缩图片
     *
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * BufferedImage拼接处理，添加分割线
     *
     * @param images
     * @return BufferedImage
     */
    public static BufferedImage combineBufferedImages(BufferedImage... images) {
        int height = 0;
        int width = 0;
        for (BufferedImage image : images) {
            //height += Math.max(height, image.getHeight());
            height += image.getHeight();
            width = image.getWidth();
        }
        BufferedImage combo = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = combo.createGraphics();
        int x = 0;
        int y = 0;
        for (BufferedImage image : images) {
            //int y = (height - image.getHeight()) / 2;
            g2.setStroke(new BasicStroke(2.0f));// 线条粗细
            g2.setColor(new Color(193, 193, 193));// 线条颜色
            g2.drawLine(x, y, width, y);// 线条起点及终点位置

            g2.drawImage(image, x, y, null);
            //x += image.getWidth();
            y += image.getHeight();

        }
        return combo;
    }

    /**
     * 通过Base64创建HTML文件并输出html文件
     *
     * @param base64
     * @param htmlPath html保存路径
     * @param title    html标题
     */
    public static void createHtmlByBase64(String base64, String htmlPath, String title) {
        PrintStream printStream = null;
        try {
            // 打开文件
            printStream = new PrintStream(new FileOutputStream(htmlPath));
        } catch (FileNotFoundException e) {
            log.error("create file error!", e);
            return;
        }
        try {
            // 将HTML文件内容写入文件中
            String htmlString = getHtmlString(base64, title);
            printStream.println(htmlString);
        } catch (Exception e) {
            log.error("createHtmlByBase64 error!", e);
        } finally {
            printStream.close();
        }

    }

    /**
     * 通过Base64创建HTML文件并输出html文件
     *
     * @param base64
     */
    public static String getHtmlString(String base64, String title) {
        StringBuilder stringHtml = new StringBuilder();
        // 输入HTML文件内容
        stringHtml.append("<html><head>");
        stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        stringHtml.append("<title>").append(title).append("</title>");
        stringHtml.append("</head>");
        stringHtml.append("<body style=\"" + "text-align: center;\">");
        stringHtml.append("<img src=\"data:image/png;base64,").append(base64).append("\"/>");
        stringHtml.append("</body><script>document.oncontextmenu = function () { return false }</script></html>");
        return stringHtml.toString();
    }

    /**
     * bufferedImage 转为 base64编码
     *
     * @param bufferedImage
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String png_base64 = "";
        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);// 写入流中
            byte[] bytes = byteArrayOutputStream.toByteArray();// 转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            // 转换成base64串 删除 \r\n
            png_base64 = encoder.encodeBuffer(bytes).trim()
                    .replaceAll("\n", "")
                    .replaceAll("\r", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return png_base64;
    }

    // 测试
    public static void main(String[] args) {
        File file = new File("E:\\opt\\putImage\\202206\\20220622\\20220622_3e23e036-b9bf-4527-9b5e-df374763d6fd.pdf");
        String htmlPath = "E:\\2593xedkyxgs.html";
        InputStream inputStream = null;
        BufferedImage bufferedImage = null;
        try {
            inputStream = new FileInputStream(file);
            bufferedImage = pdfStreamToPng(inputStream);
            String base64_png = bufferedImageToBase64(bufferedImage);
            createHtmlByBase64(base64_png, htmlPath, "授权书");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
