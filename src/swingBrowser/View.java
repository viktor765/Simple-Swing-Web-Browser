package swingBrowser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
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
    private static final int HISTORY_X_SIZE = 400;
    private static final int HISTORY_Y_SIZE = 400;
    private static final int ERROR_X_SIZE = 300;
    private static final int ERROR_Y_SIZE = 200;

    public View(Model model) {
        this.model = model;
        
        myFrame = new JFrame("ForeFix");
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
        
        back.setEnabled(model.hasPrevious());
        forward.setEnabled(model.hasForward());
        
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
            
            displayError("Problem loading page.");
            return;
        }
        
        back.setEnabled(model.hasPrevious());
        forward.setEnabled(model.hasForward());
    }
    
    public void displayError(String errorString) {
        JFrame errorFrame = new JFrame();
        errorFrame.setLayout(new GridBagLayout());
        errorFrame.setPreferredSize(new Dimension(ERROR_X_SIZE, ERROR_Y_SIZE));

        JTextArea textArea = new JTextArea(25, 50);
        textArea.append(errorString);
        textArea.setEditable(false);
        errorFrame.add(textArea);

        JButton errorCloseButton = new JButton("Close");
        errorFrame.add(errorCloseButton);

        errorCloseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.dispose();
            }
        });

        errorFrame.pack();
        errorFrame.setVisible(true);
    }
    
    public JEditorPane displayHistory() {
        JFrame historyFrame = new JFrame("History");
        historyFrame.setPreferredSize(new Dimension(HISTORY_X_SIZE, HISTORY_Y_SIZE));
        
        JEditorPane pane = new JEditorPane();
        
        JScrollPane editorScrollPane = new JScrollPane(pane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(600, 400));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        
        pane.setContentType("text/html");
        
        pane.setEditable(false);

        pane.setText(model.getHistoryHTML());
        
        historyFrame.add(editorScrollPane);     
        historyFrame.pack();
        historyFrame.setVisible(true);
        
        return pane;
    }
}
