package com.pan.util;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;

import org.junit.Test;

public class RSAUtil {

	// 生成秘钥对
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

	// 获取公钥(Base64编码)
	public static String getPublicKey(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 获取私钥(Base64编码)
	public static String getPrivateKey(KeyPair keyPair) {
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return byte2Base64(bytes);
	}

	// 将Base64编码后的公钥转换成PublicKey对象
	public static PublicKey string2PublicKey(String pubStr) throws Exception {
		byte[] keyBytes = base642Byte(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// 将Base64编码后的私钥转换成PrivateKey对象
	public static PrivateKey string2PrivateKey(String priStr) throws Exception {
		byte[] keyBytes = base642Byte(priStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	// 公钥加密
	public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 私钥解密
	public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

	// 字节数组转Base64编码
	public static String byte2Base64(byte[] bytes) {
		Encoder encoder2 = Base64.getEncoder();
		return encoder2.encodeToString(bytes);
	}

	// Base64编码转字节数组
	public static byte[] base642Byte(String base64Key) throws IOException {
		Decoder decoder = Base64.getDecoder();
		return decoder.decode(base64Key);
	}
	
	/**
	 * 解密
	 * @param secret 加密内容base64后的字符串
	 * @param privateKeyStr base64后私钥
	 * @return
	 * @throws Exception
	 */
	public static String decodeByPrivateKey(String secret, String privateKeyStr) throws Exception {
		PrivateKey privateKey = string2PrivateKey(privateKeyStr);
		// 加密后的内容Base64解码
		byte[] base642Byte = RSAUtil.base642Byte(secret);
		// 用私钥解密
		byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
		return new String(privateDecrypt);
	}

	@Test
	public void test1() throws Exception {
		String content = "hsoc3fzEb33X+cRBGC3VDjHZ6peQgELcRYWqJm2JCQd2uyx0gnEheTxSG89PT85g79zhgQpDT0pKTlp1hTOCOqLsuxU8k95Eb9C89MUKjwJzVyT4Xe30rqMY9HNQHxM3WABcJ32lx2yilALQRtXvIHEXQIh72gu4OMgqxzIR2yhDGBCyE07q+EenBQLo1bErAuShxOnYdQeTIHpESWvJk4eCmq6VCo3unlm+5hFz40C+VvgLUJFJY3V9BIdufGiBtn7rb1BWDn+bhtprIaHMcbqF4SZYP0iRoxwh9ZYJA0Rxgu/cgHCk3BAAQxOeoqqLjadLQ0AC9JyxcBfO3qAMlA==";
		String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFzax3UC4kfdQs1cXgDtI1BJdR28PDsRmQwqBhwMncgDsXqZMFUNJjW+5PYw20BhP79Dgpw+q1l0OsUBGBt7oVIK0nZX8tWWKpxO/9p0mYdrgvtmyxWn9daYwrRCbPxlwKk+tCtNux9WLAgLRcVlHFgdstVsYxQFP2j+Bseu6GD6u9fg4iqVa2bvx59MNXQ9QTqbdjbWI6ZxB//bb6TqeJkw+VPCV82CN/Zqt94zXWpVbbXJHPpITIkQd/Xa59xTnIqsU1BtaAtcQ2/flEKxHfD9/fGrLrkLviWzGWPN53gnqlBP/RAhmyda00AhbkZM2iy4V4djcHeebVieJ5N/SPAgMBAAECggEBAJTP5HWq+9v2Ul8cwQ6M9/ZtKs6p8IDURzCSTAje+r8KNeraZ8JzCKU5r3NISTHr9LL0DCDXxUeysnX7kwAtUjC88agXAvQCMGkawZbqeULb5jv3GZTgSbvkQVD3p99fEzXeDPuVh0c+HnpHGenyAvj9dgCJLPg2tjRkD+ptHe/6nF963aV/RDVI/bSHqX6+tMJ3DZ7rrdl2Q4npKheh0cVKdtgHLrZVBhRW/EupwgCiOuxIb6g83DcGt0FmIEsIwUn5/qQiswnVQ/2RBa3+0CCs7EXLjjZb2+zgb8B5B8SkE/MAVQIu0evfnLdAJqn+KgOT439gkrwNvUdcjT1uH7ECgYEA9QKw8TbwfxmBA47/AbG7k1qlsMxCtrAaUDk9L8I1miDOCKXPFtIqz26i2BzsW6tXIlMsxO9oepuQR8++HODecTeaIIp8LgGzQ1CfGscjqdZAlLNrpvhfU/CwFg42LzLDa/sn+7t02rH3FBB2UHXw8c6dl4T79pPhzTTuBs96JckCgYEAzqzug3gvsUwPe49+J5ZsBosmKJwXraXEC+DwuM+Egekeb4IazeRGctUYnvXeycqAXntoLAjW/hgdfGkUm2zeVDTQcasB1UvuzNYVKjn8LNIC5MmlZktQbC6ks8wksbM/NRci5be658+3bGdgkPowu/nn/umbW0FmIlWJPbJP05cCgYBnqFbSBstjCphBuCr9PYO9iPlWvmRvRDwturm/Tew9pClnETd3UT2ZexZcVohNFEWOGSB0xVEvpkc5ipPFRgH2Zm7H269EC6YbEa3ut1fIUAQMpYgjISu7qLXm7uZPu6tgMOcuopWCvQ5qp7hWvD9PHnogE7xCCzjaB824hekIuQKBgQC8gTBeyz08yfIJMdzPwLwwz7PzMEPMhzgj2XvWJLhoQTcYX6ORDVdQH5sASaN1i/S/uHFQUJO/WEF0mudHx+mVBkLkmiPm5wYc4FqXlCG0w6wN/vmULrdgpL5mm5kQVzwccUjMG2MnUearwjJqqTfQC+UVNP8VIbZFNJ0jn8J9awKBgDdLJxly5ej8bRObhdMJE1YrdlrgGt5jw8kj603W4FUEQ3VgBdQVxNBLsne6k+0cg4Kk11BrbX3iUojxidyCQmbtcPkXP3J5A5TRX9x5qTZRXQ7Qdzm8wq8Tj/m9IWw7wJJXBh8x7q4QzMX3XMZLWY3xJHsNNGfgnUGjHHdxBOf2";
		PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
		// 加密后的内容Base64解码
		byte[] base642Byte = RSAUtil.base642Byte(content);
		// 用私钥解密
		byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
		System.out.println(new String(privateDecrypt));
	}

	public static void main(String[] args) {
		try {
			// ===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
			// 生成RSA公钥和私钥，并Base64编码
			KeyPair keyPair = RSAUtil.getKeyPair();
			String publicKeyStr = RSAUtil.getPublicKey(keyPair);
			String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
			System.out.println("RSA公钥Base64编码:" + publicKeyStr);
			System.out.println("RSA私钥Base64编码:" + privateKeyStr);

			// =================客户端=================
			// hello, i am infi, good night!加密
			String message = "hello, i am infi, good night!";
			// 将Base64编码后的公钥转换成PublicKey对象
			PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
			// 用公钥加密
			byte[] publicEncrypt = RSAUtil.publicEncrypt(message.getBytes(), publicKey);
			// 加密后的内容Base64编码
			String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
			System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);

			// ############## 网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容
			// #################

			// ===================服务端================
			// 将Base64编码后的私钥转换成PrivateKey对象
			PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
			// 加密后的内容Base64解码
			byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
			// 用私钥解密
			byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
			// 解密后的明文
			System.out.println("解密后的明文: " + new String(privateDecrypt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
