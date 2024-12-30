package business;

import core.Helper;
import dao.CustomerDao;
import entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();

    //Filter
    public ArrayList<Customer> filter(String name, Customer.TYPE type){
        String query = "SELECT * FROM customer";

        ArrayList<String> wherelist = new ArrayList<>();

        if (name.length() > 0){     //if name is not empty
            wherelist.add("customer_name LIKE '%" + name + "%'");
        }
        if (type != null){          //if type selected
            wherelist.add("customer_type = '" + type + "'");
        }
        if (wherelist.size() > 0){
            String whereQuery = String.join(" AND ", wherelist);
            query += " WHERE " + whereQuery;    //Create my custom query
        }
        return this.customerDao.query(query);
    }

    //get all customers
    public ArrayList<Customer> findAll(){
        return this.customerDao.findAllCustomers();
    }

    public Customer getById(int id){
        return this.customerDao.getById(id);
    }

    public boolean save(Customer customer){
        return this.customerDao.save(customer);
    }

    public boolean update(Customer customer){
        if (this.getById(customer.getCustomerId()) == null){
            Helper.showMessage(customer.getCustomerId() + " ID Kayıtlı Müşteri bulunamadı");
            return false;
        }
        return this.customerDao.update(customer);
    }

    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage(id + " ID kayıtlı müşteri bulunamadı");
            return false;
        }
        return this.customerDao.delete(id);
    }
}