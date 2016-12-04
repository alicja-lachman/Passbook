package com.lachman.passbook.view.dialog;

import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.CENTER;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.lachman.passbook.util.ImageHelper;

/**
 * Dialog displaying help of the application.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class HelpDialog extends JDialog {

    public HelpDialog(Frame parent) {
        super(parent);
        initView();
    }

    /**
     * Method setting up the view and all elements.
     */
    private void initView() {
        setResizable(false);

        String text = "<html><center>How to.... </center> <ul> <li>Add new password? "
                + "<ul> <li>Click &quot;Add new password&quot; on the toolbar and fill all fields.</li> "
                + "</ul> </li> <li>Get decrypted password? "
                + "<ul> <li>Double click on a password and "
                + "click &quot;Get password&quot; on the toolbar, then type in "
                + "<br> the encryption key you used for creating the password.</li> "
                + "</ul> </li> <li>Edit an existing password? <ul> "
                + "<li>Select password, then click &quot;Edit password&quot; "
                + "on the toolbar&nbsp;and fill all fields.</li> </ul> </li> "
                + "<li>Delete an existing password? <ul> <li>Select password, "
                + "then click &quot;Delete password&quot;&nbsp;on the toolbar."
                + "</li> </ul> </li> <li>Open file with passwords? <ul> "
                + "<li>Click File-&gt;Open file.. (or use shortcut ctrl+o) "
                + "and select the file.</li> </ul> </li> <li>Save changes in "
                + "file? <ul> <li>Click File-&gt;Save file "
                + "</li> </ul> </li> <li>Save changes to an existing "
                + "file or create a new file? <ul> <li>Click File-&gt;Save to "
                + "file.. and select a file or type in the file&#39;s name. "
                + "Don&#39;t forget about extension!</li> </ul> </li> <li>Clear "
                + "log messages? <ul> <li>Click View-&gt;Clean log.</li> </ul> "
                + "</li> </ul></html>";

        JLabel description = new JLabel(text);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panel.add(description);
        add(panel);
        setModalityType(ModalityType.MODELESS);

        setTitle("How to...");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
    }

}
