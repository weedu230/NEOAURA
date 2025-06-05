package neoaura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Adminpoll extends JPanel {
    private JTextField questionField;
    private JTextField[] optionFields;
    private JComboBox<String> correctAnswerDropdown;
    private JButton savePollButton;

    public Adminpoll() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Create Poll"));
        setBackground(Color.WHITE);

        questionField = new JTextField();
        add(new JLabel("Question:"));
        add(questionField);

        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField();
            add(new JLabel("Option " + (i + 1) + ":"));
            add(optionFields[i]);
        }

        correctAnswerDropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
        add(new JLabel("Correct Answer:"));
        add(correctAnswerDropdown);

        savePollButton = new JButton("Save Poll");
        savePollButton.addActionListener(this::savePoll);
        savePollButton.setBackground(new Color(255,210,0));
        add(savePollButton);
    }

    private void savePoll(ActionEvent e) {
        String question = questionField.getText().trim();
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            options[i] = optionFields[i].getText().trim();
        }
        int correctIndex = correctAnswerDropdown.getSelectedIndex();

        if (question.isEmpty() ||
                options[0].isEmpty() || options[1].isEmpty() || options[2].isEmpty() || options[3].isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the question and all options.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Database database = new Database();
        database.addPoll(question, options[0], options[1], options[2], options[3], correctIndex + 1);

        JOptionPane.showMessageDialog(this, "Poll Saved Successfully!");

        questionField.setText("");
        for (JTextField optionField : optionFields) {
            optionField.setText("");
        }
        correctAnswerDropdown.setSelectedIndex(0);
    }

}
