/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thotheolh.sc.mailcard.cipher.algo.mode;

/**
 *
 * @author Thotheolh
 */
public abstract class Mode {
    
    public abstract void processBlock(byte[] block, byte[] params);
    
}
