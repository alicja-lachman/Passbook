/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lachman.passbook.view;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import util.ImageHelper;

/**
 *
 * @author Alicja Lachman
 */
public class NewPasswordDialog extends JDialog {

    private static final String JAVA_ICON = "java_icon.png";
    private NewPasswordInput result;

    public NewPasswordDialog(Frame parent) {
        super(parent);
        initView();
    }
    JTextField domainText;
    JTextField usernameText;
    JTextField encryptionKeyText;
    JPasswordField passwordText;
    JPasswordField passwordCheckText;

    private void initView() {
        setResizable(false);

        setLayout(new GridBagLayout());
       
        JLabel name = new JLabel("Add new password");

        JPanel domainPanel = new JPanel();
        domainPanel.add(new JLabel("Type domain name: "));
        domainText = new JTextField(15);
        //  JPasswordField passwordField = new JPasswordField(15);

        domainPanel.add(domainText);

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Type username: "));
        usernameText = new JTextField(15);
        usernamePanel.add(usernameText);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Type password: "));
        passwordText = new JPasswordField(15);
        passwordPanel.add(passwordText);

        JPanel passwordCheckPanel = new JPanel();
        passwordCheckPanel.add(new JLabel("Type password again: "));
        passwordCheckText = new JPasswordField(15);
        passwordCheckPanel.add(passwordCheckText);

        JPanel keyPanel = new JPanel();
        keyPanel.add(new JLabel("Type encryption key: "));
        encryptionKeyText = new JTextField(30);
        encryptionKeyText.setText("123456789123456789123456789");
        keyPanel.add(encryptionKeyText);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (!checkIfAllFieldsAreFilled()) {
                    JOptionPane.showMessageDialog(getRootPane(),
                            "You provided incomplete data, plase fix them before proceeding",
                            "Error", JOptionPane.ERROR_MESSAGE);

                } else {
                    result = new NewPasswordInput(domainText.getText(),
                            usernameText.getText(),
                            passwordText.getPassword(),
                            encryptionKeyText.getText());
                    dispose();
                }

            }
        });

        JPanel buttons = new JPanel();
        buttons.add(cancelButton);
        buttons.add(okButton);

        add(name);
        add(domainPanel);
        add(usernamePanel);
        add(passwordPanel);
        add(passwordCheckPanel);
        add(keyPanel);
        add(buttons);
        pack();
//        createLayout(name, cancelButton, okButton);

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        setTitle("Add new password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
    }

//     private void createLayout(JComponent... arg) {
//
//        Container pane = getContentPane();
//        GroupLayout gl = new GroupLayout(pane);
//        pane.setLayout(gl);
//
//        gl.setAutoCreateContainerGaps(true);
//        gl.setAutoCreateGaps(true);
//
//        gl.setHorizontalGroup(gl.createParallelGroup(CENTER)
//                .addComponent(arg[0])
//                .addComponent(arg[1])
//                .addComponent(arg[2])
//                .addGap(200)
//        );
//
//        gl.setVerticalGroup(gl.createSequentialGroup()
//                .addGap(30)
//                .addComponent(arg[0])
//                .addGap(20)
//                .addComponent(arg[1])
//                .addGap(20)
//                .addComponent(arg[2])
//                .addGap(30)
//        );
//
//        pack();
//    }
    private boolean checkIfAllFieldsAreFilled() {
        if (domainText.getText().isEmpty()) {
            return false;
        } else if (usernameText.getText().isEmpty()) {
            return false;
        } else if (encryptionKeyText.getText().isEmpty()) {
            return false;
        } else if (passwordText.getPassword().length == 0) {
            return false;
        } else if (passwordCheckText.getPassword().length == 0) {
            return false;
        } else if (!(Arrays.equals(passwordText.getPassword(), passwordCheckText.getPassword()))) {
            return false;
        }
        return true;
    }

    public NewPasswordInput getResult() {
        return result;
    }

    public class NewPasswordInput {

        private String domain;
        private String username;

        private char[] password;
        private String encryptionKey;

        public NewPasswordInput(String domain, String username, char[] password, String encryptionKey) {
            this.domain = domain;
            this.username = username;
            this.password = password;
            this.encryptionKey = encryptionKey;
        }

        public String getDomain() {
            return domain;
        }

        public String getUsername() {
            return username;
        }

        public char[] getPassword() {
            return password;
        }

        public String getEncryptionKey() {
            return encryptionKey;
        }

    }
}
