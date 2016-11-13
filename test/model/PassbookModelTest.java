/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Alicja Lachman
 */
public class PassbookModelTest {
    
    public PassbookModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDecryptedPassword method, of class PassbookModel.
     */
  @Ignore
    @Test
    public void testGetDecryptedPassword() throws Exception {
        System.out.println("getDecryptedPassword");
        Password password = null;
        String encryptionKey = "";
        PassbookModel instance = new PassbookModel();
        String expResult = "";
        String result = instance.getDecryptedPassword(password, encryptionKey);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPassword method, of class PassbookModel.
     */
      @Ignore
    @Test
    public void testCreatePassword() throws Exception {
        System.out.println("createPassword");
        String domain = "";
        String username = "";
        String password = "";
        String encryptionKey = "";
        PassbookModel instance = new PassbookModel();
        Password expResult = null;
        Password result = instance.createPassword(domain, username, password, encryptionKey);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePasswordToFile method, of class PassbookModel.
     */
    @Test
    public void testSavePasswordToFile() throws Exception {
  
        String file = "C:\\json.txt";

        Password password;
        
        PassbookModel instance = new PassbookModel();
        password = instance.createPassword("onet", "alucha", "haseuko", "123456789123456789123456789");
        instance.createPassword("onet2", "alucha2", "haseuko2", "123456789123456789123456789");
        instance.savePasswordToFile(file, password);
  
    }

    /**
     * Test of getPasswordFromFile method, of class PassbookModel.
     */
      @Ignore
    @Test
    public void testGetPasswordFromFile() throws Exception {
        System.out.println("getPasswordFromFile");
        String file = "";
        PassbookModel instance = new PassbookModel();
        Password expResult = null;
        Password result = instance.getPasswordFromFile(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
