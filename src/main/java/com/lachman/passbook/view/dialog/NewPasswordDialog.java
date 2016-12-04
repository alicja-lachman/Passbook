package com.lachman.passbook.view.dialog;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import com.lachman.passbook.util.ErrorMessageHelper;
import com.lachman.passbook.util.ImageHelper;

/**
 * Dialog used for creating new password.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class NewPasswordDialog extends JDialog {

    /**
     * Result of creating new password.
     */
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

    /**
     * Method setting upt the view and all elements.
     */
    private void initView() {
        domainText = new JTextField(15);
        usernameText = new JTextField(15);
        passwordText = new JPasswordField(15);
        passwordCheckText = new JPasswordField(15);
        encryptionKeyText = new JTextField(30);

        setResizable(false);
        setTitle("Add new password");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel name = new JLabel("Add new password");
        name.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 5, 10);
        add(name, constraints);

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;

        add(new JLabel("Type domain name: ", SwingConstants.RIGHT), constraints);
        add(new JLabel("Type username: ", SwingConstants.RIGHT), constraints);
        add(new JLabel("Type password: ", SwingConstants.RIGHT), constraints);
        add(new JLabel("Type password again: ", SwingConstants.RIGHT), constraints);
        JLabel encryptionLabel = new JLabel("Type encryption key: ", SwingConstants.RIGHT);
        encryptionLabel.setToolTipText("Encryption key should be at least 24 characters long!");
        add(encryptionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = GridBagConstraints.RELATIVE;

        add(domainText, constraints);
        add(usernameText, constraints);
        add(passwordText, constraints);
        add(passwordCheckText, constraints);
        add(encryptionKeyText, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.SOUTH;

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent event) -> {
            dispose();
        });
        JButton okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent event) -> {
            int checkResult = checkAllFields();
            if (checkResult != 0) {
                JOptionPane.showMessageDialog(getRootPane(),
                        ErrorMessageHelper.getErrorMessage(checkResult),
                        "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                result = new NewPasswordInput(domainText.getText(),
                        usernameText.getText(),
                        passwordText.getPassword(),
                        encryptionKeyText.getText());
                dispose();
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(cancelButton);
        buttons.add(okButton);

        add(buttons, constraints);
        pack();

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        setTitle("Add new password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        getRootPane().setDefaultButton(okButton);
    }

    /**
     * Method checking if all fields are correctly filled.
     *
     * @return result code.
     */
    private int checkAllFields() {
        if (domainText.getText().isEmpty()) {
            return 1;
        } else if (usernameText.getText().isEmpty()) {
            return 1;
        } else if (encryptionKeyText.getText().isEmpty()) {
            return 1;
        } else if (passwordText.getPassword().length == 0) {
            return 1;
        } else if (passwordCheckText.getPassword().length == 0) {
            return 1;
        } else if (!(comparePasswords())) {
            return 2;
        } else if (encryptionKeyText.getText().length() < 24) {
            return 3;
        }
        return 0;
    }

    /**
     * Method comparing two JPasswordFields.
     *
     * @return
     */
    private boolean comparePasswords() {
        return Arrays.equals(passwordText.getPassword(), passwordCheckText.getPassword());
    }

    /**
     * Method returning the result of adding new password.
     *
     * @return
     */
    public NewPasswordInput getResult() {
        return result;
    }

    /**
     * Class describing result of adding new password operation.
     */
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
