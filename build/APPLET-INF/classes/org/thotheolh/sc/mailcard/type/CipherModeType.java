/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.type;

/**
 *
 * @author gerald
 */
public class CipherModeType {

    public static short NULL_NOPAD = (short) 0;
    public static short AES_CBC_NOPAD = (short) 1;
    public static short AES_ECB_NOPAD = (short) 2;
    public static short DES_CBC_NOPAD = (short) 3;
    public static short DES_ECB_NOPAD = (short) 4;
    public static short SEED_CBC_NOPAD = (short) 5;
    public static short SEED_ECB_NOPAD = (short) 6;
    public static short TWOFISH_CBC_NOPAD = (short) 7;
    public static short TWOFISH_ECB_NOPAD = (short) 8;
    public static short SERPENT_CBC_NOPAD = (short) 9;
    public static short SERPENT_ECB_NOPAD = (short) 10;
    public static short RSA_PKCS1 = (short) 11;
    public static short RSA_PKCS1_OAEP = (short) 12;
}
