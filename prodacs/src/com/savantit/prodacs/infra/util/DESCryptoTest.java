/*
 * Created on Apr 9, 2005
 *
 * ClassName	:  	Check.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.infra.util;

/**
 * @author kduraisamy
 *
 */
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
public class DESCryptoTest {
    public static void main(String[] args) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            Key key = kg.generateKey();
            System.out.println("Key value :"+key);
            Cipher cipher = Cipher.getInstance("DES");
 
            byte[] data = "Hello World!".getBytes();
            System.out.println("Original data : " + new String(data));
 
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(data);
            System.out.println("Encrypted data: " + new String(result));
 
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] original = cipher.doFinal(result);
            System.out.println("Decrypted data: " + new String(original));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
 






/*
*$Log: DESCryptoTest.java,v $
*Revision 1.1  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*
*/