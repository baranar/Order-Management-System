package view;

import business.CustomerController;
import core.Helper;
import entity.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI extends JFrame {
    private JPanel container;
    private JTextField fld_customer_name;
    private JComboBox<Customer.TYPE> cmb_customer_type;
    private JTextField fld_customer_phone;
    private JTextField fld_customer_mail;
    private JTextArea fld_customer_adress;
    private JButton btn_customer_save;
    private JLabel lbl_customer_name;
    private JLabel lbl_customer_type;
    private JLabel lbl_customer_phone;
    private JLabel lbl_customer_mail;
    private JLabel lbl_customer_adress;
    private JLabel lbl_title;
    private Customer customer;
    private CustomerController customerController;

    public CustomerUI(Customer customer){

        this.customer = customer;
        this.customerController = new CustomerController();

        this.add(container);
        this.setTitle("Müşteri ekle/Düzenle");
        this.setSize(300,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.cmb_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));

        if (customer.getCustomerId() == 0){
            lbl_title.setText("Müşteri Ekle");
        } else {
            lbl_title.setText("Müşteri Düzenle");
            this.fld_customer_name.setText(this.customer.getCustomerName());
            this.fld_customer_mail.setText(this.customer.getCustomerMail());
            this.fld_customer_phone.setText(this.customer.getCustomerPhone());
            this.fld_customer_adress.setText(this.customer.getCustomerAdress());
            this.cmb_customer_type.getModel().setSelectedItem(this.customer.getType());
        }

        this.btn_customer_save.addActionListener(e ->{
            JTextField[] checkList = {this.fld_customer_name, this.fld_customer_phone};
            if (Helper.isFieldListEmpty(checkList)){
                Helper.showMessage("fill");
            } else if(!Helper.isFieldEmpty(this.fld_customer_mail) && !Helper.isEmailValid(this.fld_customer_mail.getText())) {
                Helper.showMessage("Lütfen geçerli bir e-posta adresi giriniz !");
            } else {
                boolean result = false;
                this.customer.setCustomerName(this.fld_customer_name.getText());
                this.customer.setCustomerPhone(this.fld_customer_phone.getText());
                this.customer.setCustomerMail(this.fld_customer_mail.getText());
                this.customer.setCustomerAdress(this.fld_customer_adress.getText());
                this.customer.setType((Customer.TYPE) this.cmb_customer_type.getSelectedItem());

                if (this.customer.getCustomerId() == 0){
                    result = this.customerController.save(this.customer);
                } else{
                    result = this.customerController.update(customer);
                }
                if (result){
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}
