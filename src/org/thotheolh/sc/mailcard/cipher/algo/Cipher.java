/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo;

import org.thotheolh.sc.mailcard.KeyHandle;
import javacard.security.Key;

/**
 *
 * @author gerald
 */
public abstract class Cipher {

    public abstract void init(KeyHandle keyHandle, short opMode, short cipherMode, short padMode);

    public abstract void update(byte[][] inputData, short encFrom, short encLen, byte[] outputData);

    public abstract void doFinal(byte[][] inputData, short encFrom, short encLen, Object outputData);

    public abstract byte[] handleDataPadding(byte[] inputData, short padMode);

    public byte[] getKeyBytes(KeyHandle keyHandle) {
        return null;
    }

    public Key getKey(KeyHandle keyHandle) {
        return null;
    }

}
