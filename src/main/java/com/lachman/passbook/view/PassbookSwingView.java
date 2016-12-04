package com.lachman.passbook.view;

import com.lachman.passbook.view.dialog.HelpDialog;
import com.lachman.passbook.view.dialog.EditPasswordDialog;
import com.lachman.passbook.view.dialog.NewPasswordDialog;
import com.lachman.passbook.model.PassbookModel;
import com.lachman.passbook.view.dialog.EditPasswordDialog.EditPasswordResult;
import com.lachman.passbook.view.dialog.NewPasswordDialog.NewPasswordInput;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.lachman.passbook.util.ImageHelper;

/**
 * View class of the application.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class PassbookSwingView extends JFrame {

    /**
     * path to application's icon
     */
    private static final String ICON_PATH = "key_icon.png";
    /**
     * Reference to ImageHelper
     */
    private ImageHelper imageHelper = ImageHelper.get();
    /**
     * Text area used for logging user's operations.
     */
    JTextArea logTextArea;
    /**
     * Table displaying all stored passwords.
     */
    JTable passwordTable;
    /**
     * Data model for table.
     */
    PassbookModel passbookModel;
    /**
     * File in which the passwords are stored.
     */
    File file;

    public PassbookSwingView() {
        passbookModel = new PassbookModel();
        initView();

    }

    /**
     * Method setting upt the view and all controls.
     */
    private void initView() {
        setSize(900, 700);
        setLayout(new BorderLayout());
        setTitle("Passbook");
        setIconImage(imageHelper.getIcon(ICON_PATH));
        setLocationRelativeTo(null);
        setBackground(Color.yellow);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setUpUIManager();
        initMenu();
        initToolbar();
        initPasswordTable();
        initSplitPane();
    }

    /**
     * Method setting up UIManager's options.
     */
    private void setUpUIManager() {
        Font font = new Font("sans-serif", Font.PLAIN, 18);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("ToolTip.background", Color.WHITE);
    }

    /**
     * Method initializing menu and its elements.
     */
    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem fileMenuItem = new JMenuItem("Open file..");
        fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        fileMenuItem.setToolTipText("Choose a file which contains a list of passwords in json format");
        JMenuItem saveMenuItem = new JMenuItem("Save file");
        saveMenuItem.addActionListener((ActionEvent e) -> {
            if (saveMenuItem.isEnabled()) {
                try {
                    passbookModel.savePasswordsToFile(file);
                } catch (IOException ex) {
                    logTextArea.append("Operation failed due to: "
                            + ex.getMessage() + "\n");
                }
            }
        });
        saveMenuItem.setEnabled(false);

        fileMenuItem.addActionListener((ActionEvent e) -> {
            try {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Text & JSON files", "txt", "json");
                chooser.setFileFilter(filter);
                int result = chooser.showOpenDialog(null);
                if (result == 1) {
                    return;
                }
                file = chooser.getSelectedFile();
                passbookModel.getPasswordsFromFile(file);
                saveMenuItem.setEnabled(true);
            } catch (Exception ex) {

                logTextArea.append("Reading from file did not succeed. Cause: "
                        + "\n" + ex.getMessage() + "\n");
            }
        }
        );
        menu.add(fileMenuItem);
        menu.add(saveMenuItem);
        JMenuItem saveFileMenuItem = new JMenuItem("Save to file..");
        saveFileMenuItem.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Text & JSON files", "txt", "json");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Choose file");
            chooser.setApproveButtonText("Save");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            chooser.setAcceptAllFileFilterUsed(false);
            try {
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    passbookModel.savePasswordsToFile(chooser.getSelectedFile());
                    logTextArea.append("Passwords successfully saved to \n"
                            + chooser.getSelectedFile());
                }
            } catch (IOException ex) {
                logTextArea.append("Saving to file failed due to: " + ex.getMessage());
            }
        }
        );
        menu.add(saveFileMenuItem);

        JMenu viewMenu = new JMenu("View");
        JMenuItem cleanLogMenuItem = new JMenuItem("Clean log");

        cleanLogMenuItem.addActionListener(
                (ActionEvent e) -> {
                    logTextArea.setText("");
                }
        );
        viewMenu.add(cleanLogMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("How to...");

        helpMenuItem.addActionListener(
                (ActionEvent e) -> {
                    showHelpPopup();
                }
        );

        helpMenu.add(helpMenuItem);

        menuBar.add(menu);

        menuBar.add(viewMenu);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Method used for displaying help dialog.
     */
    private void showHelpPopup() {
        HelpDialog dialog = new HelpDialog(this);
        dialog.setVisible(true);
    }

    /**
     * Method initializing toolbar and its elements.
     */
    private void initToolbar() {

        JToolBar toolbar = new JToolBar();

        toolbar.setFloatable(true);

        JButton addButton = new JButton("Add new password",
                new ImageIcon(imageHelper.getIcon("add_icon.png")));
        addButton.addActionListener((ActionEvent e) -> {
            showNewPasswordDialog();
        });
        JButton editButton = new JButton("Edit password",
                new ImageIcon(imageHelper.getIcon("edit_icon.png")));

        editButton.addActionListener((ActionEvent e) -> {
            int selectedItem = passwordTable.getSelectedRow();
            if (selectedItem < 0) {
                JOptionPane.showMessageDialog(getRootPane(),
                        "You have to select a password first!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String domainName = (String) passbookModel.getValueAt(selectedItem, 0);
            EditPasswordDialog editDialog = new EditPasswordDialog(this, domainName);
            editDialog.setVisible(true);
            if (editDialog.getResult() != null) {
                try {
                    EditPasswordResult result = editDialog.getResult();
                    passbookModel.changePasswordForDomain(domainName,
                            result.getPassword().toString(),
                            result.getEncryptionKey());

                    logTextArea.append("Edited password for domain: "
                            + domainName + "\n");
                } catch (Exception ex) {
                    logTextArea.append("Last operation failed due to exception: \n"
                            + ex.getMessage() + "\n");
                }
            }
        });

        JButton deleteButton = new JButton("Delete password",
                new ImageIcon(imageHelper.getIcon("delete_icon3.png")));
        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedItem = passwordTable.getSelectedRow();
            if (selectedItem < 0) {
                JOptionPane.showMessageDialog(getRootPane(),
                        "You have to select a password first!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String domainName = (String) passbookModel.getValueAt(selectedItem, 0);
            int result = JOptionPane.showConfirmDialog(getRootPane(),
                    "Do you really want to delete password for " + domainName + "?",
                    "Delete password", JOptionPane.YES_NO_OPTION);
            try {
                if (result == JOptionPane.YES_OPTION) {
                    passbookModel.deletePasswordForDomain(domainName);
                    logTextArea.append("Deleted password for " + domainName + "\n");

                }
            } catch (IOException ex) {
                logTextArea.append("An exception occured: " + ex.getMessage() + "\n");
            }
        });
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(Box.createHorizontalGlue());

        add(toolbar, BorderLayout.PAGE_START);
    }

    /**
     * Method showing new password dialog.
     */
    private void showNewPasswordDialog() {
        try {
            NewPasswordDialog newPasswordDialog = new NewPasswordDialog(this);
            newPasswordDialog.setVisible(true);
            if (newPasswordDialog.getResult() != null) {
                NewPasswordInput result = newPasswordDialog.getResult();
                passbookModel.createPassword(result.getDomain(),
                        result.getUsername(), result.getPassword(),
                        result.getEncryptionKey());
                String domainName = newPasswordDialog.getResult().getDomain();
                logTextArea.append("Created password for domain: " + domainName + "\n");
            }
        } catch (Exception e) {
            logTextArea.append("Last operation failed due to exception: \n"
                    + e.getMessage() + "\n");
        }
    }

    /**
     * Method initializing password table.
     */
    private void initPasswordTable() {

        passwordTable = new JTable(passbookModel);
        passwordTable.setAutoCreateRowSorter(true);
        passwordTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        String domainName = (String) passbookModel
                                .getValueAt(passwordTable.getSelectedRow(), 0);
                        String encryptionKey = (JOptionPane
                                .showInputDialog("Please input encryption key for "
                                        + domainName));
                        JOptionPane.showMessageDialog(null, "Password for "
                                + domainName + " is: "
                                + passbookModel.getDecryptedPasswordForDomain(domainName, encryptionKey));
                    } catch (Exception ex) {
                        logTextArea.append("Last operation failed due to: "
                                + ex.getMessage() + "\n");
                    }
                }
            }
        });
    }

    /**
     * Method initializing split pane with password table and log area.
     */
    private void initSplitPane() {

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(logTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(passwordTable), scrollPane);
        splitPane.setDividerLocation(0.5d);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);

    }
}
