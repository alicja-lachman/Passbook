/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lachman.passbook.view;

import com.lachman.passbook.model.PassbookModel;
import com.lachman.passbook.view.NewPasswordDialog.NewPasswordInput;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.UIManager;
import util.ImageHelper;

/**
 * @vesrion 1.0
 * @author Alicja Lachman
 */
public class PassbookSwingView extends JFrame {

    private static final String ICON_PATH = "key_icon.png";
    private ImageHelper imageHelper = ImageHelper.get();

    JTextArea logTextArea;
    JTable passwordTable;
    PassbookModel passbookModel;

    public PassbookSwingView() {
        passbookModel = new PassbookModel();
        initView();

    }

    private void initView() {
        setSize(900, 700);
        setLayout(new BorderLayout());
        setTitle("Passbook");
        setIconImage(imageHelper.getApplicationIcon(ICON_PATH));
        setLocationRelativeTo(null);
        setBackground(Color.yellow);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setUpFonts();
        initMenu();
        initToolbar();
        initPasswordTable();
        initSplitPane();
    }

    private void setUpFonts() {
        Font font = new Font("sans-serif", Font.PLAIN, 18);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
    }

    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem fileMenuItem = new JMenuItem("Choose file");

        JMenu viewMenu = new JMenu("View");
        JMenuItem cleanLogMenuItem = new JMenuItem("Clean log");
        cleanLogMenuItem.addActionListener((ActionEvent e) -> {
            logTextArea.setText("");
        });
        viewMenu.add(cleanLogMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        JMenuItem authorMenuItem = new JMenuItem("About the author");
        authorMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAuthorInfoPopup();
            }
        });

        menu.add(fileMenuItem);

        helpMenu.add(helpMenuItem);
        helpMenu.add(authorMenuItem);
        menuBar.add(menu);
        menuBar.add(viewMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void showAuthorInfoPopup() {
        AboutDialog dialog = new AboutDialog(this);
        dialog.setVisible(true);
    }

    private void initToolbar() {

        JToolBar toolbar = new JToolBar();

        toolbar.setFloatable(true);

        JButton addButton = new JButton("Add new password", new ImageIcon(imageHelper.getApplicationIcon("add_icon.png")));
        addButton.addActionListener((ActionEvent e) -> {
            showNewPasswordDialog();
            // passbookModel.addRow();
        });
        JButton editButton = new JButton("Edit password", new ImageIcon(imageHelper.getApplicationIcon("edit_icon.png")));
        JButton deleteButton = new JButton("Delete password", new ImageIcon(imageHelper.getApplicationIcon("delete_icon3.png")));
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(Box.createHorizontalGlue());

        add(toolbar, BorderLayout.PAGE_START);
    }

    private void showNewPasswordDialog() {
        try{
        NewPasswordDialog newPasswordDialog = new NewPasswordDialog(this);
        newPasswordDialog.setVisible(true);
        if (newPasswordDialog.getResult() != null) {
            NewPasswordInput result = newPasswordDialog.getResult();
   passbookModel.createPassword(result.getDomain(), result.getUsername(), result.getPassword().toString(), result.getEncryptionKey());
            String domainName = newPasswordDialog.getResult().getUsername();
            logTextArea.append("Created password for domain: "+ domainName);
        }
        }catch(Exception e){
            logTextArea.append("Last operation failed due to exception: \n"+ e.getMessage()+ "\n");
        }
    }

    private void initPasswordTable() {

        passwordTable = new JTable(passbookModel);
    passwordTable.setAutoCreateRowSorter(true);
    }

    private void initSplitPane() {

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(passwordTable), scrollPane);
        splitPane.setDividerLocation(0.5d);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);

    }
}
