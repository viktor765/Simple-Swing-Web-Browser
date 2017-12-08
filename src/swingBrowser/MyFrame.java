package swingBrowser;

public class MyFrame {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        
        model.addObserver(view);
        
        new Controller(model, view);
    }
}
