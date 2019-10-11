/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task0.Obj;

import java.time.LocalDate;

 
/**
 *
 * @author Lorenzo
 */
public class Book{
    private String title;
    private double price;
    private int numPages;
    private LocalDate date;
    private String category;
    private int quantity;
    
    public Book(String title, double price, int numPages, LocalDate date, String category, int quantity){
        this.title = title;
        this.price = price;
        this.numPages = numPages;
        this.date = date;
        this.category = category;
        this.quantity = quantity;
    }
    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getNumPages() {
        return numPages;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
