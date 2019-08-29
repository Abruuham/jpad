/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
/**
 *
 * @author abrah
 */
public class JPad extends Application {
    
    private Stage stage;
    private TextArea ta = new TextArea();
    private String fName = "Untitled";
    private Label status = new Label("Ready");
    
    
    
    private HBox toolBar(){
        
        HBox box = new HBox();
        String[] btns = {"New","Open","Save","Save As..."};
        
        
        for(int i = 0; i<btns.length;i++)
        {
            
            Button b = new Button(btns[i]);
            
            b.setOnAction(toolBarHandler);
            
            box.getChildren().add(b);
        }
        
        box.setSpacing(5.0);
        box.setStyle("-fx-background-color:#D3D3D3");
        
        
        
        return box;
    }
    
    private MenuBar menuBar(){
    
    Menu fileMenu = new Menu("File");
    MenuItem newMenuItem = new MenuItem("New");
    MenuItem openMenuItem = new MenuItem("Open");
    MenuItem saveMenuItem = new MenuItem("Save");
    MenuItem saveAsMenuItem = new MenuItem("Save As...");
    MenuItem exitMenuItem = new MenuItem("Exit");
    
    newMenuItem.setOnAction(menuHandler);
    openMenuItem.setOnAction(menuHandler);
    saveMenuItem.setOnAction(menuHandler);
    saveAsMenuItem.setOnAction(menuHandler);
    
    exitMenuItem.setOnAction(actionEvent -> Platform.exit());
    
    fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, new SeparatorMenuItem(), exitMenuItem);
        
    
    MenuBar bar = new MenuBar();
    bar.getMenus().add(fileMenu);
    
    
    return bar;
    
    }
    
    
    
    private void handleEvent(String event)
    {
        
        switch(event)
        {
            case "New":
            
                newFile();
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save As...":
                saveFileAs();
                break;
            
        }
        
        
        
    }
    
    EventHandler<ActionEvent> menuHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
          
            MenuItem mi = (MenuItem) event.getSource();
            handleEvent(mi.getText());   
        }     
    };
    EventHandler<ActionEvent> toolBarHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
          
            Button mi = (Button) event.getSource();
            handleEvent(mi.getText());   
        }     
    };
    
    
   private void newFile(){
       
       fName = "Untitled";
       ta.clear();
       
       ta.requestFocus();
       
       
   }
    
    
    private void saveFile()
    {
        
        if("Untitled".equals(fName))
        {
            saveFileAs();
        }
        else
        {
            writeFile();
        }
        
        
    }
    
    private void openFile()
    {
        
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JPad files (*.jpad)", "*.jpad");
        
        fc.getExtensionFilters().add(filter);
        
        File file = fc.showOpenDialog(stage);
        /*
        if( fc == null)
        {
            status.setText("Ready");
            ta.requestFocus();
                   }
        else
        {*/
            fName = file.getPath();
            readFile(fName);
        //}
        
        
        
    }
    
    
    
    private void saveFileAs()
    {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JPad files (*.jpad)", "*.jpad");
        
        fc.getExtensionFilters().add(filter);
        
        File file = fc.showSaveDialog(stage);
        
       /* if( fc.getTitle() == null)
        {
          */
           //fName = file.getPath();
            writeFile(); 
        /*}
        else
        {
            status.setText("Ready");
            ta.requestFocus();
           
        }
        
        */
        
    }
    
    
    private void readFile(String fName)
    {
        
        if(fName == "Untitled")
        {
            ta.clear();
            ta.requestFocus();
        
            
        }
        else
        {
        FileInputStream fs = null;
        String input = "";
   int ch;

   try{
      fs = new FileInputStream(fName);
      while((ch = fs.read()) != -1)
         input+= (char)((ch + 128) % 256); // cast byte to char

      ta.setText(input);
      status.setText("File Read Successfully");
      
      
   //llstage.setTitle("JPad - " + fName);   
   }
   
   
   
   catch(FileNotFoundException e){
      status.setText("Error opening file");
   }
   catch(IOException ie){
      status.setText("Error reading file");
   }
   finally{
      try{    //need a try catch around the file close also
         if(fs != null)
            fs.close(); // close the file
      }
      catch(IOException ie){
         System.out.println(ie.getMessage());
      }
   }
}
        
    }    
    
    
    
    private void writeFile()
    {
        
        FileOutputStream fout = null;
        try{
            String s = "";
            fout = new FileOutputStream(fName);
            s = ta.getText();
            
            for(int i = 0; i<s.length()-1;i++)
            {
                fout.write(s.charAt(i) + 128);
                
            }
            
            status.setText("File Written Successfully");
           //stage.setTitle("JPad - "+ fName);
            
        }
       catch(FileNotFoundException e){
     status.setText("Error opening file");
   }
   catch(IOException ie){
      status.setText("Error reading file");
   }
   finally{
      try{    //need a try catch around the file close also
         if(fout != null)
            fout.close(); // close the file
      }
      catch(IOException ie){
         System.out.println(ie.getMessage());
      }
   }
}
        
    
 
    
    @Override
    public void start(Stage stage) {
        
        
      
       
        
       
        
        BorderPane root = new BorderPane();
        GridPane menuPane = new GridPane();
        
        root.setCenter(ta);
        menuPane.add(menuBar(), 0,0);
        menuPane.add(toolBar(), 1,0);
        root.setTop(menuPane);
        root.setBottom(status);
       
        Scene scene = new Scene(root, 400, 300);
        
        stage.setTitle(fName);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
