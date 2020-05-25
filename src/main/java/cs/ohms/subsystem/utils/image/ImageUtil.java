// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/1.
package cs.ohms.subsystem.utils.image;

import cs.ohms.subsystem.utils.FileUtil;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 简单图像操作工具
 * 1、去背景化
 * 2、缩放图像
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ImageUtil {
    // 图片类型png
    public final static String PNG = "png";
    // 图片类型jpg
    public final static String JPG = "jpg";
    // 图片类型git
    public final static String GIF = "GIF";
    // 水印位置左上角
    public final static int WM_LT = 1;
    // 水印位置右上角
    public final static int WM_RT = 2;
    // 水印位置左下角
    public final static int WM_LB = 3;
    // 水印位置右下角
    public final static int WM_RB = 4;
    // 水印位置中部
    public final static int WM_CT = 5;
    // 边框上
    public final static int BORDER_T = 0x1;
    // 边框右
    public final static int BORDER_R = 0x2;
    // 边框下
    public final static int BORDER_B = 0x4;
    // 边框左
    public final static int BORDER_L = 0x8;

    // BufferedImage操作原对象
    protected BufferedImage bufferedImage;

    /**
     * 构造器
     *
     * @param file 图像文件
     * @throws IOException 读取错误
     */
    public ImageUtil(String file) throws IOException {
        InputStream in = FileUtil.getInputStream(file);
        this.bufferedImage = ImageIO.read(in);
        in.close();
    }

    /**
     * 构造器
     *
     * @param bufferedImage BufferedImage
     */
    public ImageUtil(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    /**
     * 获取图像宽度
     *
     * @return 图像宽度
     */
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    /**
     * 获取图像高度
     *
     * @return 图像高度
     */
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    /**
     * 获取操作的BufferedImage对象
     *
     * @return BufferedImage对象
     */
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    /**
     * 绘制背景颜色
     *
     * @param color 颜色
     * @return this
     */
    public ImageUtil setBackground(Color color) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(color);
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics2D.dispose();
        return this;
    }

    /**
     * 按指定宽和高缩放图像
     *
     * @param width  宽度
     * @param height 高度
     * @return this
     * @throws ImageUtilException ImageUtilException
     */
    public ImageUtil zoom(int width, int height) throws ImageUtilException {
        BufferedImage to = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        if (!to.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null)) {
            throw new ImageUtilException("图片缩放失败");
        }
        bufferedImage = to;
        return this;
    }

    /**
     * 按指定倍率放大图像
     *
     * @param mul 倍率
     * @return this
     * @throws ImageUtilException ImageUtilException
     */
    public ImageUtil zoomIn(int mul) throws ImageUtilException {
        int width = bufferedImage.getWidth() * mul;
        int height = bufferedImage.getHeight() * mul;
        return zoom(width, height);
    }

    /**
     * 按指定倍率缩小图片
     *
     * @param mul 倍率
     * @return this
     * @throws ImageUtilException ImageUtilException
     */
    public ImageUtil zoomOut(int mul) throws ImageUtilException {
        if (0 == mul) {
            return null;
        }
        int width = bufferedImage.getWidth() / mul;
        int height = bufferedImage.getHeight() / mul;
        return zoom(width, height);
    }

    /**
     * 添加水印
     *
     * @param watermark 水印
     * @param position  添加的位置
     *                  <b>WM_LT：左上角 WM_RT：右上角 WM_LB：左下角 WM_RB：右下角 WM_CT：图像中部</b>
     * @return 添加后的结果|null
     * @throws ImageUtilException ImageUtilException
     */
    public ImageUtil addWatermark(@NotNull BufferedImage watermark, int position) throws ImageUtilException {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int ww = watermark.getWidth();
        int hw = watermark.getHeight();
        Color color = new Color(0, 0, 0, 0);
        switch (position) {
            case WM_LT:
                return this.addImage(watermark, 0, 0, color);
            case WM_RT:
                return this.addImage(watermark, w - ww, 0, color);
            case WM_LB:
                return this.addImage(watermark, 0, h - hw, color);
            case WM_RB:
                return this.addImage(watermark, w - ww, h - hw, color);
            case WM_CT:
                return this.addImage(watermark, (w - ww) / 2, (h - hw) / 2, color);
            default:
                throw new ImageUtilException("水印位置选择错误");
        }
    }

    /**
     * 删除指定的颜色
     *
     * @param color 带alpha的Color实例
     * @return this
     */
    public ImageUtil deleteColor(Color color) {
        // 透明色
        Color toColor = new Color(0, 0, 0, 0);
        return replace(color, toColor);
    }

    /**
     * 替换指定的颜色
     *
     * @param color1 需要替换的颜色
     * @param color2 目标颜色
     * @return this
     */
    public ImageUtil replace(@NotNull Color color1, @NotNull Color color2) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage to = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        WritableRaster raster = to.getRaster();
        int oa = color1.getAlpha();
        int or = color1.getRed();
        int og = color1.getGreen();
        int ob = color1.getBlue();
        int ta = color2.getAlpha();
        int tr = color2.getRed();
        int tg = color2.getGreen();
        int tb = color2.getBlue();
        int[] des = new int[4];
        while (--w > 0) {        /* 遍历所有像素点 */
            for (int th = 0; th < h; ++th) {
                int rgb = bufferedImage.getRGB(w, th);
                int a, r, g, b;
                a = 0xff & rgb >> 24;
                r = 0xff & rgb >> 16;
                g = 0xff & rgb >> 8;
                b = 0xff & rgb;
                if (r == or && g == og && b == ob) {
                    des[0] = tr;
                    des[1] = tg;
                    des[2] = tb;
                    des[3] = ta;
                } else {
                    des[0] = r;
                    des[1] = g;
                    des[2] = b;
                    des[3] = a;
                }
                raster.setPixel(w, th, des);
            }
        }
        return this;
    }

    /**
     * 在当前图像上绘制图像
     *
     * @param image 子图像
     * @param x     绘制开始点x
     * @param y     绘制开始点y
     * @param color 背景颜色
     * @return this
     * @throws ImageUtilException ImageUtilException
     */
    public ImageUtil addImage(BufferedImage image, int x, int y, Color color) throws ImageUtilException {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        if (!graphics2D.drawImage(image, x, y, color, null)) {
            throw new ImageUtilException("添加图像失败");
        }
        return this;
    }

    /**
     * 给当前对象添加边框
     *
     * @param size   边框大小
     * @param color  边框颜色
     * @param border 边框位置
     * @return this
     */
    public ImageUtil addBorder(int size, Color color, int border) {
        addBorder(bufferedImage.createGraphics(), bufferedImage.getWidth()
                , bufferedImage.getHeight(), size, color, border);
        return this;
    }

    /**
     * 给图片添加边框
     *
     * @param graphics2D 2D画板
     * @param width      图像宽度
     * @param height     图像高度
     * @param size       边框大小
     * @param color      边框颜色
     * @param border     位置
     *                   <p><b>BORDER_T：上 BORDER_R：右 BORDER_B：下 BORDER_L：左</b></p>
     *                   <p>全部：BORDER_T | BORDER_R | BORDER_B | BORDER_L</p>
     *                   <p>因为采用的是位来做标志，所以全部也可以直接使用0xf(1111)</p>
     */
    public void addBorder(@NotNull Graphics2D graphics2D, int width, int height, int size, Color color, int border) {
        Color old = graphics2D.getColor();
        graphics2D.setColor(color);
        if (BORDER_T == (BORDER_T & border)) {
            graphics2D.fillRect(0, 0, width, size);
        }
        if (BORDER_R == (BORDER_R & border)) {
            graphics2D.fillRect(width - size, 0, width, height);
        }
        if (BORDER_B == (BORDER_B & border)) {
            graphics2D.fillRect(0, height - size, width, height);
        }
        if (BORDER_L == (BORDER_L & border)) {
            graphics2D.fillRect(0, 0, size, height);
        }
    }

    /**
     * 把当前对象写入到指定文件
     *
     * @param file 目标文件
     * @param type 类型
     * @return true|false
     * @throws IOException 写入失败
     */
    public boolean writeToFile(File file, String type) throws IOException {
        return ImageIO.write(bufferedImage, type, file);
    }

    /**
     * 把当前对象写入到指定文件
     *
     * @param file 文件路径
     * @param type 类型
     * @return true|false
     * @throws IOException 写入失败
     */
    public boolean writeToFile(String file, String type) throws IOException {
        return ImageIO.write(bufferedImage, type, new File(file));
    }

    /**
     * 复制图像对象
     *
     * @return 复制的结果|null
     */
    public BufferedImage copy() {
        BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        Graphics2D graphics2D = newImage.createGraphics();
        if (graphics2D.drawImage(bufferedImage, 0, 0, null)) {
            graphics2D.dispose();
            return newImage;
        }
        return null;
    }

    /**
     * 克隆当前ImageUtil对象
     *
     * @return ImageUtil
     */
    @Override
    public ImageUtil clone() throws CloneNotSupportedException {
        ImageUtil clone = (ImageUtil) super.clone();
        clone.bufferedImage = this.copy();
        return clone;
    }
}
