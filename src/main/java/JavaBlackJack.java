import models.app.GameManager;

public class JavaBlackJack {

    public static void main(String[] args) {
        try {
            // load program
            GameManager gameManager = new GameManager();
            gameManager.begin();
        } catch (Exception ex) {
            System.out.print("something went wrong");
        }
    }
}
