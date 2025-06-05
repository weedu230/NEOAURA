package neoaura;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgetPassword {
    private JPanel leftpanel;
    private JPanel Forgetform;
    private JTextField email;
    private JTextField mobileno;
    private JPasswordField newpassword;
    private JButton resendOTPButton;
    private JButton changePasswordButton;
    private JFormattedTextField otp;
    private JPanel Rightpanel;
    private JButton Goback;
    String sentOTP = null;

    public ForgetPassword(JFrame current) {
        CommonMethods size=new CommonMethods();
        Forgetform.setMinimumSize(size.screensize());
        Forgetform.setPreferredSize(size.screensize());
        Forgetform.setMaximumSize(size.Fullscreensize());
        Rightpanel.setMinimumSize(size.rightpanelsize());
        Rightpanel.setPreferredSize(size.rightpanelsize());
        Rightpanel.setMaximumSize(size.Fullrightpanelsize());
        leftpanel.setMinimumSize(size.leftpanelsize());
        leftpanel.setPreferredSize(size.leftpanelsize());
        leftpanel.setMaximumSize(size.Fullleftpanelsize());
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredOTP = otp.getText();
                String newPass = new String(newpassword.getPassword());
                String mail = email.getText().trim().toLowerCase();
                String mobile = mobileno.getText().trim().toLowerCase();

                if (mail.isEmpty() || mobile.isEmpty()) {
                    JOptionPane.showMessageDialog(Forgetform, "Email and Mobile Number are required.");
                    }
                else {
                    if (sentOTP != null && enteredOTP.equals(sentOTP)) {
                        if (Database.updatePassword(mail, newPass)) {
                            JOptionPane.showMessageDialog(Forgetform, "Password Updated. Please Login.");
                            current.dispose();
                            JFrame frame = new JFrame("NEO AURA");
                            Userloginform app = new Userloginform(frame);
                            frame.setContentPane(app.getLoginform());
                            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setLocationRelativeTo(null);
                        } else {
                            JOptionPane.showMessageDialog(Forgetform, "Failed to update password.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(Forgetform, "Incorrect OTP.");
                    }
                }
            }
        });
        resendOTPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = ForgetPassword.this.email.getText();
                String mobile = mobileno.getText();

                if (Database.checkUserForOTP(email, mobile)) {
                    sentOTP = Database.generateOTP();
                    boolean sent = Database.sendOTP(email, sentOTP);
                    if (sent) {
                        JOptionPane.showMessageDialog(Forgetform,"OTP sent to your email.");
                    } else {
                        JOptionPane.showMessageDialog(Forgetform, "Failed to send OTP.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Forgetform, "No user found with this email and mobile.");
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
    public JPanel GetForgetform()
    {
        return Forgetform;
    }
}
