/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo;

import javacard.security.CryptoException;
import org.thotheolh.sc.km.world.KeyHandle;
import org.thotheolh.sc.mailcard.cipher.algo.pad.PKCS7;
import org.thotheolh.sc.mailcard.type.CipherModeType;
import org.thotheolh.sc.mailcard.type.OperationType;
import org.thotheolh.sc.mailcard.type.SymmetricPadType;
import javacardx.crypto.Cipher;

/**
 *
 * @author gerald
 */
public class AES extends org.thotheolh.sc.mailcard.cipher.algo.Cipher {

    private Cipher cipher;
    private PKCS7 pkcs7 = new PKCS7();
    private short padMode;

    public AES() {
    }

    public void init(KeyHandle keyHandle, short opMode, short cipherMode, short padMode) {
        this.padMode = padMode;

        if (cipherMode == CipherModeType.AES_CBC_NOPAD && padMode == SymmetricPadType.NO_PAD) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
        } else if (cipherMode == CipherModeType.AES_ECB_NOPAD && padMode == SymmetricPadType.NO_PAD) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_ECB_NOPAD, false);
        } else if (cipherMode == CipherModeType.AES_CBC_NOPAD && padMode == SymmetricPadType.PKCS5) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_CBC_PKCS5, false);
        } else if (cipherMode == CipherModeType.AES_ECB_NOPAD && padMode == SymmetricPadType.PKCS5) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_ECB_PKCS5, false);
        } else if (cipherMode == CipherModeType.AES_CBC_NOPAD && padMode == SymmetricPadType.PKCS7) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
        } else if (cipherMode == CipherModeType.AES_ECB_NOPAD && padMode == SymmetricPadType.PKCS7) {
            cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_ECB_NOPAD, false);
        } else {
            cipher = null;
        }

        if (cipher != null) {
            if (opMode == OperationType.DO_ENCRYPT || opMode == OperationType.DO_ENCRYPT_SIGN) {
                cipher.init(getKey(keyHandle), Cipher.MODE_ENCRYPT);
            } else if (opMode == OperationType.DO_DECRYPT || opMode == OperationType.DO_DECRYPT_VERIFY) {
                cipher.init(getKey(keyHandle), Cipher.MODE_DECRYPT);
            }
        }
    }

    public void update(byte[][] inputData, short encFrom, short encLen, byte[] outputData) {
        cipher.update(handleDataPadding(inputData[0], padMode), encFrom, (short) inputData[0].length, outputData, (short) 0);
    }

    public void doFinal(byte[][] inputData, short encFrom, short encLen, Object outputData) {
        cipher.doFinal(handleDataPadding(inputData[0], padMode), encFrom, encLen, (byte[]) outputData, (short) 0);
    }

    public byte[] handleDataPadding(byte[] data, short padMode) {
        if (padMode == SymmetricPadType.PKCS7) {
            return pkcs7.handleData(data);
        }
        return null;
    }

}
