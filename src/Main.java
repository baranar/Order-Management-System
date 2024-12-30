import business.UserController;
import core.Helper;
import entity.User;
import view.DashboardUI;
import view.LoginUI;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Helper.setTheme();
        Helper.optionPaneDialogTranslateToTR();

        LoginUI login = new LoginUI();
    }
}