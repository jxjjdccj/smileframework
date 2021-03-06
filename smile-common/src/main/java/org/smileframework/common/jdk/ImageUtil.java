package org.smileframework.common.jdk;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

/**
 * @author: cz
 * @description: 图像工具类
 */
public class ImageUtil {

    public static String  SRC_JPG ="_src.jpg"; //源JPG
    public static String  CUT_JPG ="_cut.jpg";//剪切后的JPG

    /**
     * 截取图片
     *
     * @param srcImageFile 原图片地址
     * @param x            截取时的x坐标
     * @param y            截取时的y坐标
     * @param desWidth     截取的宽度
     * @param desHeight    截取的高度
     */
    public static String imageCut(String srcImageFile, int x, int y, int desWidth,
                              int desHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
//            System.out.println("ImageUtil.imageCut"+srcImageFile + ImageUtil.SRC_JPG);
            BufferedImage bi = ImageIO.read(new File(srcImageFile + ImageUtil.SRC_JPG));
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth >= desWidth && srcHeight >= desHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(desWidth, desHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                //输出文件
                ImageIO.write(tag, "JPEG", new File(srcImageFile + ImageUtil.CUT_JPG));
                //剪切后的文件名
                return srcImageFile+ImageUtil.CUT_JPG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return  null;
    }
}
