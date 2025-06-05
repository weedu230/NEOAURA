package neoaura;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup {
    private JPanel leftpanel;
    private JPanel rightpanel;
    private JPasswordField password;
    private JTextField firstname;
    private JFormattedTextField email;
    private JTextField mobileno;
    private JCheckBox agreeWithTermsAndCheckBox;
    private JButton makeAccountButton;
    private JPanel Signupform;
    private JTextField lastname;
    private JButton Goback;

    public Signup(JFrame current) {
        CommonMethods size = new CommonMethods();
        Signupform.setMinimumSize(size.screensize());
        Signupform.setPreferredSize(size.screensize());
        Signupform.setMaximumSize(size.Fullscreensize());
        rightpanel.setMinimumSize(size.rightpanelsize());
        rightpanel.setPreferredSize(size.rightpanelsize());
        rightpanel.setMaximumSize(size.Fullrightpanelsize());
        leftpanel.setMinimumSize(size.leftpanelsize());
        leftpanel.setPreferredSize(size.leftpanelsize());
        leftpanel.setMaximumSize(size.Fullleftpanelsize());
        makeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = firstname.getText().trim().toLowerCase();
                String lname = lastname.getText().trim().toLowerCase();
                String mail = email.getText().trim().toLowerCase();
                String mobile = mobileno.getText().trim();
                String pass = new String(password.getPassword());

                if (fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || mobile.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(Signupform, "All fields are required.");
                    return;
                }

                if (!agreeWithTermsAndCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(Signupform, "Please agree to terms and conditions.");
                    return;
                }

                if (!isValidPassword(pass)) {
                    JOptionPane.showMessageDialog(Signupform, "Password must contain at least one uppercase letter, one lowercase letter, and one number and minimum of 8 characters.");
                    return;
                }

                if (!isValidGmail(mail)) {
                    JOptionPane.showMessageDialog(Signupform, "Email must be a valid Gmail address (e.g., yourname@gmail.com).");
                    return;
                }
                if (Database.isEmailExists(mail)) {
                    JOptionPane.showMessageDialog(Signupform, "An account already exists with this Gmail.");
                    return;
                }

                if (Database.isMobileExists(mobile)) {
                    JOptionPane.showMessageDialog(Signupform, "This mobile number is already registered.");
                    return;
                }

                if (Database.isNameExists(fname, lname)) {
                    JOptionPane.showMessageDialog(Signupform, "A user with this name already exists.");
                    return;
                }
                boolean success = Database.signupUser(fname, lname, mail, mobile, pass);
                if (success) {
                    JOptionPane.showMessageDialog(Signupform, "Signup successful! Please log in.");
                    current.dispose();
                    JFrame frame = new JFrame("NEO AURA");
                    Userloginform app = new Userloginform(frame);
                    frame.setContentPane(app.getLoginform());
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } else {
                    JOptionPane.showMessageDialog(Signupform, "Signup failed. Please try again.");
                }
            }
        });
        Goback.addActionListener(new ActionListener() {
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

        private boolean isValidPassword(String password) {
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        }

        private boolean isValidGmail(String email) {
            return email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
        }
    public JPanel GetSignupform()
    {
        return Signupform;
    }
}
