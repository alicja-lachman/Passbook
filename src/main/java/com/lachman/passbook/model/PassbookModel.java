package com.lachman.passbook.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lachman.passbook.exception.EmptyPasswordFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DESedeKeySpec;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.codec.binary.Base64;

/**
 * Model class of the application It allows ciphering and deciphering password
 * data to and from files. It uses the DESede encryption algorithm
 *
 * @version 2.0
 * @author Alicja Lachman
 */
public class PassbookModel extends AbstractTableModel {

    /**
     * field representing coding format used in application
     */
    private final String CODING_FORMAT = "UTF8";
    /**
     * field representing encryption scheme of DESede algorithm
     */
    private final String DESEDE_ENCRYPTION_SCHEME = "DESede";

    /**
     * list of stored passwords
     */
    private List<Password> passwordList = new ArrayList();
    /**
     * path to file where all passwords are stored
     */
    private String file;

    private String[] columns = new String[]{"Domain", "Username", "Password"};

    public PassbookModel() {
        super();
        passwordList.add(new Password());
        passwordList.add(new Password());

    }

    /**
     * Method reads passwords from file if exists. If not, a new file is
     * created.
     *
     * @throws IOException
     */
    public void getPasswordsFromFile() throws IOException {

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();

            List<Password> list = gson.fromJson(reader, new TypeToken<List<Password>>() {
            }.getType());

            if (list != null) {
                passwordList.addAll(list);
            }
        } catch (IOException e) {
            File fileToCreate = new File(file);
            fileToCreate.createNewFile();
        }
    }

    /**
     * Method setting the path to file which stores passwords.
     *
     * @param file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Method returning decrypted password.
     *
     * @param password password to be decrypted
     * @param encryptionKey key used for encryption
     * @return string containing all data about password entry
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     */
    public String getDecryptedPassword(Password password, String encryptionKey)
            throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException {
        return new StringBuilder()
                .append("The password for domain: ")
                .append(password.getDomain())
                .append(" and username: ")
                .append(password.getUsername())
                .append(" is: ")
                .append(decrypt(password.getPassword(), encryptionKey))
                .toString();
    }

    /**
     * Method creating instance of Password, based on provided data. Password
     * field in encrypted using the encryption key and saved in file.
     *
     * @param domain
     * @param username
     * @param password
     * @param encryptionKey
     * @return created password
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws IOException
     */
    public Password createPassword(String domain, String username, String password, String encryptionKey)
            throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {

        Password createdPassword = new Password();
        createdPassword.setDomain(domain);
        createdPassword.setUsername(username);
        createdPassword.setPassword(encrypt(password, encryptionKey));

        passwordList.add(createdPassword);
        fireTableDataChanged();
        // savePasswordsToFile();
        return createdPassword;
    }

    /**
     * Method saving passwordList to file.
     *
     * @throws IOException
     */
    private void savePasswordsToFile() throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(passwordList, writer);
        }

    }

    /**
     * Method returning Password for given domain name.
     *
     * @param domainName
     * @return
     */
    public Password getPasswordForDomain(String domainName) {
        Password foundPassword = null;
        for (Password password : passwordList) {
            if (password.getDomain().equals(domainName)) {
                foundPassword = password;
            }
        }
        return foundPassword;
    }

    /**
     * Method returning all domain names for which passwords are stored.
     *
     * @return list of domain names
     */
    public List<String> listAllDomainNames() {
        List<String> domainNames = new ArrayList();
        passwordList.forEach(password -> domainNames.add(password.getDomain()));
        return domainNames;
    }

    /**
     * Method checking if password for given domain name already exists in
     * application.
     *
     * @param domainName
     * @return does password exists
     */
    public boolean checkIfPasswordForDomainExists(String domainName) {
        for (Password password : passwordList) {
            if (password.getDomain().equals(domainName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method reads a given file and creates instance of Password class, based
     * on the data read from file.
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchElementException
     * @throws EmptyPasswordFileException
     * @deprecated from old implementation
     */
    @Deprecated
    public Password getPasswordFromFile(String file) throws FileNotFoundException,
            IOException, NoSuchElementException, EmptyPasswordFileException {
        Password password = new Password();
        BufferedReader br = new BufferedReader(new FileReader(file));
        if (br.readLine() == null) {
            throw new EmptyPasswordFileException();
        }
        Scanner inputFile = new Scanner(new File(file));

        password.setDomain(inputFile.nextLine());
        password.setUsername(inputFile.nextLine());
        password.setPassword(inputFile.nextLine());

        return password;
    }

    /**
     * Method encrypting given string using the DESede algorithm and the given
     * encryption key.
     *
     * @param unencryptedString string to be encrypted
     * @param encryptionKey key used for encryption
     * @return encrypted string
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     */
    private String encrypt(String unencryptedString, String encryptionKey)
            throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException {

        byte[] arrayBytes = encryptionKey.getBytes(CODING_FORMAT);
        KeySpec keySpec = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
        SecretKey key = secretKeyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedText = cipher.doFinal(unencryptedString.getBytes(CODING_FORMAT));
        return new String(Base64.encodeBase64(encryptedText));

    }

    /**
     * Method decrypting given string using the DESede algorithm and the given
     * encryption key.
     *
     * @param encryptedString string to be decrypted
     * @param encryptionKey key used for encryption
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     */
    private String decrypt(String encryptedString, String encryptionKey)
            throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException {

        byte[] arrayBytes = encryptionKey.getBytes(CODING_FORMAT);
        KeySpec keySpec = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
        SecretKey key = secretKeyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = cipher.doFinal(Base64.decodeBase64(encryptedString));
        return new String(decryptedText);
    }

    /**
     * Method deletes password for given domain name if exists.
     *
     * @param domainName
     * @throws IOException
     */
    public void deletePasswordForDomain(String domainName) throws IOException {
        Password passwordToDelete = null;
        for (Password password : passwordList) {
            if (password.getDomain().equals(domainName)) {
                passwordToDelete = password;
            }
        }
        if (passwordToDelete != null) {
            passwordList.remove(passwordToDelete);
        }
        savePasswordsToFile();
    }

    /**
     * Method changing password for already existing password.
     *
     * @param domainName
     * @param newPassword
     * @param encryptionKey
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws IOException
     */
    public void changePasswordForDomain(String domainName, String newPassword, String encryptionKey)
            throws BadPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException, NoSuchPaddingException, IOException {

        for (Password password : passwordList) {
            if (password.getDomain().equals(domainName)) {
                password.setPassword(encrypt(newPassword, encryptionKey));
            }
        }
        savePasswordsToFile();
    }

    @Override
    public int getRowCount() {
        return passwordList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Password password = passwordList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return password.getDomain();
            case 1:
                return password.getUsername();
            case 2:
                return password.getPassword();
            // to complete here...
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    public void addRow() {
        Password passwordToAdd = new Password();
        passwordList.add(passwordToAdd);
        fireTableDataChanged();

    }

}
