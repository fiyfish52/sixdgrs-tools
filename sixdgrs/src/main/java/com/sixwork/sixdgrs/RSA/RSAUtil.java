package com.sixwork.sixdgrs.RSA;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

	
public class RSAUtil {
	 
	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
 
	/** */
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
 
	/** */
	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";
 
	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";
 
	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
 
	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
 
	/** */
	/**
	 * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
	 */
	private static final int INITIALIZE_LENGTH = 1024;
	
	public static final String CHARSET = "UTF-8";
 
	/** */
	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(INITIALIZE_LENGTH);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
 
	/** */
	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return new String(Base64.encodeBase64(signature.sign()),CHARSET);
	}
 
	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decodeBase64(sign.getBytes()));
	}
 
	/** */
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
 
	/** */
	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
 
	/** */
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
 
	/** */
	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
 
	/** */
	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return new String(Base64.encodeBase64(key.getEncoded()),CHARSET);
	}
 
	/** */
	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return new String(Base64.encodeBase64(key.getEncoded()),CHARSET);
	}
 
	/**
	 * java端公钥加密
	 */
	public static String encryptedDataOnJava(String data, String PUBLICKEY) {
		try {
			data = new String(Base64.encodeBase64(encryptByPublicKey(data.getBytes(),
					PUBLICKEY)),CHARSET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
 
	/**
	 * java端私钥解密
	 */
	public static String decryptDataOnJava(String data, String PRIVATEKEY,String  charset) {
		String temp = "";
		try {
			byte[] rs = Base64.decodeBase64(data.getBytes());
			temp = new String(RSAUtil.decryptByPrivateKey(rs, PRIVATEKEY),charset);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
 
	public static void main(String[] args) throws  Exception{
		Map<String, Object> map = genKeyPair();
		String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKB9oOrOGnLLd57A5k/kh52P3hk5MGLLpPNZvAfr2rOPYX/cEUiyUOoP4FnsHNCKGECzuND0m/yBDk5H672MgjKbNTI1PTOGFksqyFVaS2mzPLatch9KteWiWsTfaWoXuI/iqfKXjl5GWOcFEMN/RVz5557DOS4pBHCodk4PZQp7AgMBAAECgYAn5PKyS4dxj7htgx/9Lh60AQZBmOC5PkwDngPKqEtWJUckRG0beA/7DvVDBRlokVtUAhjoAFYiH2aR+PjVxm66hcKY4pdShuwQcVpfGnPb1lerJ4ZznZ2E7B8t4/+6AucFQF55B1/Ic9EI7JQy4Z5qjafzuqDg4CulTN0qgJ122QJBAOvQfCcvYuxRGSyEyDps+btmS1j4ApZ/cPMpThCgj2/U4LLK2o+RHPo7f6lYDei3yiEdBDc4j1ZTZjcl/aaA5c0CQQCuOooxkJYn3U+TlUYNS5lvSflB8MCKBJoW++DkOYpcskrMb7SUFZVX2IBFd+GLJ9JMtQWlTvamHi8ooNPw5elnAkEA0auC6FhyzYniJzo8ye4hMiXnMsj5jBTuOdZtCc2pG5cc3vwMzwAI0tlaThjz7QqVV1H0cKECmY/332sRGB2dtQJACq0rqr3hda/qIHEVSdtIG7m8vH20TsDZcG8e6N1dzTBfxQdTWX+5Fxj9Q0SFXbGjAXBWWDrugl93JFY2ppxwIQJBAJoGNqGdqLV8V1St1E94UFEtEKYs2wzaJ0EQvQq/RSQUIzsvdPbnft8jjCUqxeznubiqH+OdkPqUn/5XvXtgxSU=";
				//getPrivateKey(map);
		String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgfaDqzhpyy3eewOZP5Iedj94ZOTBiy6TzWbwH69qzj2F/3BFIslDqD+BZ7BzQihhAs7jQ9Jv8gQ5OR+u9jIIymzUyNT0zhhZLKshVWktpszy2rXIfSrXlolrE32lqF7iP4qnyl45eRljnBRDDf0Vc+eeewzkuKQRwqHZOD2UKewIDAQAB";
				//getPublicKey(map);
		System.out.println("private1:"+privatekey);
		System.out.println("public1:"+publickey);
		String str = "{\"sql\": \"c2VsZWN0IHJlY2lkLGJpemZlZWRldGlkLGxvZ2lkLGN1c3RpZCxpbnZubyxzZXJpYWxubyxuYW1lLG9wY29kZSxwYXlmZWVzLHBheXdheSxmZWVjb2RlLHJmZWVjb2RlLGlmZWVjb2RlLG9wdGltZSBhcyBwYXl0aW1lLGZlZWtpbmQsc2NvcGV0eXBlLG1hcmtubyxiaXp0aW1lIGFzIGxvZ29wdGltZSxiaXpvcGVyaWQgYXMgb3BvcGVyLG9wZXJpZCBhcyBwYXlvcGVyLGJpemRlcHRpZCBhcyBvcGRlcHQsZGVwdGlkIGFzIHBheWRlcHQscGlkLHNhbGVzcGtnaWQsc2VydmlkLGRldm5hbWUsYml6YXJlYWlkIGFzIGFyZWFpZCxhcmVhaWQgYXMgdXNlcmFyZWFpZCxwYXRjaGlkLHBlcm1hcmssbG9naWNkZXZubyBhcyBjbWFjY3RubyxncmlkaWQsd2hsYWRkciBhcyBhZGRyZXNzIGZyb20gdmlld19iaXpfZGF0YV8yMDIwIHdoZXJlIDE9MSBBTkQgb3B0aW1lID49JzIwMjAtMDUtMTFUMDA6MDA6MDAnIEFORCBvcHRpbWUgPD0nMjAyMC0wNS0xMVQwOTowMDowMCcgIzk2I1BMQUNFIzk2Iw==\", \"alias\": \"ret1\", \"field\": \"recid\", \"queMethod\": \"QUERY_ESTEST\", \"pageid\": 1, \"pagesize\": 10, \"synMode\":\"N\"}";
		byte[] data1 = encryptByPublicKey(str.getBytes(),publickey);
		
		String message = new String(Base64.encodeBase64(data1), RSAUtil.CHARSET);
		System.out.println("密文："+message);
		String s = new String(Base64.encodeBase64(data1),CHARSET);
		
		
		
		System.out.println("解密密文：：：："+decryptDataOnJava(message,privatekey,CHARSET));
		
		Map<String, Object> map1 = genKeyPair();
		String privatekey1 ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKvkvlq6BwbzDTk1yzG7Gy5ti/0iUQCs13MgyW5eOyqYyrsqiZD2K6f77zRhsyrigmlCwfYHFmrYrgStr3Vpi8Apao9YeTyNEmIVmReQJSIecRfhsSDMUYBvELPYyUPMF9pdVj26HDn1xLvz/YaKgM7yNHhwfATZ5cuEpO9FRJo7AgMBAAECgYEAnB36jOgb7cHdprImxBoNi9NOqy75psVUQGnsyGKpFf0DU45EtvX9nGoCTO7XDQIAM53AD7VMNOpq0jD1BIxBsV0faBFMgsBIkj1IJr3/xwl8ME1/LIpHVvYvM/K3Z778UvwKGzGbtAdLPLh3DumdzVdPihZC2gTiH6CH+DEUSqECQQDjf1qD1oGEGsZ+7BpQgPAKjqPzxzTnx21dbawfiLgTIM6OCsIZUAMMgwHiOaEwjJYQUO0hkA6qPeQffC4V2R+pAkEAwW35sJUw6VmFQAtyOYO+tD5qkDLfqcoGJHCWmdUSeBBNI6pzZbmRNY70S/L8NCDnnaWAZLTJwjjt1/7rsO1pQwJANwCifL+O/saOeT9SOWaDIrpL73OZWfSAf5c1h0ZB4vYKmpMFTKNoqUy/VtfCHf/PhFBclMHEmepuziHy7ntZSQJABZ3a5Ty/ydbWnrB2ZaMks3kogP1pLzX1jbNTPC5Ktf/LLaDybt6/j62R/G6W0jCuaQ54NvRk82XRyjbVwzJxnwJAKUCp7+w5PrkZg9GDLns5wbkVZgWRkBpTl+Y6yM0BB2FlFDsZAcID44nRwUJbLHxkU6TuUg6Bxeg4Srx1VwqQnw==";
				//getPrivateKey(map1);
		String publickey1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCr5L5augcG8w05NcsxuxsubYv9IlEArNdzIMluXjsqmMq7KomQ9iun++80YbMq4oJpQsH2BxZq2K4Era91aYvAKWqPWHk8jRJiFZkXkCUiHnEX4bEgzFGAbxCz2MlDzBfaXVY9uhw59cS78/2GioDO8jR4cHwE2eXLhKTvRUSaOwIDAQAB";
				//getPublicKey(map1);
		System.out.println("sign private2:"+privatekey1);
		System.out.println("sign public2:"+publickey1);
		
		String ss = sign(data1,privatekey1);
		
		System.out.println(s);
		System.out.println(ss);
		s = decryptDataOnJava(s,privatekey,CHARSET);
		boolean issgin = verify(data1,publickey1,ss);
		System.out.println(issgin);
		System.out.println(s);
		
		String requestData = "{requestData:}";
	}
	
	public static String easpString(String requestData){
        if(requestData != null && !requestData.equals("")){
            String s = "{\"requestData\":";
            if(!requestData.startsWith(s)){
                throw new RuntimeException("参数【requestData】缺失异常！");
            }else{
                int closeLen = requestData.length()-1;
                int openLen = "{\"requestData\":".length();
                String substring = StringUtils.substring(requestData, openLen, closeLen);
                return substring;
            }
        }
        return "";
    }

}
