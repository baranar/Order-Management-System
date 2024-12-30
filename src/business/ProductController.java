package business;

import core.Helper;
import core.Item;
import dao.ProductDao;
import entity.Customer;
import entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public Product getById(int id){
        return this.productDao.getById(id);
    }

    public ArrayList<Product> filter(String name, String code, Item isStock){
        String query = "SELECT * FROM product";
        ArrayList<String> wherelist = new ArrayList<>();

        if (name.length() > 0){
            wherelist.add("product_name LIKE '%" + name + "%'");
        }

        if (code.length() > 0){
            wherelist.add("product_code LIKE '%" + code + "%'");
        }

        if (isStock != null){
            if (isStock.getKey() == 1){
                wherelist.add("product_stock > 0");
            } else {
                wherelist.add("product_stock <= 0");
            }
        }

        if (wherelist.size() > 0){
            String whereQuery = String.join(" AND ", wherelist);
            query += " WHERE " + whereQuery;
        }
        return this.productDao.query(query);
    }

    public boolean update(Product product){
        if (this.getById(product.getProductId()) == null){
            Helper.showMessage(product.getProductId() + " ID Kayıtlı Ürün bulunamadı");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage(id + " ID kayıtlı ürün bulunamadı");
            return false;
        }
        return this.productDao.delete(id);
    }

    public boolean save(Product product){
        return this.productDao.save(product);
    }

    public ArrayList<Product> findAll(){
        return this.productDao.findAllProducts();
    }
}
