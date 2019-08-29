/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.ArrayList;

/**
 *
 * @author abrah
 * @param <T>
 */
public class Stack<T> {
    
    private final ArrayList <T> al = new ArrayList<>();
    private final ArrayList<T> la = new ArrayList<>();
   private int top = -1;
   private int doubleTop = -1;

   void push(T elem){
      
      al.add(++top, elem);
   }

   T pop(){
      if(al.size() > 0){
         return al.get(top--);
      }
      return null;
   }
   T peek(){
      if(al.size() > 0){
         return al.get(top);
      }
      return null;
   }
    
   void adds(T elem)
   {
       la.add(++doubleTop, elem);
   }
    
    
    
}
