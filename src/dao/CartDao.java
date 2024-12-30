package dao;

import business.ProductController;
import core.DbHelper;
import entity.Cart;
import entity.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartDao {
    private Connection connection;
    private ProductDao productDao;
    private CustomerDao customerDao;

    public CartDao() {
        this.connection = DbHelper.getInstance();
        this.productDao = new ProductDao();
        this.customerDao = new CustomerDao();
    }

    public boolean save(Cart cart){
        String query = "INSERT INTO cart " +
                "(customer_id, product_id, price, date, note)" +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,cart.getCustomerId());
            preparedStatement.setInt(2, cart.getProductId());
            preparedStatement.setInt(3,cart.getPrice());
            preparedStatement.setDate(4, Date.valueOf(cart.getDate()));
            preparedStatement.setString(5, cart.getNote());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Cart> findAll(){
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM cart");
            while (resultSet.next()){
                carts.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carts;
    }

    public Cart match(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getInt("id"));
        cart.setCustomerId(resultSet.getInt("customer_id"));
        cart.setProductId(resultSet.getInt("product_id"));
        cart.setPrice(resultSet.getInt("price"));
        cart.setNote(resultSet.getString("note"));
        cart.setDate(LocalDate.parse(resultSet.getString("date")));

        cart.setCustomer(this.customerDao.getById(cart.getCustomerId()));
        cart.setProduct(this.productDao.getById(cart.getProductId()));
        return cart;
    }
}
