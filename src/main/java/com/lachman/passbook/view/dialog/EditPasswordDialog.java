package com.lachman.passbook.view.dialog;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.lachman.passbook.util.ErrorMessageHelper;

/**
 * Dialog used for editing an existing password
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class EditPasswordDialog extends JDialog {

    /**
     * domain name for which the password is being changed
     */
    private String domainName;
    /**
     * Field for entering new password.
     */
    JPasswordField password;
    /**
     * Field for entering new password again, to avoid mistakes.
     */
    JPasswordField passwordCheck;
    /**
     * Field for entering encryption key used for encrypting the password.
     */
    JTextField encryptionKey;
    /**
     * Result of the editting operation.
     */
    EditPasswordResult result;

    /**
     * Constructor of the class.
     *
     * @param parent
     * @param domainName
     */
    public EditPasswordDialog(Frame parent, String domainName) {
        super(parent);
        this.domainName = domainName;
        initView();

    }

    /**
     * Method setting up the view and all elements.
     */
    private void initView() {

        password = new JPasswordField(15);
        passwordCheck = new JPasswordField(15);

        encryptionKey = new JTextField(25);

        setResizable(false);
        setTitle("Edit password");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel title = new JLabel("Edit password for " + domainName);
        title.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 10, 5, 10);
        add(title, constraints);

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;

        add(new JLabel("New password:", SwingConstants.RIGHT), constraints);
        add(new JLabel("Repeat new password:", SwingConstants.RIGHT), constraints);
        JLabel encryptionLabel = new JLabel("Encryption key: ", SwingConstants.RIGHT);
        encryptionLabel.setToolTipText("Encryption key should be at least 24 characters long!");
        add(encryptionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = GridBagConstraints.RELATIVE;

        add(password, constraints);
        add(passwordCheck, constraints);
        add(encryptionKey, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.SOUTH;
        JPanel buttonsPanel = new JPanel();

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent event) -> {
            dispose();
        });
        JButton okButton = new JButton("Edit");
        okButton.addActionListener((ActionEvent event) -> {
            int resultCode = checkAllFields();
            if (resultCode != 0) {
                JOptionPane.showMessageDialog(getRootPane(),
                        ErrorMessageHelper.getErrorMessage(resultCode),
                        "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                result = new EditPasswordResult(
                        password.getPassword(),
                        encryptionKey.getText());
                dispose();
            }
        });
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);
        add(buttonsPanel, constraints);

        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        getRootPane().setDefaultButton(okButton);

    }

    /**
     * Method checking if all fields are correctly filled
     *
     * @return result code
     */
    private int checkAllFields() {
        if (encryptionKey.getText().isEmpty()) {
            return 1;
        } else if (password.getPassword().length == 0) {
            return 1;
        } else if (passwordCheck.getPassword().length == 0) {
            return 1;
        } else if (!comparePasswords()) {
            return 2;
        } else if (encryptionKey.getText().length() < 24) {
            return 3;
        }
        return 0;
    }

    /**
     * Method used for comparing text from two JPasswordFields.
     *
     * @return
     */
    private boolean comparePasswords() {
        return Arrays.equals(password.getPassword(), passwordCheck.getPassword());
    }

    /**
     * Method returning the result of editting the password.
     *
     * @return
     */
    public EditPasswordResult getResult() {
        return result;
    }

    /**
     * Class used for defining the edditing result.
     */
    public class EditPasswordResult {

        /**
         * new password
         */
        private char[] password;

        private String encryptionKey;

        public EditPasswordResult(char[] password, String encryptionKey) {

            this.password = password;
            this.encryptionKey = encryptionKey;
        }

        public char[] getPassword() {
            return password;
        }

        public String getEncryptionKey() {
            return encryptionKey;
        }

    }
}
