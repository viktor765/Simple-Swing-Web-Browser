package swingBrowser;

import java.awt.event.*;
import java.net.MalformedURLException;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Controller {
    private final Model model;
    private final View view;
    
    public Controller (Model model, View view) {
        System.out.println("Hello world!");
        
        this.model = model;
        this.view = view;
        
        view.go.addActionListener(goListener);
        view.addressBar.addActionListener(goListener);
        view.back.addActionListener(backListener);
        view.forward.addActionListener(forwardListener);
        view.history.addActionListener(historyListener);
        view.close.addActionListener(closeListener);
        
        view.editorPane.addHyperlinkListener(linkClickListener);
        
        model.addObserver(view);
    }
    
    private final ActionListener goListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.setNewCurrentURL(view.addressBar.getText());
            } catch (MalformedURLException e2) {
                view.displayError("Bad URL.");
            }
        }
    };
    
    private final HyperlinkListener linkClickListener =  new HyperlinkListener() {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {                
                model.setNewCurrentURL(e.getURL());
            }
        }
    };
    
    private final ActionListener backListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setPrevious();
        }
    };
    
    private final ActionListener forwardListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setForward();
        }
    };
    
    private final ActionListener historyListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JEditorPane historyPane = view.displayHistory();
            
            historyPane.addHyperlinkListener(linkClickListener);
        }
    };
    
    private final ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
}
