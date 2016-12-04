package com.lachman.passbook.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
 * @version 2.0
 * @author Alicja Lachman
 */
public class PassbookModelTest {

    final String ENCRYPTION_KEY = "123456789123456789123456789";
    final String exampleFilePath = "src/test/example.txt";
    final static String exampleFileToCreatePath = "src/test/exampleCreated.txt";
    String domainName = "domain";
    String userName = "username";
    char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
    PassbookModel instance;

    @Before
    public void preparePassbookModel() {
        instance = new PassbookModel();

    }

    @AfterClass
    public static void deleteExampleTextFile() throws IOException {
        Files.delete(Paths.get(exampleFileToCreatePath));
    }

    @Test
    public void testEmptyPassbookModel() throws Exception {
        assertEquals(0, instance.getRowCount());
    }

    /**
     * Test of getPasswordsFromFile method, of class PassbookModel.
     */
    @Test
    public void testGetPasswordsFromFile() throws Exception {
        //GIVEN
        File file = new File(exampleFilePath);

        //WHEN
        instance.getPasswordsFromFile(file);

        //THEN
        assertNotEquals(0, instance.getRowCount());
    }

    /**
     * Test of getDecryptedPasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testGetDecryptedPasswordForDomain() throws Exception {
        //GIVEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);

        //WHEN
        String result = instance.getDecryptedPasswordForDomain(domainName, ENCRYPTION_KEY);

        //THEN
        assertEquals(String.valueOf(password), result);

    }

    /**
     * Test of createPassword method, of class PassbookModel.
     */
    @Test
    public void testCreatePassword() throws Exception {
        //WHEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);

        //THEN
        assertEquals(true, instance.checkIfPasswordForDomainExists(domainName));
    }

    /**
     * Test of createPassword method, of class PassbookModel using wrong
     * encryption key.
     */
    @Test(expected = InvalidKeyException.class)
    public void testCreatePasswordWithWrongEncryptionKey() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        String encryptionKey = "233";

        //WHEN
        instance.createPassword(domainName, userName, password, encryptionKey);

        //THEN
        //InvalidKeyException
    }

    /**
     * Test of savePasswordsToFile method, of class PassbookModel.
     */
    @Test
    public void testSavePasswordsToFile() throws Exception {
        //GIVEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        File file = new File(exampleFileToCreatePath);

        //WHEN
        instance.savePasswordsToFile(file);
        instance.getPasswordsFromFile(file);

        //THEN
        assertEquals(1, instance.getRowCount());
    }

    /**
     * Test of getPasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testGetPasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {

        //WHEN
        Password createdPassword = instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        Password result = instance.getPasswordForDomain(domainName);

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

        //WHEN
        Password createdPassword = instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        Password result = instance.getPasswordForDomain("otherDomain");

        //THEN
        assertNull(result);

    }

    /**
     * Test of checkIfPasswordForDomainExists method, of class PassbookModel.
     */
    @Test
    public void testCheckIfPasswordForDomainExists() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {

        //WHEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);

        //THEN
        assertEquals(true, instance.checkIfPasswordForDomainExists(domainName));
    }

    /**
     * Test of deletePasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testDeletePasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {

        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);

        //WHEN
        instance.deletePasswordForDomain(domainName);

        //THEN
        assertEquals(false, instance.checkIfPasswordForDomainExists(domainName));
    }

    /**
     * Test of changePasswordForDomain method, of class PassbookModel.
     */
    @Test
    public void testChangePasswordForDomain() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //GIVEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        String newPassword = "password2";

        //WHEN
        instance.changePasswordForDomain(domainName, newPassword, ENCRYPTION_KEY);

        String result = instance.getDecryptedPasswordForDomain(domainName, ENCRYPTION_KEY);

        //THEN
        assertEquals(newPassword, result);
    }

    /**
     * Test of getRowCount method, of class PassbookModel.
     */
    @Test
    public void testGetRowCount() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //WHEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        int result = instance.getRowCount();

        //THEN
        assertEquals(1, result);
    }

    /**
     * Test of getColumnCount method, of class PassbookModel.
     */
    @Test
    public void testGetColumnCount() {
        //WHEN
        int result = instance.getColumnCount();

        //THEN
        assertEquals(3, result);
    }

    /**
     * Test of getValueAt method, of class PassbookModel.
     */
    @Test
    public void testGetValueAt() throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {
        //WHEN
        instance.createPassword(domainName, userName, password, ENCRYPTION_KEY);
        Object result = instance.getValueAt(0, 0);

        //THEN
        assertEquals(domainName, result);

    }

    /**
     * Test of getColumnName method, of class PassbookModel.
     */
    @Test
    public void testGetColumnName() {
        //WHEN
        String result = instance.getColumnName(0);

        //THEN
        assertEquals("Domain", result);

    }
}
