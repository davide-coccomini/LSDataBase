package workinggroup.task1.Obj;

import java.time.LocalDate;

 
/**
 *
 * @author Lorenzo
 */
public class Book{
    private int idBOOK;

    private String title;
    private double price;
    private int numPages;
    private int year;
    private String category;
    private int quantity;
    
    public Book(int ID,String title, double price, int numPages, int year, String category, int quantity){
        this.idBOOK=ID;
        this.title = title;
        this.price = price;
        this.numPages = numPages;
        this.year = year;
        this.category = category;
        this.quantity = quantity;
    }
    
    public int getIdBOOK() {
        return idBOOK;
    }

    public void setIdBOOK(int idBOOK) {
        this.idBOOK = idBOOK;
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

    public int getYear() {
        return year;
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
