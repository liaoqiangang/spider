package com.spider.utils;

/**
 * Created by liaoqiangang-pc on 2018/3/5.
 * 传入参数：一个字节数组 * 传出参数：字节数组的 MD5 结果字符串
 */
public class MD5 {
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // 用来将字节转换成十六进制表示的字符
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];
            // 每个字节用十六进制表示的话，使用两个字符，
            // 所以表示成十六进制需要 32 个字符
            int k = 0;
            // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {
                // 从第一个字节开始，将 MD5 的每一个字节
                // 转换成十六进制字符
                byte byte0 = tmp[i];
                // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                // 取字节中高 4 位的数字转换，
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 取字节中低 4 位的数字转换 }
            s = new String(str);
            // 将换后的结果转换为字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
