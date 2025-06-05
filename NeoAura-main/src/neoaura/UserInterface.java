package neoaura;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
public class UserInterface {
    private JButton AiButton;
    private JButton botSelectionButton;
    private JButton logoutButton;
    private JButton DASHBOARDButton;
    private JPanel Buttonpanel;
    private JPanel Mainpanel;
    private JPanel Cardpanel;
    private JPanel Barpanel;
    private JPanel Dashboard;
    private JPanel Chat;
    private JPanel Botselection;
    public JTextField textfield;
    public JButton sendtext;
    private JTextPane TextDisplay;
    private JScrollPane scrollpanel;
    private JPanel bottompanel;
    private JButton pollbutton;
    private JPanel Pollpanel;
    private JLabel usernamelabel;
    private JScrollPane scrollpane;
    private int count=0;
    private AIChat aiChat;

    public UserInterface(JFrame current) {
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

        Chat.setMinimumSize(size.halfchatpanel());
        Chat.setPreferredSize(size.halfchatpanel());
        Chat.setMaximumSize(size.fullchatpanel());

        Botselection.setMinimumSize(size.halfcardpanel());
        Botselection.setPreferredSize(size.halfcardpanel());
        Botselection.setMaximumSize(size.fullcardpanel());

        bottompanel.setMinimumSize(size.halfbottompanel());
        bottompanel.setPreferredSize(size.halfbottompanel());
        bottompanel.setMaximumSize(size.fullbottompanel());
        sendtext.setMinimumSize(new Dimension(80, 30));
        sendtext.setPreferredSize(new Dimension(80, 30));
        sendtext.setMaximumSize(new Dimension(80, 30));
        textfield.setMinimumSize(size.textfield());
        textfield.setPreferredSize(size.textfield());
        textfield.setMaximumSize(size.fulltextfield());

        CardLayout cardLayout = (CardLayout) Cardpanel.getLayout();
        cardLayout.show(Cardpanel, "Dashboard");
        loaddashboard();
        usernamelabel.setText(User.getFullName());
        sendtext.addActionListener(ev -> sendUserMessage());
        textfield.addActionListener(ev -> sendUserMessage());
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        String apiKey1 = "AIzaSyBV9gshoMzZs-oeubYD2NL0HxY9hlzLRNw";
        String apiKey2 = "AIzaSyAvv5A7yBE9pUF8sfeQWs_pjMd849as5P4";
        String apiKey3 = "AIzaSyBL3bTjGmJeNcQQFSG3b4p8WC5XCQBVk0Q";
        Map<String, String> botKeyMap = new HashMap<>() {{
            put("ChatGPT", apiKey1);
            put("Deepseek", apiKey1);
            put("Gamma", apiKey2);
            put("Meta", apiKey2);
            put("Gemmini", apiKey3);
            put("Claude", apiKey3);
        }};



        DASHBOARDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "Dashboard");
            }
        });
        AiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "Chat");
                Chat.revalidate();
                Chat.repaint();
                aiChat = new AIChat(apiKey1);
            }
        });
        botSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(Cardpanel, "Botselection");
            }
        });
        Dashboard.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
               loaddashboard();
            }
        });
        Botselection.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                Botselection.removeAll();
                Botselection.setLayout(new BorderLayout());
                Botselection.setBackground(new Color(18, 18, 18));

                JLabel heading = new JLabel("Select a Bot", SwingConstants.CENTER);
                heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
                heading.setForeground(Color.WHITE);
                heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
                Botselection.add(heading, BorderLayout.NORTH);

                String[] bots = {"ChatGPT", "Deepseek", "Gamma", "Meta", "Gemmini","Claude"};
                JPanel gridPanel = new JPanel(new GridLayout(0, 2, 25, 25));
                gridPanel.setBackground(new Color(18, 18, 18));
                gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

                for (String bot : bots) {
                    JButton botCard = new JButton(bot);
                    botCard.setFocusPainted(false);
                    botCard.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    botCard.setForeground(Color.WHITE);
                    botCard.setBackground(new Color(40, 40, 40));
                    botCard.setPreferredSize(new Dimension(160, 80));
                    botCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    botCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(255, 210, 0), 2, true), // Rounded border
                            BorderFactory.createEmptyBorder(12, 24, 12, 24)
                    ));

                    botCard.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            botCard.setBackground(new Color(60, 60, 60));
                        }

                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            botCard.setBackground(new Color(40, 40, 40));
                        }
                    });

                    botCard.addActionListener(a -> {
                        String selectedBot = bot;
                        String selectedKey = botKeyMap.get(selectedBot);

                        aiChat.setApiKey(selectedKey);

                        TextDisplay.setText("Chatting with " + selectedBot + "...\n");
                        CardLayout layout = (CardLayout) Cardpanel.getLayout();
                        layout.show(Cardpanel, "Chat");
                        Chat.revalidate();
                        Chat.repaint();
                    });

                    gridPanel.add(botCard);
                }

                Botselection.add(gridPanel, BorderLayout.CENTER);
                Botselection.revalidate();
                Botselection.repaint();
            }
            });

        Chat.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                Chat.revalidate();
                Chat.repaint();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
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
                User.clearSession();
            }
        });
        pollbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = new Database();
                java.util.List<Poll> polls = db.getAllPolls();

                if (polls.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No polls available.");
                    return;
                }

                Pollpanel.removeAll();
                Pollpanel.setLayout(new BoxLayout(Pollpanel, BoxLayout.Y_AXIS));
                Pollpanel.setBackground(Color.WHITE);

                for (Poll pollData : polls) {
                    Userpoll userPollPanel = new Userpoll();
                    userPollPanel.getPollData(pollData.getQuestion(), pollData.getOptions().toArray(new String[0]), pollData.getCorrectIndex());

                    JPanel wrapper = new JPanel(new BorderLayout());
                    wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    wrapper.add(userPollPanel, BorderLayout.CENTER);

                    Pollpanel.add(wrapper);
                }
                Pollpanel.revalidate();
                Pollpanel.repaint();

                CardLayout cardLayout = (CardLayout) Cardpanel.getLayout();
                cardLayout.show(Cardpanel, "Scrollcard");
            }
        });
    }
    public JPanel getMainpanel()
    {
        return Mainpanel;
    }
    private void sendUserMessage() {
        String userText = textfield.getText().trim();
        if (userText.isEmpty()) return;

        appendToChat("You: " + userText, true);
        textfield.setText("");

        new Thread(() -> {
            String botReply = aiChat.getBotReply(userText);
            SwingUtilities.invokeLater(() -> {
                appendToChat("Bot: " + botReply, false);
                count++;
            });
        }).start();
    }


    private void appendToChat(String message, boolean isUser) {
        StyledDocument doc = TextDisplay.getStyledDocument();

        Style style = TextDisplay.addStyle("style", null);
        StyleConstants.setFontSize(style, 14);
        StyleConstants.setForeground(style, isUser ? Color.GRAY : Color.BLACK);
        StyleConstants.setBold(style, isUser);

        try {
            int start = doc.getLength();
            doc.insertString(start, message + "\n", style);
            SimpleAttributeSet paragraphStyle = new SimpleAttributeSet();
            StyleConstants.setAlignment(paragraphStyle, isUser ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(start, message.length(), paragraphStyle, false);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JScrollBar vBar = scrollpanel.getVerticalScrollBar();
            vBar.setValue(vBar.getMaximum());
        });
    }
    private void loaddashboard()
    {
        Dashboard.removeAll();
        Dashboard.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to NeoAura", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        welcomeLabel.setForeground(Color.white);
        Dashboard.add(welcomeLabel, BorderLayout.NORTH);

        JTextArea statsArea = new JTextArea();
        statsArea.setText("Chats Today:" + count + "\nActive Bots: 5\nLogged-in User: "+User.getEmail());
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statsArea.setBorder(BorderFactory.createTitledBorder("System Stats"));

        JScrollPane statsScrollPane = new JScrollPane(statsArea);
        Dashboard.add(statsScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 210, 0));
        JButton goToChatButton = new JButton("Go to Chat");
        goToChatButton.setBackground(Color.black);
        goToChatButton.setForeground(Color.white);
        JButton goToBotButton = new JButton("Bot Selection");
        goToBotButton.setBackground(Color.black);
        goToBotButton.setForeground(Color.white);
        goToChatButton.setMinimumSize(new Dimension(120, 40));
        goToChatButton.setPreferredSize(new Dimension(120, 40));
        goToBotButton.setMinimumSize(new Dimension(120, 40));
        goToBotButton.setPreferredSize(new Dimension(120, 40));

        goToChatButton.addActionListener(a -> {
            CardLayout layout = (CardLayout) Cardpanel.getLayout();
            layout.show(Cardpanel, "Chat");
        });
        goToBotButton.addActionListener(a -> {
            CardLayout layout = (CardLayout) Cardpanel.getLayout();
            layout.show(Cardpanel, "Botselection");
        });

        buttonPanel.add(goToChatButton);
        buttonPanel.add(goToBotButton);

        Dashboard.add(buttonPanel, BorderLayout.SOUTH);
        Dashboard.revalidate();
        Dashboard.repaint();
    }

    private void createUIComponents() {
        TextDisplay = new WrapTextPane();
        TextDisplay.setEditable(false);
        TextDisplay.setMargin(new Insets(10, 10, 10, 10));
    }
    public class WrapTextPane extends JTextPane {
        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }
    }

}
