package dao;

import core.DbHelper;
import entity.Basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasketDao {
    private Connection connection;
    private ProductDao productDao;

    public BasketDao() {
        this.connection = DbHelper.getInstance();
        this.productDao = new ProductDao();
    }

    public ArrayList<Basket> findAllBaskets(){
        ArrayList<Basket> baskets = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM basket");
            while (resultSet.next()){
                baskets.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baskets;
    }

    public boolean clear(){
        String query = "DELETE FROM basket";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //save the product to basket
    public boolean save(Basket basket){
        String query = "INSERT INTO basket (product_id) VALUES (?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,basket.getProductId());

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Basket match(ResultSet resultSet) throws SQLException {
        Basket basket = new Basket();
        basket.setId(resultSet.getInt("id"));
        basket.setProductId(resultSet.getInt("product_id"));
        basket.setProduct(this.productDao.getById(resultSet.getInt("product_id")));
        return basket;
    }
}
