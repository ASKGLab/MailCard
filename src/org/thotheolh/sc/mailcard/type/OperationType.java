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
public class OperationType {

    public static short DO_ENCRYPT = (short) 1;
    public static short DO_ENCRYPT_SIGN = (short) 2;
    public static short DO_DECRYPT = (short) 3;
    public static short DO_DECRYPT_VERIFY = (short) 4;
    public static short DO_HASH = (short)5;
    public static short DO_SIGN = (short) 6;
    public static short DO_VERIFY = (short) 7;
}
