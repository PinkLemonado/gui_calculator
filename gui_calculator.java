package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class calculator_demo extends KeyAdapter {
    JFrame frame;
    JPanel panel;
    JLabel text;
    // add variables...
    BigDecimal left = null, right = null, num = BigDecimal.ZERO;
    boolean eq = false, numDone = false;
    char op = 'N';

    public static void main(String[] args) {
        new calculator_demo();
    }

    public calculator_demo() {
    	//GUI and keyEventListener
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) {
        }

        frame = new JFrame("107502506"); // your student ID
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setSize(280, 200);
        frame.addKeyListener(this);

        GridBagConstraints gbc;
        JButton btn;
        String btnText[] = {
            "7",  "8", "9", "*",
            "4",  "5", "6", "-",
            "1",  "2", "3", "+",
            "0",  "=", "C", "/"
        };

        panel = new JPanel(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 2;
        gbc.insets = new Insets(0,3,0,12);
        gbc.fill = GridBagConstraints.BOTH;
        text = new JLabel("0", JLabel.RIGHT);
        panel.add(text, gbc);

        JPanel innerPanel = new JPanel(new GridLayout(5, 4, 3, 3));
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                int k=i*4+j;
                btn = new JButton(btnText[k]);
                btn.setFocusable(false);      
                innerPanel.add(btn);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 6;
        gbc.insets = new Insets(2,2,2,2);
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(innerPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            if (numDone) {
                num = new BigDecimal(text.getText() + e.getKeyChar());
                text.setText(num.toPlainString());
            }
            else {
            	num = new BigDecimal(e.getKeyChar() - '0');
                text.setText(num.toPlainString());
                numDone = true;
            }
        }
        else if (e.getKeyChar() == '+' || e.getKeyChar() == '-' || e.getKeyChar() == '*' || e.getKeyChar() == '/') {
            if (left == null) {
                left = num;
            }
            else if (op != 'N' && right == null && numDone) {
            	calculate(left, num);
                left = num;
            }
            else if (eq && right != null) {
                left = num;
                right = null;
                eq = false;
            }
            else if (eq) {
                eq = false;
            }
            numDone = false;
            op = e.getKeyChar();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            numDone = false;
            if (left == null) {
                left = num;
                eq = true;
            }
            else if (op != 'N' && right == null) {
                right = num;
                eq = true;
                calculate(left, right);
            }
            else if (eq && right != null) {
                left = num;
                calculate(left, right);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            left = null;
            right = null;
            eq = false;
            numDone = false;
            op = 'N';
            num = BigDecimal.ZERO;
            text.setText(num.toPlainString());
        }
    }

    public void calculate(BigDecimal a, BigDecimal b) {
        try {
        	switch (op) {
        	case '+':
        		num = a.add(b).stripTrailingZeros();
        		break;
        	case '-':
        		num = a.subtract(b).stripTrailingZeros();
        		break;
        	case '*':
        		num = a.multiply(b).stripTrailingZeros();
        		break;
        	case '/':
        		num = a.divide(b,6,RoundingMode.HALF_UP).stripTrailingZeros();
        		break;
        	}
            text.setText(num.toPlainString());
        }
        catch (Exception e) {
        	text.setText("Divide by 0!");
        }
    }
}


