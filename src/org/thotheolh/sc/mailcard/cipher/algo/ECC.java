/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo;

import org.thotheolh.sc.mailcard.KeyHandle;

/**
 *
 * @author gerald
 */
public class ECC extends org.thotheolh.sc.mailcard.cipher.algo.Cipher {

    public ECC() {
    }

    public void init(KeyHandle keyHandle, short opMode, short cipherMode, short padMode) {
    }

    public void update(byte[][] inputData, short encFrom, short encLen, byte[] outputData) {
    }

    public void doFinal(byte[][] inputData, short encFrom, short encLen, Object outputData) {
    }

    public byte[] handleDataPadding(byte[] inputData, short padMode) {
        return null;
    }
}
