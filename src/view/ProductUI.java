package view;

import business.ProductController;
import core.Helper;
import entity.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductUI extends JFrame {
    private Product product;
    private ProductController productController;
    private JPanel container;
    private JLabel lbl_title;
    private JTextField fld_product_name;
    private JTextField fld_product_code;
    private JTextField fld_product_price;
    private JTextField fld_product_stock;
    private JButton btn_product;
    private JLabel lbl_product_name;
    private JLabel lbl_product_code;
    private JLabel lbl_product_price;
    private JLabel lbl_product_stock;

    public ProductUI(Product product){
        this.product = product;
        this.productController = new ProductController();

        this.add(container);
        this.setTitle("Ürün ekle/Düzenle");
        this.setSize(300,350);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (this.product.getProductId() == 0){
            lbl_title.setText("Ürün Ekle");
        } else {
            lbl_title.setText("Ürün Düzenle");
            this.fld_product_name.setText(this.product.getProductName());
            this.fld_product_code.setText(this.product.getProductCode());
            this.fld_product_price.setText(String.valueOf(this.product.getProductPrice()));
            this.fld_product_stock.setText(String.valueOf(this.product.getProductStock()));
        }

        this.btn_product.addActionListener(e -> {
            JTextField[] checkList = {
                    this.fld_product_name,
                    this.fld_product_code,
                    this.fld_product_price,
                    this.fld_product_stock
            };

            if (Helper.isFieldListEmpty(checkList)){    //are fields empty?
                Helper.showMessage("fill");
            } else {
                this.product.setProductName(this.fld_product_name.getText());
                this.product.setProductCode(this.fld_product_code.getText());
                this.product.setProductPrice(Integer.parseInt(this.fld_product_price.getText()));
                this.product.setProductStock(Integer.parseInt(this.fld_product_stock.getText()));

                boolean result;
                if (this.product.getProductId() == 0){      //if there isn't product, then its a save request
                    result = this.productController.save(this.product);
                } else{
                    result = this.productController.update(this.product);
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
