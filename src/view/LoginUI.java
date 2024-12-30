package view;

import business.UserController;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_mail;
    private JButton btn_login;
    private JPasswordField fld_password;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private UserController userController;

    public LoginUI(){
        this.userController = new UserController();

        //Standart window settings
        this.add(container);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(700,700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.btn_login.addActionListener(e -> {
            // Listens to Login button
            //is fields Empty?
            JTextField[] checkFieldList = {this.fld_password, this.fld_mail };

            //is Email Valid?
            if (!Helper.isEmailValid(this.fld_mail.getText())){
                Helper.showMessage("Geçerli Bir Eposta giriniz!");
            } else if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMessage("fill");
            } else {
                User user = this.userController.findByLogin(this.fld_mail.getText(), this.fld_password.getText());
                if (user == null){
                    Helper.showMessage("Girdiğiniz bilgilerle eşleşen bir kullanıcı yok!");
                } else {
                    this.dispose();
                    DashboardUI dashboardUI = new DashboardUI(user);    //Open DashBoard
                }
            }
        });
    }
}
