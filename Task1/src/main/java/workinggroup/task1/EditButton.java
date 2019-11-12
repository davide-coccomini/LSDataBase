package workinggroup.task1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Lorenzo
 */
/* Links ActionEvents to buttons in order to delete a book or edit its quantity */
public class EditButton extends Button{
    private int row_Id;
    private UIController controller;
    private int argument;
    
    EditButton(String func, int row_Id, int argument, UIController ctrl, final int type){
        controller = ctrl;
        this.row_Id = row_Id;
        this.argument = argument;
        super.setText(func);
        
        switch(func){
            case "X":
                super.getStyleClass().add("DELETE_BTT");
                super.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    controller.row_Delete(get_Row_Id(),type);
                    }
                });
                break;
            case "+":
            case "-":
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
    public int get_Row_Id(){
        return row_Id;
    }
    public int get_Argument(){
        return argument;
    }
}