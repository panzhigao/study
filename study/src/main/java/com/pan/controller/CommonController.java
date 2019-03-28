package com.pan.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.KeyPair;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.constant.MyConstant;
import com.pan.util.RSAUtil;
import com.pan.util.TokenUtils;
import com.pan.util.VerifyCodeUtils;

/**
 * @author 作者
 * @version 创建时间：2018年6月28日 下午3:22:26
 * 类说明
 */
@Controller
@RequestMapping("api")
public class CommonController {
	/**
     * 生成验证码
     * @param response
     * @throws IOException
     * @ValidateCode.generateTextCode(验证码字符类型,验证码长度,需排除的特殊字符)
     * @ValidateCode.generateImageCode(文本验证码,图片宽度,图片高度,干扰线的条数,字符的高低位置是否随机,图片颜色,字体颜色,干扰线颜色)
     */
    @RequestMapping(value = "validateCode")
    public void validateCode(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        TokenUtils.setAttribute(MyConstant.VERCODE, verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = VerifyCodeUtils.generateImageCode(verifyCode, 70, 30, 5, true, Color.WHITE, Color.BLUE, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }
    
	/**
	 * 生成公钥和私钥
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/publicKey")
	@ResponseBody
	public String generatePublicKey() throws Exception{
		KeyPair keyPair = RSAUtil.getKeyPair();
		String privateKey = RSAUtil.getPrivateKey(keyPair);
		String publicKey = RSAUtil.getPublicKey(keyPair);
		//私钥放入session
		TokenUtils.setAttribute(MyConstant.PRIVATE_KEY, privateKey);
		return publicKey;
	}
}
