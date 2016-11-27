package com.lachman.passbook.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @version 1.0
 * @author Alicja Lachman
 */
public class PassbookModelTest {

    final String ENCRYPTION_KEY = "123456789123456789123456789";
    PassbookModel instance;
    final String filePath = "src/test/example.txt";

    @Before
    public void preparePassbookModel() throws IOException {
        instance = new PassbookModel();
        instance.setFile(filePath);
        instance.getPasswordsFromFile();
    }

    @After
    public void deleteExampleTextFile() throws IOException {
        Files.delete(Paths.get(filePath));
    }

    /**
     * Test of getDecryptedPassword method, of class PassbookModel.
     */
    @Test
    public void testGetDecryptedPassword() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";
        Password createdPassword = instance.createPassword(domain, username, password, ENCRYPTION_KEY);
        String expResult = new StringBuilder().append("The password for domain: ")
                .append(domain)
                .append(" and username: ")
                .append(username)
                .append(" is: ")
                .append(password)
                .toString();
        //WHEN
        String result = instance.getDecryptedPassword(createdPassword, ENCRYPTION_KEY);

        //THEN
        assertEquals(expResult, result);

    }

    /**
     * Test of createPassword method, of class PassbookModel.
     */
    @Test
    public void testCreatePassword() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        //WHEN
        instance.createPassword(domain, username, password, ENCRYPTION_KEY);

        //THEN
        assertEquals(instance.checkIfPasswordForDomainExists(domain), true);

    }

    /**
     * Test of createPassword method, of class PassbookModel using wrong encryption key.
     */
    @Test(expected = InvalidKeyException.class)
    public void testCreatePasswordWithWrongEncryptionKey() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";
        String encryptionKey = "233";

        //WHEN
        instance.createPassword(domain, username, password, encryptionKey);

        //THEN 
        //Expected exception: InvalidKeyException
    }

    /**
     * Test of getPasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testGetPasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        //WHEN
        Password createdPassword = instance.createPassword(domain, username, password, ENCRYPTION_KEY);
        Password result = instance.getPasswordForDomain(domain);

        //THEN
        assertEquals(createdPassword, result);

    }

    /**
     * Test of getPasswordForDomain method, of class PassbookModel, with
     * unexisting domain name.
     */
    @Test
    public void testGetPasswordForUnexistingDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        //WHEN
        Password createdPassword = instance.createPassword(domain, username, password, ENCRYPTION_KEY);
        Password result = instance.getPasswordForDomain("otherDomain");

        //THEN
        assertNull(result);

    }

    /**
     * Test of listAllDomainNames method, of class PassbookModel.
     */
    @Test
    public void testListAllDomainNames() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";
        String domain2 = "domainName2";
        String username2 = "username2";
        String password2 = "password2";

        instance.createPassword(domain, username, password, ENCRYPTION_KEY);
        instance.createPassword(domain2, username2, password2, ENCRYPTION_KEY);

        //WHEN
        List<String> result = instance.listAllDomainNames();
        //THEN
        assertEquals(2, result.size());
    }

    /**
     * Test of checkIfPasswordForDomainExists method, of class PassbookModel.
     */
    @Test
    public void testCheckIfPasswordForDomainExists() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        //WHEN
        instance.createPassword(domain, username, password, ENCRYPTION_KEY);

        //THEN
        assertEquals(instance.checkIfPasswordForDomainExists(domain), true);
    }

    /**
     * Test of deletePasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testDeletePasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        instance.createPassword(domain, username, password, ENCRYPTION_KEY);

        //WHEN
        instance.deletePasswordForDomain(domain);

        //THEN
        assertEquals(instance.checkIfPasswordForDomainExists(domain), false);
    }

    /**
     * Test of changePasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testChangePasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String domain = "domainName";
        String username = "username";
        String password = "password";

        instance.createPassword(domain, username, password, ENCRYPTION_KEY);

        String newPassword = "password2";

        //WHEN
        instance.changePasswordForDomain(domain, newPassword, ENCRYPTION_KEY);
        Password changesPassword = instance.getPasswordForDomain(domain);
        String result = instance.getDecryptedPassword(changesPassword, ENCRYPTION_KEY);
        String expResult = new StringBuilder().append("The password for domain: ")
                .append(domain)
                .append(" and username: ")
                .append(username)
                .append(" is: ")
                .append(newPassword)
                .toString();

        //THEN
        assertEquals(expResult, result);
    }

}
