import data.AppData;
import ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        AppData.loadAllData();
        new LoginFrame().setVisible(true);
    }
}
    