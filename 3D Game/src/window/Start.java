package window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;

public class Start extends JFrame {public static void main(String[] args) {SwingUtilities.invokeLater(Start::new);}
    Start() {
        super("Title");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        add(new Screen(this));
        setVisible(true);
    }
}
