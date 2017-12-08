package swingBrowser;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observer;
import java.util.Observable;
import javax.swing.*;
import javax.swing.event.HyperlinkListener;

public class View extends JPanel implements Observer {
    private final JFrame myFrame;
    private final JButton backButton;
    private final JButton forwardButton;
    private final JButton goButton;
    private final JButton historyButton;
    private final JButton closeButton;
    private final JTextField addressBar;
    private final JEditorPane editorPane;
    private final Model model;
    
    private static final int X_SIZE = 1000;  
    private static final int Y_SIZE = 800;
    private static final int HISTORY_X_SIZE = 400;
    private static final int HISTORY_Y_SIZE = 400;
    private static final int ERROR_X_SIZE = 300;
    private static final int ERROR_Y_SIZE = 200;
    private static final int SCROLLPANE_X_SIZE = 900;
    private static final int SCROLLPANE_Y_SIZE = 600;
    
    private static final String initialURL = "http://google.com";

    public View(Model model) {
        this.model = model;
        
        myFrame = new JFrame("ForeFix");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        
        backButton = new JButton("Back");
        forwardButton = new JButton("Forward");
        goButton = new JButton("Go");
        historyButton = new JButton("History");
        closeButton = new JButton("Close");
        addressBar = new JTextField(initialURL, 20);
        
        myFrame.add(this);

        this.add(backButton);
        this.add(forwardButton);
        this.add(addressBar);
        this.add(goButton);
        this.add(historyButton);
        this.add(closeButton);
        
        closeButton.addActionListener(closeListener);
        
        backButton.setEnabled(model.hasPrevious());
        forwardButton.setEnabled(model.hasForward());
        
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(SCROLLPANE_X_SIZE, SCROLLPANE_Y_SIZE));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        
        this.add(editorScrollPane);
        
        myFrame.pack();
        myFrame.setVisible(true); 
    }
    
    private final ActionListener closeListener = (ActionEvent e) -> {
        System.exit(0);
    };
    
    @Override
    public void update(Observable o, Object url) {
        try {
            editorPane.setPage((URL)url);
            addressBar.setText(url.toString());
        } catch (IOException e) {
            System.err.println(e);
            
            displayError("Problem loading page.");
            return;
        }
        
        backButton.setEnabled(model.hasPrevious());
        forwardButton.setEnabled(model.hasForward());
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
    
    public void addGoListener(ActionListener actionListener) {
        goButton.addActionListener(actionListener);
        addressBar.addActionListener(actionListener);
    }
    
    public void addForwardListener(ActionListener actionListener) {
        forwardButton.addActionListener(actionListener);
    }
    
    public void addBackListener(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }
    
    public void addHistoryListener(ActionListener actionListener) {
        historyButton.addActionListener(actionListener);
    }
    
    public void addHyperlinkListener(HyperlinkListener actionListener) {
        editorPane.addHyperlinkListener(actionListener);
    }
    
    public String getAddressBarText() {
        return addressBar.getText();
    }
}
