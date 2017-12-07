package swingBrowser;

import java.awt.event.*;
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
            System.out.println("Go or enter pushed.");
            
            model.setNewCurrentURL(view.addressBar.getText());
        }
    };
    
    private final HyperlinkListener linkClickListener =  new HyperlinkListener() {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                System.out.println("Link clicked.");
                
                model.setNewCurrentURL(e.getURL());
            }
        }
    };
    
    private final ActionListener backListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Back pushed.");
            
            model.setPrevious();
        }
    };
    
    private final ActionListener forwardListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Forward pushed.");
            
            model.setForward();
        }
    };
    
    private final ActionListener historyListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("History pushed.");
        }
    };
    
    private final ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Close pushed.");
            System.exit(0);
        }
    };
}
