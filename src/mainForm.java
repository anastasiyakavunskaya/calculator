import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class mainForm extends JFrame {
    private JPanel rootPanel;
    private JTextField expressionField;
    private JButton runButton;
    private JLabel enterLabel;
    private JLabel resultLabel;

    public mainForm() {
        setContentPane(rootPanel);
        setVisible(true);
        setSize(500,300);
        setResizable(false);

        enterLabel.setText("Введите выражение для подсчета");
        runButton.setText("Вычислить");
        resultLabel.setText("");
        ActionListener actionListener = new TestActionListener();
        runButton.addActionListener(actionListener);
        expressionField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9'))&&(c !='+')&&(c !='-')&&(c !='*')&&(c !='/')&&(c != KeyEvent.VK_BACK_SPACE)&&(c !='(')&&(c !=')')) {
                    e.consume();  // ignore event
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        new mainForm();
    }
    public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result;
            if(!expressionField.getText().isEmpty()) {
                result ="Результат вычисления: " + main.calculate(expressionField.getText());
            }
            else
              result = "Вы не ввели выражение!";
            resultLabel.setText(result);
        }
    }

}


