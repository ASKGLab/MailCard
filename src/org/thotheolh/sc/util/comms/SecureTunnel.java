/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.util.comms;

import javacard.security.AESKey;
import javacard.security.KeyBuilder;

/**
 * Provides a Secure Tunnel protocol to allow APDUs to be tunneled in a form
 * that is encrypted and authenticated between host and card.
 * 
 * @author Thotheolh
 */
public class SecureTunnel {

    private byte[] buffer = null;
    private AESKey sessionKey = null;
    private static SecureTunnel instance = null;

    protected SecureTunnel() {

    }

    public static SecureTunnel getInstance() {
        if (instance == null) {
            instance = new SecureTunnel();
        }
        return instance;
    }

    public void setupTunnel() {
        sessionKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256, true);
    }

    public void closeTunnel() {
        sessionKey.clearKey();
    }

    public byte[] processIncoming(byte[] buffer) {
        this.buffer = buffer;
        return null;
    }

    public byte[] processOutgoing(byte[] buffer) {
        this.buffer = buffer;
        return null;
    }

}
