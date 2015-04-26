/*
 * A stripped down PGP engine for lightweight usage.
 * Cipher Suite:
 * - AES128-CBC
 * - AES256-CBC
 *
 * Cipher Padding:
 * - PKCS5
 * - PKCS7
 *
 * Hashing Schemes:
 * - SHA1
 * - SHA-256
 */
package org.thotheolh.sc.mailcard.cipher.proto.pgp;

import javacard.security.Key;

/**
 *
 * @author Thotheolh
 */
public class PGPEngine {
    
    public PGPEngine() {
        
    }
    
    public void handleHeader(Key asymmKey, byte[] header, short mode) {
        
    }
    
    public void handlePart(Key sessionKey, byte[] data, short mode) {
        
    }
    
}
