/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo;

import javacard.security.Signature;
import org.thotheolh.sc.mailcard.KeyHandle;
import org.thotheolh.sc.mailcard.type.CipherModeType;
import org.thotheolh.sc.mailcard.type.OperationType;
import javacardx.crypto.Cipher;

/**
 *
 * @author gerald
 */
public class RSA extends org.thotheolh.sc.mailcard.cipher.algo.Cipher {

    Cipher cipher;
    private short padMode;
    private short opMode;
    private Signature signer;

    public RSA() {
    }

    public void init(KeyHandle keyHandle, short opMode, short cipherMode, short padMode) {

        this.padMode = padMode;
        this.opMode = opMode;

        if (opMode == OperationType.DO_ENCRYPT) {
            if (cipherMode == CipherModeType.RSA_PKCS1) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_ENCRYPT);
            } else if (cipherMode == CipherModeType.RSA_PKCS1_OAEP) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1_OAEP, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_ENCRYPT);
            }
        } else if (opMode == OperationType.DO_ENCRYPT_SIGN) {
            if (cipherMode == CipherModeType.RSA_PKCS1) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_ENCRYPT);
                signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
                signer.init(getKey(keyHandle), Signature.MODE_SIGN);
            } else if (cipherMode == CipherModeType.RSA_PKCS1_OAEP) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1_OAEP, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_ENCRYPT);
                signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
                signer.init(getKey(keyHandle), Signature.MODE_SIGN);
            }
        } else if (opMode == OperationType.DO_DECRYPT) {
            if (cipherMode == CipherModeType.RSA_PKCS1) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_DECRYPT);
            } else if (cipherMode == CipherModeType.RSA_PKCS1_OAEP) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1_OAEP, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_DECRYPT);
            }
        } else if (opMode == OperationType.DO_DECRYPT_VERIFY) {
            if (cipherMode == CipherModeType.RSA_PKCS1) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_DECRYPT);
                signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
                signer.init(getKey(keyHandle), Signature.MODE_SIGN);
            } else if (cipherMode == CipherModeType.RSA_PKCS1_OAEP) {
                cipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1_OAEP, false);
                cipher.init(getKey(keyHandle), Cipher.MODE_DECRYPT);
                signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
                signer.init(getKey(keyHandle), Signature.MODE_SIGN);
            }
        } else if ((cipherMode == CipherModeType.RSA_PKCS1 || cipherMode == CipherModeType.RSA_PKCS1_OAEP)
                && opMode == OperationType.DO_SIGN) {
            signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
            signer.init(getKey(keyHandle), Signature.MODE_SIGN);
        } else if ((cipherMode == CipherModeType.RSA_PKCS1 || cipherMode == CipherModeType.RSA_PKCS1_OAEP)
                && opMode == OperationType.DO_VERIFY) {
            signer = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
            signer.init(getKey(keyHandle), Signature.MODE_SIGN);
        }
    }

    public void update(byte[] inputData, byte[] outputData) {
        signer.update(inputData, (short) 0, (short) inputData.length);
    }

    public void doFinal(byte[] inputData, byte[] outputData) {
        if (opMode == OperationType.DO_VERIFY) {
            // signer.verify();
        } else if (opMode == OperationType.DO_SIGN) {
            signer.sign(inputData, (short) 0, (short) inputData.length, outputData, (short) 0);
        } else if (opMode == OperationType.DO_DECRYPT) {

        } else if (opMode == OperationType.DO_DECRYPT_VERIFY) {

        } else if (opMode == OperationType.DO_ENCRYPT) {

        } else if (opMode == OperationType.DO_ENCRYPT_SIGN) {

        }
    }

    public byte[] handleDataPadding(byte[] inputData, short padMode) {
        return null;
    }
}
