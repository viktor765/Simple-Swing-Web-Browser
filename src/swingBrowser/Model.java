package swingBrowser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Model extends Observable {
    private URL currentURL;
    private final Stack<URL> previousStack = new Stack<>();
    private final Stack<URL> forwardStack = new Stack<>();
    private final ArrayList<URL> history = new ArrayList<>();
    
    public void setNewCurrentURL(String urlStr) throws MalformedURLException {
        setNewCurrentURL(new URL(
                urlStr.matches("\\w+\\.\\w+") ? "http://" + urlStr : urlStr));
    }
    
    public void setNewCurrentURL(URL url) {
        if (currentURL != null) {
            previousStack.push(currentURL);
        }
        
        forwardStack.clear();
        setCurrentURL(url);
    }
    
    private void setCurrentURL(URL url) {
        currentURL = url;
        
        setChanged();
        notifyObservers(currentURL);
        
        history.add(currentURL);
    }
    
    public void setPrevious() {
        forwardStack.push(currentURL);
        setCurrentURL(previousStack.pop());
    }
    
    public void setForward() {
        previousStack.push(currentURL);
        setCurrentURL(forwardStack.pop());      
    }
    
    public Boolean hasPrevious() {
        return !previousStack.isEmpty();
    }
    
    public Boolean hasForward() {
        return !forwardStack.isEmpty();
    }
    
    public String getHistoryHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>History></title></head><body><table border=\"1\">");
        
        history.stream().forEach((URL url) -> {
            sb.append("<tr><td>interlinked cell</td><td>")
              .append("<a href=\"")
              .append(url.toString())
              .append("\">")
              .append(url.toString())
              .append("</a></td></tr>");
        });
        
        sb.append("</table></body></html>");
        
        return sb.toString();
    }
}
