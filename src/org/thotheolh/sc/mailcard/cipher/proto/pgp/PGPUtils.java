/*
 * PGP Utility Library.
 */
package org.thotheolh.sc.mailcard.cipher.proto.pgp;

import javacard.security.KeyPair;

/**
 *
 * @author Thotheolh
 */
public class PGPUtils {

    public PGPUtils() {
    }

    /**
     * Generates OpenPGP Certificate for keypair.
     * @param pgpKeyPair
     * @return 
     */
    public byte[] toPGPKeyPairCert(KeyPair pgpKeyPair) {
        return null;
    }
    
    /**
     * Wraps/Unwraps a PGP Private Key certificate with a password
     * @param cert
     * @param password
     * @param mode
     * @return 
     */
    public byte[] processPrivCertWrap(byte[] cert, byte[] password, short mode) {
        return null;
    }
}
