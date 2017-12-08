package swingBrowser;

import java.awt.event.*;
import java.net.MalformedURLException;
import javax.swing.JEditorPane;
import javax.swing.event.*;

public class Controller {
    private final Model model;
    private final View view;
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        
        view.addGoListener(goListener);
        view.addBackListener(backListener);
        view.addForwardListener(forwardListener);
        view.addHistoryListener(historyListener);
        view.addHyperlinkListener(linkClickListener);
        
        go();
    }
    
    private void go() {
        try {
            model.setNewCurrentURL(view.getAddressBarText());
        } catch (MalformedURLException e2) {
            view.displayError("Bad URL.");
        }
    }
    
    private final ActionListener goListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            go();
        }
    };
    
    private final HyperlinkListener linkClickListener =  new HyperlinkListener() {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    model.setNewCurrentURL(e.getURL().toString());
                } catch (MalformedURLException e2) {
                    view.displayError("Bad URL.");
                }
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
}
