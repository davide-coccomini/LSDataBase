package workinggroup.task1leveldb;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/*
 * @author Lorenzo
 */
/* This class creates a new type of button that will be used to update the quantity of a book or delete it */
public class EditButton extends Button{
    private int row_Id;
    private UIController controller;
    private int argument;
    
    /* Links ActionEvents to buttons in order to allow the user to delete a book or edit its quantity */
    EditButton(String func, int row_Id, int argument, UIController ctrl, final int type){
        controller = ctrl;
        this.row_Id = row_Id;
        this.argument = argument;
        super.setText(func);
        
        switch(func){
            case "X":   //delete book
                super.getStyleClass().add("DELETE_BTT");
                super.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    controller.row_Delete(get_Row_Id(),type);
                    }
                });
                break;
            case "+":   //increment book quantity by 1
            case "-":   //decrement book quantity by 1
                super.getStyleClass().add("SQUARE_BTT");
                super.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    controller.quantity_Edit(get_Row_Id(),get_Argument());
                    }
                });    
                break;
          
            default: 
                break;
        } 
    };
    /* gets the id of the row, that is the same of the object that it displays */
    public int get_Row_Id(){
        return row_Id;
    }
    public int get_Argument(){
        return argument;
    }
}