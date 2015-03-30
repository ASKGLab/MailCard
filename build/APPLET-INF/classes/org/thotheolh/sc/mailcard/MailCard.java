/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard;

import javacard.framework.*;
import javacard.security.AESKey;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;

/**
 *
 * @author gerald
 */
public class MailCard extends Applet {

    final static byte PIN_TRY_LIMIT = (byte) 0x08;
    final static byte MAX_PIN_SIZE = (byte) 0x08;
    final private OwnerPIN pin; //Main PIN
    final private OwnerPIN dPin; //DPIN
    final private RSAPrivateKey pgpPrivKey;
    final private RSAPublicKey pgpPubKey;
    final private RSAPublicKey mailServerPubKey;
    final private KeyPair pgpKeyPair;
    final private AESKey otpKey;
    final private AESKey otpSyncKey;
    final private byte[] BLANK_PIN_BYTES = new byte[]{(byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00};

    /**
     * Installs this applet.
     *
     * @param bArray the array containing installation parameters
     * @param bOffset the starting offset in bArray
     * @param bLength the length in bytes of the parameter data in bArray
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MailCard(bArray, bOffset, bLength);
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
     * Only used in desperate moments when crap happens !!!
     */
    private void die() {
        pgpPrivKey.clearKey();
        pgpPubKey.clearKey();
        mailServerPubKey.clearKey();
        otpKey.clearKey();
        otpSyncKey.clearKey();
        pin.update(this.BLANK_PIN_BYTES, MAX_PIN_SIZE, MAX_PIN_SIZE);
        dPin.update(this.BLANK_PIN_BYTES, MAX_PIN_SIZE, MAX_PIN_SIZE);
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
