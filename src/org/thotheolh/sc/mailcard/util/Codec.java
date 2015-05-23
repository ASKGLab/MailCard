/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.util;

/**
 *
 * @author Thotheolh
 */
public class Codec {

    public static byte[] intToAsciiBytes(int input) {
        int ctr = 1;
        byte[] result = new byte[6];
        byte[] intAsciiBytes = new byte[]{0x30, 0x31, 0x32, 0x33, 0x34, 0x35,
            0x36, 0x37, 0x38, 0x39};

        while (input > 0) {
            result[result.length - ctr] = intAsciiBytes[input % 10];
            input = input / 10;
            ctr++;
        }
        return result;
    }

}
