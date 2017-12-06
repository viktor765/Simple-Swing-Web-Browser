package swingBrowser;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observer;
import java.util.Observable;
import javax.swing.*;

public class View extends JPanel implements Observer {
    public final JFrame myFrame;
    public final JButton back;
    public final JButton forward;
    public final JButton go;
    public final JButton history;
    public final JButton close;
    public final JTextField addressBar;
    public final JEditorPane editorPane;
    private final Model model;
    
    private final String initialURL = "http://google.com";
    
    private static final int X_SIZE = 700;  
    private static final int Y_SIZE = 500;

    public View(Model model) {
        this.model = model;
        
        myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        
        back = new JButton("Back");
        forward = new JButton("Forward");
        go = new JButton("Go");
        history = new JButton("History");
        close = new JButton("Close");
        addressBar = new JTextField(initialURL, 20);
        
        myFrame.add(this);

        this.add(back);
        this.add(forward);
        this.add(addressBar);
        this.add(go);
        this.add(history);
        this.add(close);
        
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(600, 400));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        
        this.add(editorScrollPane);
        
        myFrame.pack();
        myFrame.setVisible(true); 
    }
    
    @Override
    public void update(Observable o, Object url) {
        System.out.println("Updated.");
        
        try {
            editorPane.setPage((URL)url);
            addressBar.setText(url.toString());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
