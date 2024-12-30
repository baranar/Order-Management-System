package view;

import business.BasketController;
import business.CartController;
import business.CustomerController;
import business.ProductController;
import core.Helper;
import core.Item;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardUI extends JFrame{
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JPanel pnl_customerFilter;
    private JTextField fld_f_customerName;
    private JComboBox<Customer.TYPE> cmb_f_customerType;
    private JButton btn_customer_filter;
    private JButton btn_customerFilter_reset;
    private JButton btn_customer_new;
    private JLabel lbl_filterCustomerName;
    private JLabel lbl_filterCustomerType;
    private JPanel pnl_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_filter_reset;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private JPanel pnl_basket;
    private JPanel pnl_basket_top;
    private JScrollPane scrl_basket;
    private JComboBox<Item> cmb_Basket_customer;
    private JButton btn_basket_reset;
    private JButton btn_basket_new;
    private JLabel lbl_basket_price;
    private JLabel lbl_basket_count;
    private JTable tbl_basket;
    private JPanel pnl_cart;
    private JTable tbl_cart;
    private JScrollPane scrl_cart;
    private User user;
    private Product product;
    private CustomerController customerController;
    private ProductController productController;
    private CartController cartController;
    private BasketController basketController;
    private DefaultTableModel tableModelCustomers = new DefaultTableModel();
    private DefaultTableModel tableModelProducts = new DefaultTableModel();
    private DefaultTableModel tableModelBasket = new DefaultTableModel();
    private DefaultTableModel tableModelCart = new DefaultTableModel();
    private JPopupMenu popupMenu_customer = new JPopupMenu();
    private JPopupMenu popupMenu_product = new JPopupMenu();


    public DashboardUI(User user){
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();
        this.cartController = new CartController();

        if (user ==null){
            Helper.showMessage("error");
        }

        this.add(container);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.lbl_welcome.setText("Hoşgeldin: " + this.user.getUserName());

        //Button for logout
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginUI loginUI = new LoginUI();
            }
        });

        //Customer TAB
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customerType.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customerType.setSelectedItem(null);

        //Product TAB
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();
        this.cmb_f_product_stock.addItem(new Item(1, "Stokta Var"));
        this.cmb_f_product_stock.addItem(new Item(2, "Stokta Yok"));
        this.cmb_f_product_stock.setSelectedItem(null);     //Default: null

        //Basket TAB
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();

        //Cart TAB
        loadCartTable();
    }
    ////////////Cart Methods
    private void loadCartTable(){
        Object[] columnCard = {"ID", "Müşteri Adı", "Ürün Adı" ,"Ürün Fiyatı", "Sipariş Tarihi", "Sipariş Notu"};
        ArrayList<Cart> carts = this.cartController.findAll();

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_cart.getModel();
        clearModel.setRowCount(0);

        this.tableModelCart.setColumnIdentifiers(columnCard);

        for (Cart cart: carts){
            Object[] rowObject = {
                    cart.getId(),
                    cart.getCustomer().getCustomerName(),
                    cart.getProduct().getProductName(),
                    cart.getProduct().getProductPrice(),
                    cart.getDate(),
                    cart.getNote()
            };
            this.tableModelCart.addRow(rowObject);
        }
        this.tbl_cart.setModel(tableModelCart);
        this.tbl_cart.getTableHeader().setReorderingAllowed(false);
        this.tbl_cart.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_cart.setEnabled(false);
    }

    ////////////Basket Methods
    private void loadBasketCustomerCombo(){
        ArrayList<Customer> customers = this.customerController.findAll();
        this.cmb_Basket_customer.removeAllItems();      //First, delete all items. I dont want to duplication
        for (Customer customer: customers){
            int comboKey = customer.getCustomerId();
            String comboValue = customer.getCustomerName();
            this.cmb_Basket_customer.addItem(new Item(comboKey, comboValue));
        }
        this.cmb_Basket_customer.setSelectedItem(null);     //Default value: Null
    }

    private void loadBasketTable(){
        Object[] columnBasket = {"ID", "Ürün Adı", "Ürün Kodu", "Ürün Fiyatı", "Stok Adedi"};
        ArrayList<Basket> baskets = this.basketController.findAll();

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_basket.getModel();
        clearModel.setRowCount(0);

        this.tableModelBasket.setColumnIdentifiers(columnBasket);
        int totalPrice = 0;

        for (Basket basket: baskets){
            Object[] rowObject = {
                    basket.getProduct().getProductId(),
                    basket.getProduct().getProductName(),
                    basket.getProduct().getProductCode(),
                    basket.getProduct().getProductPrice(),
                    basket.getProduct().getProductStock()
            };
            this.tableModelBasket.addRow(rowObject);
            totalPrice += basket.getProduct().getProductPrice();
        }
        this.lbl_basket_price.setText(totalPrice + " TL");      //total price
        this.lbl_basket_count.setText(baskets.size() + " Adet");

        this.tbl_basket.setModel(tableModelBasket);
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_basket.setEnabled(false);
    }

    private void loadBasketButtonEvent(){
        this.btn_basket_reset.addActionListener(e -> {
            if (this.basketController.clear()){
                Helper.showMessage("done");
                loadBasketTable();
            } else {
                Helper.showMessage("error");
            }
        });

        this.btn_basket_new.addActionListener(e -> {
            //I want to select a customer for the basket
            Item selectedCustomer = (Item)this.cmb_Basket_customer.getSelectedItem();
            if (selectedCustomer == null){
                Helper.showMessage("Lütfen Bir Müşteri Seçiniz!");
            } else {
                Customer customer = this.customerController.getById(selectedCustomer.getKey());     //ID was in the Key
                ArrayList<Basket> baskets = this.basketController.findAll();
                if (customer.getCustomerId() == 0){
                    Helper.showMessage("Böyle Bir Müşteri Bulunamadı!");
                } else if(baskets.size() == 0){
                    Helper.showMessage("Lütfen Sepete Ürün Ekleyiniz!");
                } else {
                    CartUI cartUI = new CartUI(customer);   //open CartUI
                    cartUI.addWindowListener(new WindowAdapter() {  //CartUI kapatıldığı zaman
                        @Override
                        public void windowClosed(WindowEvent e) {
                            loadBasketTable();      //when i close to cartUI, i should refresh the table
                            loadCartTable();
                            loadProductTable(null);
                        }
                    });
                }
            }
        });
    }

    ////////////Product Methods
    private void loadProductPopupMenu(){
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.popupMenu_product.add("Sepete ekle").addActionListener(e -> {
            int selectedId = (int) tbl_product.getValueAt(tbl_product.getSelectedRow(), 0);
            //check the product. Is Stock empty?
            Product basketProduct = this.productController.getById(selectedId);
            if (basketProduct.getProductStock() <= 0){
                Helper.showMessage("Bu ürün stokta bulunmamaktadır!");
            } else {
                Basket basket = new Basket(basketProduct.getProductId());
                if (this.basketController.save(basket)){
                    Helper.showMessage("done");
                    loadBasketTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        this.popupMenu_product.add("Güncelle").addActionListener(e -> {
            int selectedId = (int) tbl_product.getValueAt(tbl_product.getSelectedRow(), 0);
            Product editedProduct = this.productController.getById(selectedId);
            ProductUI productUI = new ProductUI(editedProduct);

            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });

        this.popupMenu_product.add("Sil").addActionListener(e -> {
            int selectedId = (int) tbl_product.getValueAt(tbl_product.getSelectedRow(), 0);
            if (Helper.isConfirm("sure?")){
                if (this.productController.delete(selectedId)){
                    Helper.showMessage("done");
                    loadProductTable(null);
                    loadBasketTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_product.setComponentPopupMenu(this.popupMenu_product);
    }

    private void loadProductTable(ArrayList<Product> products){
        Object[] columnProduct = {"ID", "Ürün Adı", "Ürün Kodu", "Ürün Fiyatı", "Stok Adedi"};
        if (products == null){
            products = this.productController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tableModelProducts.setColumnIdentifiers(columnProduct);
        for (Product product: products){
            Object[] rowObject = {
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductCode(),
                    product.getProductPrice(),
                    product.getProductStock()
            };
            this.tableModelProducts.addRow(rowObject);
        }
        this.tbl_product.setModel(tableModelProducts);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(false);
    }

    private void loadProductButtonEvent(){
        this.btn_product_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //product nullsa yeni bir nesne oluşturacak.
                if (product == null) {
                    product = new Product();
                }
                //Bu işlem bizim için yeni bir ekran açacak. --> ProductUI.Form
                ProductUI productUI = new ProductUI(product);     //ProductUI ekranını açıyorum.
                productUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {       //Pencere kapanınca gerçekleşmesini istediğim şeyler
                        loadProductTable(null);
                    }
                });
            }
        });

        this.btn_product_filter.addActionListener(e -> {
            //Filtreleme kodlarımı yazıyorum
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
        });

        this.btn_product_filter_reset.addActionListener(e -> {
            this.fld_f_product_name.setText(null);
            this.fld_f_product_code.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
            loadProductTable(null);
        });
    }


    ////////////Customer Methods
    //Button Event
    private void loadCustomerButtonEvent(){
        this.btn_customer_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerUI customerUI = new CustomerUI(new Customer());
                customerUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadCustomerTable(null);
                        loadBasketCustomerCombo();
                    }
                });
            }
        });

        this.btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filter(
                    this.fld_f_customerName.getText(),
                    (Customer.TYPE) this.cmb_f_customerType.getSelectedItem()
            );
            loadCustomerTable(filteredCustomers);
        });

        //Reset filter
        this.btn_customerFilter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_f_customerName.setText(null);
            this.cmb_f_customerType.setSelectedItem(null);
        });
    }

    //Pop-Up menu
    private void loadCustomerPopupMenu(){
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());            //Mouse location
                tbl_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.popupMenu_customer.add("Güncelle").addActionListener(e -> {
            int selectedId = (int) tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0);
            Customer editedCustomer = this.customerController.getById(selectedId);
            CustomerUI customerUI = new CustomerUI(editedCustomer);

            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });
        });

        this.popupMenu_customer.add("Sil").addActionListener(e -> {
            int selectedId = (int) tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0);
            if (Helper.isConfirm("sure?")){
                if (this.customerController.delete(selectedId)){
                    Helper.showMessage("done");
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_customer.setComponentPopupMenu(this.popupMenu_customer);

    }

    //The method where we set the model of our table and refresh our table
    private void loadCustomerTable(ArrayList<Customer> customers){
        //Column name
        Object[] columnCustomer = {"ID", "Müşteri Adı", "Tipi", "Telefon", "E-posta", "Adres"};

        //If the parameter is null, fetch all customers (in Helper method)
        if (customers == null){
            customers = this.customerController.findAll();
        }

        /* It will overwrite the table every time it refreshes. The data will start repeating.
        To prevent this, we need to reset the table before processing the table model.  */
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.tableModelCustomers.setColumnIdentifiers(columnCustomer);

        for (Customer customer: customers){
            Object[] rowObject = {
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getType(),
                    customer.getCustomerPhone(),
                    customer.getCustomerMail(),
                    customer.getCustomerAdress()
            };
            this.tableModelCustomers.addRow(rowObject);
        }
        this.tbl_customer.setModel(tableModelCustomers);

        //table settings
        this.tbl_customer.getTableHeader().setReorderingAllowed(false); // Sütunların kaymasını engelleyen bir işlem
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);    //İndexin max genişliği 50 olsun
        this.tbl_customer.setEnabled(false);        //Düzenlenebilir olma ayarını kapattım.
    }
}
