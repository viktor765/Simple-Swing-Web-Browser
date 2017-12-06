package swingBrowser;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Controller {
    private final Model model;
    private final View view;
    
    public Controller (Model model, View view) {
        System.out.println("Hello world!");
        
        this.model = model;
        this.view = view;
        
        view.go.addActionListener(goListener);
        view.back.addActionListener(backListener);
        view.forward.addActionListener(forwardListener);
        view.history.addActionListener(historyListener);
        view.close.addActionListener(closeListener);
    }
    
    private final ActionListener goListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Go pushed.");
        
            JEditorPane editorPane = view.editorPane;
            editorPane.setEditable(false);
            
            java.net.URL helpURL;
            try {
                helpURL = new URL(view.addressBar.getText());
            } catch (MalformedURLException e2) {
                System.out.print("Bad URL");
                return;
            }
            
            if (helpURL != null) {
                try {
                    editorPane.setPage(helpURL);
                } catch (IOException e2) {
                    System.err.println("Attempted to read a bad URL: " + helpURL);
                }
            } else {
                System.err.println("Couldn't find file: TextSamplerDemoHelp.html");
            }
        }

    };
    
    private final ActionListener backListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Back pushed.");
        }
    };
    
    private final ActionListener forwardListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Forward pushed.");
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
