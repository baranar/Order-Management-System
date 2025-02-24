package dao;

import core.DbHelper;
import entity.Customer;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {
    private Connection connection;

    public ProductDao() {
        this.connection = DbHelper.getInstance();
    }

    public ArrayList<Product> query(String query){
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                products.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }


    public Product getById(int id){
        Product product = null;
        String query = "SELECT * FROM product WHERE product_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                product = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public boolean delete(int id){
        String query = "DELETE FROM product WHERE product_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Product product){
        String query = "UPDATE product SET " +
                "product_name = ? , " +
                "product_code = ? , " +
                "product_price = ? , " +
                "product_stock = ? " +
                "WHERE product_id = ? ";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,product.getProductName());
            preparedStatement.setString(2,product.getProductCode());
            preparedStatement.setInt(3,product.getProductPrice());
            preparedStatement.setInt(4,product.getProductStock());
            preparedStatement.setInt(5, product.getProductId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Product product){
        String query = "INSERT INTO product " +
                "(product_name, product_code, product_price, product_stock)" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,product.getProductName());
            preparedStatement.setString(2,product.getProductCode());
            preparedStatement.setInt(3,product.getProductPrice());
            preparedStatement.setInt(4, product.getProductStock());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> findAllProducts(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM product");
            while (resultSet.next()){
                products.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product match(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setProductCode(resultSet.getString("product_code"));
        product.setProductPrice(resultSet.getInt("product_price"));
        product.setProductStock(resultSet.getInt("product_stock"));
        return product;
    }
}
