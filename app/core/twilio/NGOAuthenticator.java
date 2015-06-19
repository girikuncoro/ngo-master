package core.twilio;

import dbaccess.dbojbects.pojo.NgoAccount;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by nhosgur on 1/6/15.
 */
public class NGOAuthenticator {
    private final static int ITERATION_NUMBER = 1000;

   public static boolean authenticateByPassword(NgoAccount ngoAccount, String password) {
        try {

            String digest, salt;
            digest = ngoAccount.getDigest();
            salt = ngoAccount.getSalt();
            byte[] bDigest = base64ToByte(digest);
            byte[] bSalt = base64ToByte(salt);

            // Compute the new DIGEST
            byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);

            return Arrays.equals(proposedDigest, bDigest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateByDigestSalt(NgoAccount ngoAccount, String digest, String salt) {
        if (null == ngoAccount || StringUtils.isBlank(digest) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("None of the parameters can be null nor empty");
        }
        if (null == ngoAccount || StringUtils.isBlank(digest) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("None of the parameters can be null nor empty");
        }
        if (StringUtils.isBlank(ngoAccount.getDigest()) || StringUtils.isBlank(ngoAccount.getSalt())) {
            throw new IllegalStateException("Neither digest nor salt can be null");
        }

        return ngoAccount.getDigest().equalsIgnoreCase(digest) &&
                ngoAccount.getSalt().equalsIgnoreCase(salt);


    }

   public static boolean authenticate(String digest, String salt,
                                       String password) throws NoSuchAlgorithmException, IOException {

        byte[] bDigest = base64ToByte(digest);
        byte[] bSalt = base64ToByte(salt);

        // Compute the new DIGEST
        byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);

        return Arrays.equals(proposedDigest, bDigest);

    }

     public static boolean createDigestAndSaltForTheAccount(String password,
                                                           NgoAccount ngoAccount) {
        if (null == ngoAccount && StringUtils.isBlank(password))
            throw new IllegalArgumentException(
                    "Neither account nor password can be null");

        try {
            // Uses a secure Random not a simple Random
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 64 bits long
            byte[] bSalt = new byte[8];
            random.nextBytes(bSalt);
            // Digest computation
            byte[] bDigest = getHash(ITERATION_NUMBER, password, bSalt);
            String sDigest = byteToBase64(bDigest);
            String sSalt = byteToBase64(bSalt);
            ngoAccount.setDigest(sDigest);
            ngoAccount.setSalt(sSalt);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws NoSuchAlgorithmException,
            SQLException, IOException {
        NgoAccount ngoAccount = new NgoAccount();
        System.out.println(createDigestAndSaltForTheAccount("somepassword",
                ngoAccount));
    }

    public static byte[] getHash(int iterationNb, String password, byte[] salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

    public static byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    public static String byteToBase64(byte[] data) {
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }

}
