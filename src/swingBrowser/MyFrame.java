package swingBrowser;

public class MyFrame {
    public static void main(String[] args) {
        Model model = new Model();
        new Controller(model, new View(model));
    }
}
