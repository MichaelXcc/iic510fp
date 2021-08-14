package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class util {
    public static String encryptAndDecrypt(String value, char secret) {
        // 将需要加密的内容转换为字节数组
        byte[] bt = value.getBytes();
        for (int i = 0; i < bt.length; i++) {
            // 通过异或运算进行加密
            bt[i] = (byte) (bt[i] ^ (int) secret);
        }
        // 将加密后的字符串保存到 newresult 变量中
        String newresult = new String(bt, 0, bt.length);
        return newresult;
    }

    public static String getUUID(){
        UUID uuid= UUID.randomUUID();
        return uuid.toString();
    }

    public static String createHashes(String passwd) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passwd.getBytes());

        byte byteData[] = md.digest();

        // convert the byte to hex format method 1
        StringBuffer sbHash = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sbHash.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hash password created => " + sbHash.toString());

        return sbHash.toString();
    }

}
