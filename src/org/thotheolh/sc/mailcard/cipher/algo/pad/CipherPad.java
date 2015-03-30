/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo.pad;

/**
 *
 * @author gerald
 */
public abstract class CipherPad {

    public abstract byte[] handleData(byte[] data);
}
