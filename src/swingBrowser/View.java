package swingBrowser;

import javax.swing.*;

public class View extends JPanel {

    public final JButton back;
    public final JButton forward;
    public final JButton go;
    public final JButton history;
    public final JButton close;
    protected JTextField addressBar;

    public View(Model model) {
        back = new JButton();
        forward = new JButton();
        go = new JButton();
        history = new JButton();
        close = new JButton();
        addressBar = new JTextField();
    }
}
