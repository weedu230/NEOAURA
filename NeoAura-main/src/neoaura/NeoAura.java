package neoaura;

import javax.swing.*;

public class NeoAura {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NEO AURA");
        AdministratorAndUser app = new AdministratorAndUser(frame);
        frame.setContentPane(app.getAdminAndUserForm());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }
}
