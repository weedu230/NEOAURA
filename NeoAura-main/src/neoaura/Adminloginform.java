package neoaura;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Adminloginform {
    private JPanel leftpanel;
    private JPanel rightpanel;
    private JButton loginButton;
    private JPasswordField password;
    private JTextField username;
    private JButton gobackbutton;
    private JPanel Loginform;

    public Adminloginform(JFrame current) {
        CommonMethods size=new CommonMethods();
        Loginform.setMinimumSize(size.screensize());
        Loginform.setPreferredSize(size.screensize());
        Loginform.setMaximumSize(size.Fullscreensize());
        rightpanel.setMinimumSize(size.rightpanelsize());
        rightpanel.setPreferredSize(size.rightpanelsize());
        rightpanel.setMaximumSize(size.Fullrightpanelsize());
        leftpanel.setMinimumSize(size.leftpanelsize());
        leftpanel.setPreferredSize(size.leftpanelsize());
        leftpanel.setMaximumSize(size.Fullleftpanelsize());
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = username.getText().trim().toLowerCase();
                String pass = new String(password.getPassword());
                if (email.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(Loginform, "Both email and password are required.");
                }
                else {
                    if (Database.loginAdmin(email, pass)) {
                        current.dispose();
                        JFrame frame = new JFrame("NEO AURA");
                        AdminInterface app = new AdminInterface(frame);
                        frame.setContentPane(app.getMainpanel());
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setLocationRelativeTo(null);
                    } else {
                        JOptionPane.showMessageDialog(Loginform, "Incorrect Email or Password", "Login", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        gobackbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.dispose();
                JFrame frame = new JFrame("NEO AURA");
                AdministratorAndUser app = new AdministratorAndUser(frame);
                frame.setContentPane(app.getAdminAndUserForm());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    public JPanel getLoginform() {
        return Loginform;
    }
}
