package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    public Encrypt() {

    }

    public String encrypt(String text) {
        StringBuffer sb = new StringBuffer();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            byte[] digest = md.digest();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        return sb.toString().toUpperCase();
    }
}
