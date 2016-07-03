/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.bookmaintenance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author livlucas
 */
public class BookDatabase {
    //local db connection
    Connection conn=null;
    public Connection initConnection()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/livlucas/NetBeansProjects/BookMaintenance/BookMaintenance.sqlite");            
            
            return conn;
        }catch(ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error connecting DB: " + e.getMessage());
            return null;
        }
    }
    
    //list of records
    public ArrayList<BookRecord> FetchAll()
    {
        ArrayList<BookRecord> bookRecords = new ArrayList<>();
        
        if (conn != null) {
            try {
                ResultSet rs = conn.prepareStatement("SELECT * FROM BookInformation").executeQuery();
                while (rs.next()) {
                    bookRecords.add(new BookRecord(rs));
                }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Error fetching BookRecords: " + e.getMessage());
            }
        }
        return bookRecords;
    }
    
    public void updateRecord(BookRecord br) {
        if (br.getId() != 0) {
            //update
            try {
                conn.prepareStatement(
                    String.format(
                        "UPDATE BookInformation SET code = \"%s\", title = \"%s\" , price = %.2f WHERE id = %d ",
                        br.getCode(), br.getTitle(), br.getPrice(), br.getId()
                    )
                ).execute();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding new record. " + e.getMessage());
            }
            
        } else {
            //add
            try {
                conn.prepareStatement(
                    String.format(
                        "INSERT INTO BookInformation (code, title, price) VALUES (\"%s\", \"%s\", %.2f)",
                        br.getCode(), br.getTitle(), br.getPrice()
                    )
                ).execute();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding new record. " + e.getMessage());
            }
        }
    }
    
    public void deleteRecord(BookRecord br) {
        try {
            conn.prepareStatement(
                String.format(
                    "DELETE FROM BookInformation WHERE id = %d" ,
                    br.getId()
                )
            ).execute();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting record. " + e.getMessage());
        }
    }
}
