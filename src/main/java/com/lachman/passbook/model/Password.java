package com.lachman.passbook.model;

/**
 * Class representing Password object.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class Password {

    /**
     * name of the user of the password
     */
    private String username;
    /**
     * domain that the password was created for
     */
    private String domain;
    /**
     * password, that is stored encrypted
     */
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }
    
}
