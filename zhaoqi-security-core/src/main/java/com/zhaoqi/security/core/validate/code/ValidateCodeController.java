package com.zhaoqi.security.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.zhaoqi.security.core.properties.SecurityProperties;

@RestController
public class ValidateCodeController {

	//操作session
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	static final String SESSIOON_KEY = "SESSIOON_KEY_IMAGE_CODE";
	
	@Autowired
	private SecurityProperties securityProperties;  
			
	@GetMapping("/code/image")
	private void createCode(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		ImageCode imageCode = createImageCode(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSIOON_KEY, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	
	}

	

	/**
     * 生成验证码
     * 
     * @return
     * @throws IOException
     */
    public ImageCode createImageCode(ServletWebRequest request) throws IOException {
        // 在内存中创建图象
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"wedth", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height", securityProperties.getCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(230, 255));
        g.fillRect(0, 0, 100, 25);
        // 设定字体
        g.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.ITALIC, 18));
        // 产生0条干扰线，
        g.drawLine(0, 0, 0, 0);
        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(getRandColor(100, 150));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 15 * i + 6, 16);
        }
        for(int i=0;i<(random.nextInt(5)+5);i++){  
                g.setColor(new Color(random.nextInt(255)+1,random.nextInt(255)+1,random.nextInt(255)+1));  
                g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
        }   

        // 图象生效
        g.dispose();
        
        g.dispose();
        
		return new ImageCode(image,sRand,60);
    }
    /**
     * 给定范围获得随机颜色
     * 
     * @param fc
     * @param bc
     * @return
     */
    Color getRandColor(int fc, int bc) {
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
