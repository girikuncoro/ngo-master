package core.twilio;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
/**
 * Created by nhosgur on 1/6/15.
 */
public class PasswordEncoder {
    private static final PasswordEncoder instance = new PasswordEncoder();
    private final static int ITERATION_COUNT = 5;

    private PasswordEncoder() {
    }

    public static synchronized PasswordEncoder getInstance() {
        return instance;
    }

    public synchronized String encode(String password)
            throws NoSuchAlgorithmException, IOException {

        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[20];
        random.nextBytes(salt);


        String encodedPassword = null;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);

        byte[] btPass = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < ITERATION_COUNT; i++) {
            digest.reset();
            btPass = digest.digest(btPass);
        }

        encodedPassword = byteToBase64(btPass);

        return encodedPassword;
    }

    private byte[] base64ToByte(String str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] returnbyteArray = decoder.decodeBuffer(str);
        return returnbyteArray;
    }

    private String byteToBase64(byte[] bt) {
        BASE64Encoder endecoder = new BASE64Encoder();
        String returnString = endecoder.encode(bt);
        return returnString;
    }

    public static void main(String...args){

    }

}
