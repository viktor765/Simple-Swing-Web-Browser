package swingBrowser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Stack;

public class Model extends Observable {
    private URL currentURL;
    private final Stack<URL> previousStack = new Stack<>();
    private final Stack<URL> forwardStack = new Stack<>();
    
    public void setNewCurrentURL(String urlStr) {
        try {
            setNewCurrentURL(new URL(urlStr));
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
    }
    
    public void setNewCurrentURL(URL url) {
        if (currentURL != null) {
            previousStack.push(currentURL);
        }
        
        setCurrentURL(url);       
        
        forwardStack.clear();
    }
    
    private void setCurrentURL(URL url) {
        currentURL = url;
        
        setChanged();
        notifyObservers(currentURL);
    }
    
    public void setPrevious() {
        forwardStack.push(currentURL);
        
        try {
            setCurrentURL(previousStack.pop());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setForward() {
        previousStack.push(currentURL);
        
        try {
            setCurrentURL(forwardStack.pop());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public Boolean hasPrevious() {
        return !previousStack.empty();
    }
    
    public Boolean hasForward() {
        return !forwardStack.empty();
    }
}
