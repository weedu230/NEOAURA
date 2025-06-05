package neoaura;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorAndUser {
    private JPanel AdminAndUserForm;
    private JButton ADMINSTRATORButton;
    private JButton USERButton;
    private JPanel PANEL;

    public AdministratorAndUser(JFrame current) {
        CommonMethods size=new CommonMethods();
        PANEL.setMinimumSize(size.screensize());
        PANEL.setPreferredSize(size.screensize());
        PANEL.setMaximumSize(size.Fullscreensize());
        ADMINSTRATORButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.dispose();
                JFrame frame = new JFrame("NEO AURA");
                Adminloginform app = new Adminloginform(frame);
                frame.setContentPane(app.getLoginform());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
        USERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.dispose();
                JFrame frame = new JFrame("NEO AURA");
                Userloginform app = new Userloginform(frame);
                frame.setContentPane(app.getLoginform());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    public JPanel getAdminAndUserForm() {
        return AdminAndUserForm;
    }
}
