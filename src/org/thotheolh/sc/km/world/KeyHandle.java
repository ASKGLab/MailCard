/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.km.world;

/**
 *
 * @author gerald
 */
public class KeyHandle {

    private byte[] keyName = new byte[128];
    private short keyType;
    private byte[] keyHash;
    private short[] keyArrayPos;

    public KeyHandle() {
    }

    public KeyHandle(byte[] keyName, short keyType, byte[] keyHash, short[] keyArrayPos) {
        this.setKeyName(keyName);
        this.setKeyType(keyType);
        this.setKeyHash(keyHash);
        this.setKeyArrayPos(keyArrayPos);
    }

    /**
     * @return the keyName
     */
    public byte[] getKeyName() {
        return keyName;
    }

    /**
     * @param keyName the keyName to set
     */
    public void setKeyName(byte[] keyName) {
        this.keyName = keyName;
    }

    /**
     * @return the keyType
     */
    public short getKeyType() {
        return keyType;
    }

    /**
     * @param keyType the keyType to set
     */
    public void setKeyType(short keyType) {
        this.keyType = keyType;
    }

    /**
     * @return the keyHash
     */
    public byte[] getKeyHash() {
        return keyHash;
    }

    /**
     * @param keyHash the keyHash to set
     */
    public void setKeyHash(byte[] keyHash) {
        this.keyHash = keyHash;
    }

    /**
     * @return the keyArrayPos
     */
    public short[] getKeyArrayPos() {
        return keyArrayPos;
    }

    /**
     * @param keyArrayPos the keyArrayPos to set
     */
    public void setKeyArrayPos(short[] keyArrayPos) {
        this.keyArrayPos = keyArrayPos;
    }
}
