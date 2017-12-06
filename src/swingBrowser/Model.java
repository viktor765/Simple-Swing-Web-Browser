package swingBrowser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

public class Model extends Observable {
    private URL currentURL;
    
    public void setCurrentURL(String urlStr) {
        try {
            currentURL = new URL(urlStr);
            setCurrentURL(currentURL);
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
    }
    
    public void setCurrentURL(URL url) {
        this.currentURL = url;
        setChanged();
        notifyObservers(url);
    }
}
