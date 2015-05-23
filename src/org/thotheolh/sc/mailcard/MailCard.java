/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard;

import javacard.framework.*;
import javacard.security.AESKey;
import javacard.security.CryptoException;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;
import javacard.security.MessageDigest;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;
import javacard.security.Signature;
import javacardx.crypto.Cipher;

/**
 *
 * @author gerald
 */
public class MailCard extends Applet {

    final static byte PIN_TRY_LIMIT = (byte) 0x08;
    final static byte MAX_PIN_SIZE = (byte) 0x08;
    final private OwnerPIN pin; //Main PIN
    final private OwnerPIN dPin; //DPIN
    private RSAPrivateKey pgpPrivKey;
    private RSAPublicKey pgpPubKey;
    final private RSAPublicKey mailServerPubKey;
    final private KeyPair pgpKeyPair;
    final private AESKey masterKey;
    final private AESKey otpKey;
    final private AESKey otpSyncKey;
    final private byte[] BLANK_PIN_BYTES = new byte[]{(byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00};
    private byte[] pop3Address;
    private byte[] pop3Port;
    private byte[] smtpAddress;
    private byte[] smtpPort;
    private byte[] mailPassword;
    private boolean isPasswordEncrypted = false;

    /**
     * Installs this applet.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        if (cardCheck()) {
            new MailCard(bArray, bOffset, bLength);
        } else {
            // Return error of installation due to failed card check.
        }
    }

    /**
     * Only this class's install method should create the applet object.
     */
    private MailCard(byte[] bArray, short bOffset, byte bLength) {
        pin = new OwnerPIN(PIN_TRY_LIMIT, MAX_PIN_SIZE);
        dPin = new OwnerPIN(PIN_TRY_LIMIT, MAX_PIN_SIZE);
        pin.update(bArray, bOffset, bLength);
        pgpPrivKey = (RSAPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, KeyBuilder.LENGTH_RSA_2048, true);
        pgpPubKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_2048, true);
        pgpKeyPair = new KeyPair(KeyPair.ALG_RSA, (short) pgpPubKey.getSize());
        mailServerPubKey = (RSAPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_2048, true);
        masterKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_256, true);
        otpKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_256, true);
        otpSyncKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_256, true);
        register();
    }

    /**
     * Card Login. Beware of the dangers of the ANGRY PRIME !
     *
     * @param pin
     * @param loginDataBuffer
     * @throws ISOException
     */
    public void login(OwnerPIN pin, byte[] pinData) throws ISOException {
        if (pin.check(pinData, (short) 0, MAX_PIN_SIZE)) {

        } else if (dPin.check(pinData, (short) 0, MAX_PIN_SIZE)) {
            die();
        }
    }

    /**
     * Card check to check environment before installing
     */
    private static boolean cardCheck() {
        boolean ciphersSupported = true;

        // Checks supporting cipher list for usability.
        try {
            Cipher.getInstance(javacardx.crypto.Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
            Cipher.getInstance(javacardx.crypto.Cipher.ALG_AES_BLOCK_128_ECB_NOPAD, false);
            Cipher.getInstance(javacardx.crypto.Cipher.ALG_RSA_NOPAD, false);
            Cipher.getInstance(javacardx.crypto.Cipher.ALG_RSA_PKCS1, false);
            Cipher.getInstance(javacardx.crypto.Cipher.ALG_RSA_PKCS1_OAEP, false);
            Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
            Signature.getInstance(Signature.ALG_RSA_SHA_256_PKCS1, false);
            Signature.getInstance(Signature.ALG_HMAC_SHA1, false);
            Signature.getInstance(Signature.ALG_HMAC_SHA_256, false);
            Signature.getInstance(Signature.ALG_HMAC_SHA_384, false);
            MessageDigest.getInstance(MessageDigest.ALG_SHA, false);
            MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
            MessageDigest.getInstance(MessageDigest.ALG_SHA_384, false);
        } catch (CryptoException e) {
            if (e.getReason() == CryptoException.NO_SUCH_ALGORITHM) {
                ciphersSupported = false;
            }
        }
        return ciphersSupported;
    }

    /**
     * Only used in desperate moments when crap happens !!!
     */
    private void die() {
        pgpPrivKey.clearKey();
        pgpPubKey.clearKey();
        masterKey.clearKey();
        otpKey.clearKey();
        otpSyncKey.clearKey();
        mailServerPubKey.clearKey();
        pin.update(this.BLANK_PIN_BYTES, MAX_PIN_SIZE, MAX_PIN_SIZE);
        dPin.update(this.BLANK_PIN_BYTES, MAX_PIN_SIZE, MAX_PIN_SIZE);
    }

    /**
     * Generates the PGP Key on the card
     */
    private void setupPGPKey() {
        pgpKeyPair.genKeyPair();
        pgpPrivKey = (RSAPrivateKey) pgpKeyPair.getPrivate();
        pgpPubKey = (RSAPublicKey) pgpKeyPair.getPublic();
    }

    private void setupPGPKey(byte[] openPGPCompatibleKeyFile) {

    }

    private void setupPGPKey(byte[] privExponent, byte[] privModulus, byte[] pubExponent, byte[] pubModulus) {

    }

    public void setupMailServer(byte[] pop3Address, byte[] pop3Port, byte[] smtpAddress, byte[] smtpPort, byte[] mailPassword, boolean pwdEnc) {
        this.pop3Address = pop3Address;
        this.pop3Port = pop3Port;
        this.smtpAddress = smtpAddress;
        this.smtpPort = smtpPort;
        this.mailPassword = mailPassword;
        this.isPasswordEncrypted = pwdEnc;
        encryptMailPassword();
    }

    /**
     * Uses masterKey to encrypt mailPassword to protect from physical offline
     * probe.
     */
    public void encryptMailPassword() {
    }

    /**
     * Uses masterKey to decrypt mailPassword during runtime.
     */
    public void decryptMailPassword() {
    }

    /**
     * Selects card and initializes activites. Checks if PIN is still usable
     * with enough retries before allowing other card activities.
     *
     * @return
     */
    public boolean select() {
        if (pin.getTriesRemaining() < PIN_TRY_LIMIT) {
            return true;
        }
        return false;
    }

    /**
     * Deselects card applet and cleans up all activity information. Resets PIN
     * tries.
     */
    public void deselect() {
        pin.reset();
        dPin.reset();
    }

    /**
     * Processes an incoming APDU.
     *
     * @see APDU
     * @param apdu the incoming APDU
     */
    public void process(APDU apdu) {
        //Insert your code here
    }
}
