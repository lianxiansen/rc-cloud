package com.rc.cloud.app.operate.infrastructure.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

public class SaltUtil {

    public static class SaltDTO {
        private String salt;
        private String hashString;

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getHashString() {
            return hashString;
        }

        public void setHashString(String hashString) {
            this.hashString = hashString;
        }

    }

    public static String sha256Hex(byte[] bytes) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            //encodeStr = byte2Hex(messageDigest.digest());16
            encodeStr = Base64.encode(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

//    private static byte[] getutf8(String name) {
//        name = "中";
//        char[] chars = name.toCharArray();
//        //使用utf-8编码字符集
//        Charset charset = Charset.forName("utf-8");
//        CharBuffer charBuffer = CharBuffer.allocate(chars.length);
//        charBuffer.put(chars);
//        charBuffer.flip();
//        //字符编码为字节数组
//        ByteBuffer byteBuffer = charset.encode(charBuffer);
//        byte[] charToBytes = byteBuffer.array();
//        System.out.println("chars.length:" + chars.length + ";bytes.length:" + charToBytes.length);
//        //byte[] bytes = name.getBytes("utf-8");
//        byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
//         return bytes;
//    }


    /// <summary>
    /// 撒盐加密
    /// </summary>
    /// <param name="salt">salt,out 类型</param>
    /// <param name="password">password,string类型</param>
    /// <param name="hashString">hashString,out 类型</param>
    public static SaltDTO SaltHashEncryption(String password) {
        SaltDTO model = new SaltDTO();
        model.salt = UUID.randomUUID().toString(); //Guid
        byte[] passwordAndSaltBytes = (password + model.salt).getBytes(StandardCharsets.UTF_8);
        model.hashString = sha256Hex(passwordAndSaltBytes);

        return model;
    }

    /// <summary>
    /// 撒盐解密
    /// </summary>
    /// <param name="salt">salt,string 类型</param>
    /// <param name="password">password,string 类型</param>
    /// <param name="hashString">hashString,string类型</param>
    public static String SaltHashDecryption(String salt, String password) {
        byte[] passwordAndSaltBytes = (password + salt).getBytes(StandardCharsets.UTF_8);
        String hashString = sha256Hex(passwordAndSaltBytes);
        return hashString;
    }




    /// <summary>
    /// 根据中奖概率获取中奖区间,decimal类型
    /// </summary>
    /// <param name="s"></param>
    /// <returns></returns>
    public static BigDecimal[] GetRegion(BigDecimal[] s) {
        BigDecimal[] hei = new BigDecimal[s.length + 1];
        hei[0] = BigDecimal.valueOf(0);
        hei[1] = s[0];
        for (int i = 1; i < s.length; i++) {
            hei[i + 1] = hei[i].add(s[i]);
        }
        return hei;
    }

    //抽奖
    public static int GetDraw(List<BigDecimal> chance, int n, int index) {
        BigDecimal[] glv = new BigDecimal[chance.size()];//将概率进行放大
        BigDecimal allChance = BigDecimal.valueOf(0);

        for (int i = 0; i < glv.length; i++) {

            glv[i] = chance.get(i).multiply(BigDecimal.valueOf(n));
            allChance.add(chance.get(i));
        }
        Random rd = new Random();

        //BigDecimal dd = rd.Next(0, (100) * n);//修改为100%比率中奖
        //生成0-100*n内的随机数
        n = n * 100;
        BigDecimal dd = BigDecimal.valueOf(rd.nextInt(n - 0 + 1));


        BigDecimal[] region = GetRegion(glv);  //设置中奖区间
        for (int i = 0; i < region.length - 1; i++) {
            //if (region[i] <= dd && dd < region[i + 1])
            if (region[i].compareTo(dd) < 1 && dd.compareTo(region[i + 1]) == -1) {
                index = i;
                break;
            }
        }
        return index;
    }

    /// <summary>
    /// 手机号码加密
    /// </summary>
    /// <param name="_mobile"></param>
    /// <returns></returns>
    public static String GetMobilePsw(String _mobile) {
        String result = _mobile;
        if (StrUtil.isEmptyOrUndefined(_mobile)) {
            return "";
        }
        if (_mobile.trim().length() == 11) {
            result = _mobile.substring(0, 3) + "****" + _mobile.substring(7, 4);
        } else {
            result = _mobile.replace(_mobile.trim().substring(_mobile.length() - 4, 4), "****");
        }
        return result;
    }

    public static String shaEncode(String inStr)  {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = new byte[0];
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
