/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lachman.passbook.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import util.ImageHelper;

/**
 * @version 1.0
 * @author Alicja Lachman
 */
public class AboutDialog extends JDialog {

    private static final String JAVA_ICON = "java_icon.png";

    public AboutDialog(Frame parent) {
        super(parent);

        initView();
    }

    private void initView() {
        setResizable(false);
        ImageIcon icon = new ImageIcon(ImageHelper.get().getApplicationIcon(JAVA_ICON));
        JLabel label = new JLabel(icon);

        JLabel name = new JLabel("Author, 1.23");
        name.setFont(new Font("Serif", Font.BOLD, 13));

        JButton btn = new JButton("OK");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        createLayout(name, label, btn);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("About author");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup(CENTER)
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addGap(200)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(30)
                .addComponent(arg[0])
                .addGap(20)
                .addComponent(arg[1])
                .addGap(20)
                .addComponent(arg[2])
                .addGap(30)
        );

        pack();
    }


}
