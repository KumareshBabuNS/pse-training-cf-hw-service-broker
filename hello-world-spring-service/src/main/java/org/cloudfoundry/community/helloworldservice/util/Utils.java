package org.cloudfoundry.community.helloworldservice.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class Utils {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch(Exception e) {
            // TODO - really should use an alternative msg digest instead
        }
    }

    // return base 64 encoded hash of string param
    public static String stringToSecureHash(String s) {
        byte[] bytes = null;
        try {
            bytes = s.getBytes("UTF-8");
        }
        catch(UnsupportedEncodingException e) {
            bytes = s.getBytes();
        }
        String pwdHash =  new String(md.digest(bytes));
        return Base64.encodeBase64String(pwdHash.getBytes());
    }

}
