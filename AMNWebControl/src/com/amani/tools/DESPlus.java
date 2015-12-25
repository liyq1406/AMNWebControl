package com.amani.tools;

import java.security.*;
import javax.crypto.*;

import org.springframework.stereotype.Repository;

@Repository("desPlus")
 public class DESPlus {
 private static String strDefaultKey = "national";

 private Cipher encryptCipher = null;

 private Cipher decryptCipher = null;


 public static String byteArr2HexStr(byte[] arrB) throws Exception {
  int iLen = arrB.length;
  // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
  StringBuffer sb = new StringBuffer(iLen * 2);
  for (int i = 0; i < iLen; i++) {
   int intTmp = arrB[i];
   // 把负数转换为正数
   while (intTmp < 0) {
    intTmp = intTmp + 256;
   }
   // 小于0F的数需要在前面补0
   if (intTmp < 16) {
    sb.append("0");
   }
   sb.append(Integer.toString(intTmp, 16));
  }
  return sb.toString();
 }


 public static byte[] hexStr2ByteArr(String strIn) throws Exception {
  byte[] arrB = strIn.getBytes();
  int iLen = arrB.length;

  // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
  byte[] arrOut = new byte[iLen / 2];
  for (int i = 0; i < iLen; i = i + 2) {
   String strTmp = new String(arrB, i, 2);
   arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
  }
  return arrOut;
 }





 public DESPlus() throws Exception {
  String strKey="AMN2013";
  Security.addProvider(new com.sun.crypto.provider.SunJCE());
  Key key = getKey(strKey.getBytes());

  encryptCipher = Cipher.getInstance("DES");
  encryptCipher.init(Cipher.ENCRYPT_MODE, key);

  decryptCipher = Cipher.getInstance("DES");
  decryptCipher.init(Cipher.DECRYPT_MODE, key);
 }
 
 public DESPlus(String strKey) throws Exception {
	  Security.addProvider(new com.sun.crypto.provider.SunJCE());
	  Key key = getKey(strKey.getBytes());

	  encryptCipher = Cipher.getInstance("DES");
	  encryptCipher.init(Cipher.ENCRYPT_MODE, key);

	  decryptCipher = Cipher.getInstance("DES");
	  decryptCipher.init(Cipher.DECRYPT_MODE, key);
	 }
 
 public static String unEncrypt(String str)  throws Exception{
 	String strKey="AMN2013";
 	DESPlus des = new DESPlus(strKey);//自定义密钥
 	return des.decrypt(str);
 }
 public static String toMD5(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[]byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		
			return buf.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

 
 public static String oldUncode(String strPassWord) {
		if (strPassWord == null || strPassWord.equals("")) {
			return "";
		}	
		int iszInlen = 0;
		int itailnum = 0;
		;
		int iFlag = 0;
		byte bcode1, bcode2, bcode3;
		char[] chOut = new char[128];
		char[] chInCopy = new char[256];
		char[] chIn = new char[256];
		System.arraycopy(strPassWord.toCharArray(), 0, chIn, 0, strPassWord
				.length());
		System.arraycopy(chIn, 0, chInCopy, 0, chIn.length);
		iszInlen = strPassWord.length();
		iszInlen = ((byte) (chInCopy[iszInlen - 1])) - 32;
		itailnum = 0;
		if (iszInlen % 3 != 0) {
			itailnum = 3 - iszInlen % 3;
		}
		iszInlen += itailnum;
		if (iszInlen < 0 || iszInlen % 3 != 0
				|| iszInlen / 3 * 4 != (strPassWord.length() - 1)) {
			return strPassWord;
		}
		for (iFlag = 0; iFlag < iszInlen / 3; iFlag++) {
			bcode1 = (byte) ((252 & ((byte) (chInCopy[iFlag * 4] - 32) << 2)) | (3 & ((byte) (chInCopy[iFlag * 4 + 1] - 32) >> 4)));
			bcode2 = (byte) ((240 & ((byte) (chInCopy[iFlag * 4 + 1] - 32) << 4)) | (15 & ((byte) (chInCopy[iFlag * 4 + 2] - 32) >> 2)));
			bcode3 = (byte) ((192 & ((byte) (chInCopy[iFlag * 4 + 2] - 32) << 6)) | (63 & ((byte) (chInCopy[iFlag * 4 + 3] - 32))));
			chOut[iFlag * 3] = (char) bcode1;
			chOut[iFlag * 3 + 1] = (char) bcode2;
			chOut[iFlag * 3 + 2] = (char) bcode3;
		}
		int ipostion = (iFlag - 1) * 3 + 3 - itailnum;
		for(int i=ipostion;i<128;i++)
			chOut[i] = 0;
		return new String(chOut).trim();
	}

	public static String oldEncode(String strValue) {
		if (strValue == null || strValue.equals("")) {
			return "";
		}
		int iszInlen, iFlag;
		byte bcode1, bcode2, bcode3, bcode4;
		char[] chOut = new char[128];
		char[] chInCopy = new char[256];
		char[] chIn = new char[256];
		System.arraycopy(strValue.toCharArray(), 0, chInCopy, 0, strValue
				.length());
		System.arraycopy(strValue.toCharArray(), 0, chIn, 0, strValue.length());
		iszInlen = strValue.length();
		if (iszInlen == 0)
			return "";
		for (iFlag = iszInlen; iFlag < iszInlen
				+ (iszInlen % 3 != 0 ? 3 - iszInlen % 3 : 0); ++iFlag)
			chInCopy[iFlag] = 'K';
		if (iszInlen % 3 != 0)
			chInCopy[iFlag] = 0;

		for (iFlag = 0; iFlag < iszInlen / 3 + (iszInlen % 3 == 0 ? 0 : 1); ++iFlag) {
			bcode1 = (byte) (((252 & (byte) (chInCopy[iFlag * 3])) >> 2) + 32);
			bcode2 = (byte) ((((3 & (byte) (chInCopy[iFlag * 3])) << 4) | ((240 & (byte) (chInCopy[iFlag * 3 + 1])) >> 4)) + 32);
			bcode3 = (byte) ((((15 & (byte) (chInCopy[iFlag * 3 + 1])) << 2) | ((192 & (byte) (chInCopy[iFlag * 3 + 2])) >> 6)) + 32);
			bcode4 = (byte) ((63 & (byte) (chInCopy[iFlag * 3 + 2])) + 32);
			chOut[iFlag * 4] = (char) bcode1;
			chOut[iFlag * 4 + 1] = (char) bcode2;
			chOut[iFlag * 4 + 2] = (char) bcode3;
			chOut[iFlag * 4 + 3] = (char) bcode4;
		}
		chOut[(iFlag - 1) * 4 + 4] = (char) (iszInlen + 32);
		chOut[(iFlag - 1) * 4 + 5] = 0;
		//处理加密字符串中的单引号，单引号造成SQL语句错误 added by wjg 2009/04/30
		char[] result = new char[256];
		int j=0;
		for(int i=0;i<chOut.length;i++)
		{
			result[j]=chOut[i];
			if(chOut[i]=='\'')
			{	
				j++;
				result[j]='\'';
			}
			j++;
		}
		return new String(result).trim();
		//return new String(chOut).trim();
	}


 public byte[] encrypt(byte[] arrB) throws Exception {
  return encryptCipher.doFinal(arrB);
 }


 public String encrypt(String strIn) throws Exception {
  return byteArr2HexStr(encrypt(strIn.getBytes()));
 }


 public byte[] decrypt(byte[] arrB) throws Exception {
  return decryptCipher.doFinal(arrB);
 }


 public String decrypt(String strIn) throws Exception {
  return new String(decrypt(hexStr2ByteArr(strIn)));
 }

 private Key getKey(byte[] arrBTmp) throws Exception {
  // 创建一个空的8位字节数组（默认值为0）
  byte[] arrB = new byte[8];

  // 将原始字节数组转换为8位
  for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
   arrB[i] = arrBTmp[i];
  }

  // 生成密钥
  Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

  return key;
 }
 
 public static void main(String[] args) {
	
	try {
		DESPlus desPlus=new DESPlus();
		System.out.println(desPlus.decrypt("510dd8133362e0c9"));
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

