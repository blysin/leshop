package com.blysin.leshop.common.controller;

import com.blysin.leshop.common.constant.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Blysin
 * @since 2017/10/19
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/randomCaptcha")
    private void randomCode(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        logger.debug("验证码");
        // 验证码图片的宽度。
        int width = 100;
        // 验证码图片的高度。
        int height = 40;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();

        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 21);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        // g.setColor(Color.GRAY);
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();

        // 设置默认生成4个验证码
        int length = 5;
        // 设置备选验证码:包括"a-z"和数字"0-9"
        //String base = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String base = "0123456789";

        int size = base.length();

        // 随机产生4位数字的验证码。
        for (int i = 0; i < length; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);

            // 用随机产生的颜色将验证码绘制到图像中。
            // 生成随机颜色(因为是做前景，所以偏深)
            // g.setColor(getRandColor(1, 100));

            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

            g.drawString(strRand, 18 * i + 6, 28);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        session.setAttribute(CommonConstants.SESSION_KEY_VERIFY_CODE, randomCode.toString());

        // 图象生效
        g.dispose();

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        try(ServletOutputStream sos = response.getOutputStream();) {
            ImageIO.write(buffImg, "jpeg", sos);
            sos.flush();
        } catch (IOException e) {
            logger.error("验证码创建错误："+e.getMessage());
        }

    }

    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
