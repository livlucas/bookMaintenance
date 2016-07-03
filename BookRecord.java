/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.bookmaintenance;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author livlucas
 */
public class BookRecord {
    private int id;
    private String code;
    private String title;
    private Double price;
    
    public BookRecord() { }

    public BookRecord(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.code = rs.getString("code");
            this.title = rs.getString("title");
            this.price = rs.getDouble("price");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating BookRecord" + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public void setPrice(String price) throws NumberFormatException {
        price = price.trim();

        if (price.length() == 0) {
            this.price = 0.0;
            return;
        }
        
        //removing $
        if (price.charAt(0) == '$') {
            price = price.substring(1);
        }

        this.price = Double.valueOf(price);
    }
}
