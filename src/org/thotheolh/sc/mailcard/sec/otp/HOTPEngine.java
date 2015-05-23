/*
 * RFC4226 compliant HMAC-OTP Engine.
 * Can only handle 6 digits OTP numbers.
 */
package org.thotheolh.sc.mailcard.sec.otp;

import javacard.framework.Util;
import javacard.security.Key;
import javacard.security.Signature;
import org.thotheolh.sc.mailcard.util.Codec;

/**
 *
 * @author Thotheolh
 */
public class HOTPEngine {

    /**
     * Generates HOTP result bytes.
     * 
     * @param hotpKey
     * @param usageCtr
     * @return 
     */
    private byte[] generateOTP(Key hotpKey, int usageCtr) {
        byte[] result = null;
        byte[] text = new byte[8];

        // Converts counter to bytes
        for (int i = text.length - 1; i >= 0; i--) {
            text[i] = (byte) (usageCtr & 0xff);
            usageCtr >>= 8;
        }

        // Compute hmac hash
        byte[] hash = new byte[]{};
        Signature hmacSha1 = Signature.getInstance(Signature.ALG_HMAC_SHA1, true);
        hmacSha1.init(hotpKey, Signature.MODE_SIGN);
        hmacSha1.sign(text, (short) 0, (short) text.length, hash, (short) 0);

        // Convert to integer for maths
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24)
                | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8)
                | (hash[offset + 3] & 0xff);

        // 6 digit OTP code modulus math
        int otp = binary % 1000000;

        // Convert back to bytes
        result = Codec.intToAsciiBytes(otp);

        return result;
    }

    /**
     * Checking mechanism for HOTP. Only call this method.
     * 
     * @param hotpKey
     * @param usageCtr
     * @param otpBytes
     * @return 
     */
    public boolean checkOTP(Key hotpKey, short usageCtr, byte[] otpBytes) {
        byte[] currOtpBytes = generateOTP(hotpKey, usageCtr);
        byte a = Util.arrayCompare(otpBytes, (short) 0, currOtpBytes, (short) 0, (short) currOtpBytes.length);
        if (a == (byte) 0) {
            return true;
        }
        return false;
    }

}
