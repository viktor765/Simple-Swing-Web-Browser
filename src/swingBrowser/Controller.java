package swingBrowser;

import java.awt.event.*;
import java.net.MalformedURLException;
import javax.swing.event.*;

public class Controller {
    private final Model model;
    private final View view;
    
    private static final String initialURLString = "https://google.com";
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        
        view.addGoListener((ActionEvent e) -> go(view.getAddressBarText()));
        view.addBackListener((ActionEvent e) -> model.setPrevious());
        view.addForwardListener((ActionEvent e) -> model.setForward());
        view.addHistoryListener((ActionEvent e) -> {
            view.displayHistory().addHyperlinkListener(
                    (HyperlinkEvent e2) -> hyperlinkUpdate(e2));
        });
        view.addEditorPaneHyperlinkListener((HyperlinkEvent e) -> hyperlinkUpdate(e));
        
        go(initialURLString);
    }
    
    private void go(String urlString) {
        try {
            model.setNewCurrentURL(urlString);
        } catch (MalformedURLException e2) {
            System.err.println(e2);
            view.displayError("Bad URL.");
        }
    }
    
    private void hyperlinkUpdate(HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            go(e.getURL().toString());
        }
    }
}
