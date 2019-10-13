/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workinggroup.task0;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Lorenzo
 */
public class EditButton extends Button{
    private int row_Id;
    private Task0Controller controller;
    private int argument;
    EditButton(String func,int row_Id,int argument,Task0Controller ctrl){
        controller = ctrl;
        this.row_Id=row_Id;
        this.argument=argument;
        super.setText(func);
        
        switch(func){
            case "X":
                super.getStyleClass().add("DELETE_BTT");
                super.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    controller.row_Delete(get_Row_Id());
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