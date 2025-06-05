package neoaura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class AdminInterface {
    private JPanel Buttonpanel;
    private JButton usersButton;
    private JButton dashboardButton;
    private JPanel Barpanel;
    private JButton logoutButton;
    private JPanel Cardpanel;
    private JPanel Mainpanel;
    private JPanel Dashboard;
    private JPanel User;
    private JButton pollbutton;
    private JPanel Pollpanel;
    private JButton DeleteQuestionbutton;
    private JPanel Deletepoll;

    public AdminInterface(JFrame current) {
        CommonMethods size = new CommonMethods();
        Mainpanel.setMinimumSize(size.screensize());
        Mainpanel.setPreferredSize(size.screensize());
        Mainpanel.setMaximumSize(size.Fullscreensize());

        Buttonpanel.setMinimumSize(size.halfbuttonpanel());
        Buttonpanel.setPreferredSize(size.halfbuttonpanel());
        Buttonpanel.setMaximumSize(size.fullbuttonpanel());

        Cardpanel.setMinimumSize(size.halfcardpanel());
        Cardpanel.setPreferredSize(size.halfcardpanel());
        Cardpanel.setMaximumSize(size.fullcardpanel());

        Dashboard.setMinimumSize(size.halfcardpanel());
        Dashboard.setPreferredSize(size.halfcardpanel());
        Dashboard.setMaximumSize(size.fullcardpanel());

        User.setMinimumSize(size.halfcardpanel());
        User.setPreferredSize(size.halfcardpanel());
        User.setMaximumSize(size.fullcardpanel());

        CardLayout cardLayout = (CardLayout) Cardpanel.getLayout();
        cardLayout.show(Cardpanel, "Dashboard");
        loaddashboard();

        Dashboard.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loaddashboard();
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "Dashboard");
            }
        });
        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "User");

            }
        });
        User.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                User.removeAll();

                Database db = new Database();
                List<String[]> users = db.getAllUsers();

                JPanel userListPanel = new JPanel();
                userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
                userListPanel.setBackground(new Color(245, 245, 245));

                for (int i = 0; i < users.size(); i++) {
                    int index = i;
                    String[] user = users.get(i);

                    JPanel userRow = new JPanel();
                    userRow.setLayout(new BorderLayout(10, 0));
                    userRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                    userRow.setBackground(user[1].equalsIgnoreCase("Active") ? new Color(0x4CAF50) : new Color(0xF44336));
                    userRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                    JLabel emailLabel = new JLabel(user[0]);
                    emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    emailLabel.setForeground(Color.WHITE);

                    JLabel statusLabel = new JLabel(user[1]);
                    statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    statusLabel.setForeground(Color.WHITE);
                    statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                    JButton blockButton = new JButton(user[1].equalsIgnoreCase("Active") ? "Block" : "Unblock");
                    blockButton.setBackground(Color.BLACK);
                    blockButton.setForeground(Color.WHITE);
                    blockButton.setFocusPainted(false);
                    blockButton.setPreferredSize(new Dimension(100, 30));

                    blockButton.addActionListener(ev -> {
                        String email = user[0];
                        String currentStatus = user[1];
                        String newStatus = currentStatus.equalsIgnoreCase("Active") ? "Inactive" : "Active";

                        db.updateUserStatus(email, newStatus);

                        statusLabel.setText(newStatus);
                        userRow.setBackground(newStatus.equalsIgnoreCase("Active") ? new Color(0x4CAF50) : new Color(0xF44336));
                        blockButton.setText(newStatus.equalsIgnoreCase("Active") ? "Block" : "Unblock");
                        users.set(index, new String[]{email, newStatus});
                    });

                    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    rightPanel.setOpaque(false);
                    rightPanel.add(statusLabel);
                    rightPanel.add(Box.createHorizontalStrut(20));
                    rightPanel.add(blockButton);

                    userRow.add(emailLabel, BorderLayout.WEST);
                    userRow.add(rightPanel, BorderLayout.EAST);

                    userListPanel.add(userRow);
                    userListPanel.add(Box.createVerticalStrut(10));
                }

                JScrollPane scrollPane = new JScrollPane(userListPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);

                User.setLayout(new BorderLayout());
                User.add(scrollPane, BorderLayout.CENTER);
                User.revalidate();
                User.repaint();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
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
        pollbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pollpanel.removeAll();
                Pollpanel.setLayout(new BorderLayout());
                Pollpanel.add(new Adminpoll(), BorderLayout.CENTER);
                Pollpanel.revalidate();
                Pollpanel.repaint();

                cardLayout.show(Cardpanel, "Polladmin");
            }
        });
        Pollpanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                Pollpanel.removeAll();
                Pollpanel.setLayout(new BorderLayout());
                Pollpanel.add(new Adminpoll(), BorderLayout.CENTER);
                Pollpanel.revalidate();
                Pollpanel.repaint();

            }
        });
        DeleteQuestionbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "Deletepoll");
            }
        });
        Deletepoll.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                deletepolls();
            }
        });

    }

    private void deletepolls() {
        Deletepoll.removeAll();
        Deletepoll.setLayout(new BorderLayout());

        Database db = new Database();
        java.util.List<Poll> polls = db.getAllPolls();

        if (polls.isEmpty()) {
            JLabel noPollsLabel = new JLabel("No polls available to delete.");
            noPollsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            Deletepoll.add(noPollsLabel, BorderLayout.CENTER);
        } else {
            JComboBox<String> comboBoxPolls = new JComboBox<>();
            for (Poll poll : polls) {
                comboBoxPolls.addItem(poll.getQuestion());
            }

            JButton deleteButton = new JButton("Delete Selected Poll");
            deleteButton.setBackground(new Color(255, 210, 0));
            deleteButton.setForeground(new Color(255, 255, 255));
            JPanel topPanel = new JPanel(new FlowLayout());
            topPanel.add(new JLabel("Select Poll to delete:"));
            topPanel.add(comboBoxPolls);
            topPanel.add(deleteButton);

            Deletepoll.add(topPanel, BorderLayout.NORTH);

            JLabel resultLabel = new JLabel("");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            Deletepoll.add(resultLabel, BorderLayout.CENTER);

            deleteButton.addActionListener(ev -> {
                int selectedIndex = comboBoxPolls.getSelectedIndex();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(Deletepoll, "No poll selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Poll selectedPoll = polls.get(selectedIndex);

                int confirm = JOptionPane.showConfirmDialog(Deletepoll,
                        "Are You Sure You Want To Delete This Poll?\n" + selectedPoll.getQuestion(),
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    db.deletePoll(selectedPoll.getId());
                    JOptionPane.showMessageDialog(Deletepoll, "Poll deleted successfully.");

                    Deletepoll.dispatchEvent(new ComponentEvent(Deletepoll, ComponentEvent.COMPONENT_SHOWN));
                }
            });
        }

        Deletepoll.revalidate();
        Deletepoll.repaint();
    }


    private void loaddashboard() {
        Dashboard.removeAll();
        Dashboard.setLayout(new BorderLayout());

        Database db = new Database();

        int totalUsers = db.getTotalUsers();
        int activeUsers = db.getActiveUsers();
        int blockedUsers = db.getBlockedUsers();
        int totalPolls = db.getTotalPolls();
        int totalChats = db.getTotalChats();
        String systemUptime = "12h 45m";

        String[] titles = {
                "Total Users", "Active Users", "Blocked Users",
                "Total Polls", "Total Chats", "System Uptime"
        };
        String[] values = {
                String.valueOf(totalUsers),
                String.valueOf(activeUsers),
                String.valueOf(blockedUsers),
                String.valueOf(totalPolls),
                String.valueOf(totalChats),
                systemUptime
        };
        Color[][] gradientColors = {
                {new Color(0x42275a), new Color(0x734b6d)}, // deep purple
                {new Color(0xcc2b5e), new Color(0x753a88)}, // pink-purple
                {new Color(0x373B44), new Color(0x4286f4)}, // dark steel blue
                {new Color(0x614385), new Color(0x516395)}, // royal violet-blue
                {new Color(0x000428), new Color(0x004e92)}, // dark navy blue
                {new Color(0x4568dc), new Color(0xb06ab3)}  // violet-indigo
        };

        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setBackground(new Color(30, 30, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String value = values[i];
            Color startColor = gradientColors[i][0];
            Color endColor = gradientColors[i][1];

            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
                    g2.setPaint(gp);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    super.paintComponent(g);
                }
            };

            card.setOpaque(false);
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            card.setPreferredSize(new Dimension(200, 120));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titleLabel.setForeground(Color.WHITE);

            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            valueLabel.setForeground(Color.WHITE);
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            card.add(titleLabel, BorderLayout.NORTH);
            card.add(valueLabel, BorderLayout.CENTER);
            grid.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        Dashboard.add(scrollPane, BorderLayout.CENTER);
        Dashboard.revalidate();
        Dashboard.repaint();
    }

    public JPanel getMainpanel()
    {
        return Mainpanel;
    }
}
