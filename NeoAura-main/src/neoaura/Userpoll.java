package neoaura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Userpoll extends JPanel {
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup group;
    private JButton submitButton;
    private JLabel resultLabel;
    private int correctIndex;

    public Userpoll() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Answer the Poll"));
        setBackground(Color.WHITE);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        group = new ButtonGroup();
        optionButtons = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            group.add(optionButtons[i]);
            add(optionButtons[i]);
        }

        submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(this::checkAnswer);
        submitButton.setBackground(new Color(255,210,0));
        add(submitButton);

        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(Color.BLUE);
        add(resultLabel);
    }

    public void getPollData(String question, String[] options, int correctIndex) {
        questionLabel.setText("Q: " + question);
        for (int i = 0; i < options.length && i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setSelected(false);
        }
        resultLabel.setText("");
        this.correctIndex = correctIndex;
    }

    private void checkAnswer(ActionEvent e) {
        int selectedIndex = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedIndex = i;
                break;
            }
        }

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an option.");
        } else if (selectedIndex == correctIndex) {
            resultLabel.setText("Correct Answer");
            resultLabel.setForeground(new Color(0, 128, 0));
        } else {
            resultLabel.setText("Wrong Answer");
            resultLabel.setForeground(Color.RED);
        }
    }

}
