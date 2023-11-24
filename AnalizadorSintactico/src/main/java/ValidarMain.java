

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class ValidarMain {

    public static void main(String[] args) {
        Stack pila = new Stack();
        Stack pilaAnterior = new Stack();
        Stack pilaAunMasAnterior = new Stack();
        
    
        Scanner lectura = new Scanner (System.in);
        System.out.println(">>>");
        String info = lectura.nextLine();
        info += " ";
        char c;
        String lexema = "";
        int i = 0;
        int infolength = info.length();
        
        while(pila.isEmpty() == false){
            pila.pop();
        }
        
        for(i=0;i<infolength;i++){
            c = info.charAt(i);
            
            if(c == ' '){
                //la gramatica debe de iniciar con selec, se comprueba ese hecho
                if(pila.isEmpty() == true){
                    if(lexema.equals("select") ){
                        pila.push(lexema);
                        lexema = "";
                    }else{
                        System.out.print("\nLa linea esta mal escrita, debe iniciar con un select");
                    }
                }
                
                //pain
                //System.out.print("\npain:"+lexema);
                if(lexema.equals("*")){
                    //System.out.print("\n*");
                    pila.push(lexema);
                    lexema = "";
                }else if(lexema.equals("from")){
                     //System.out.print("\nfrom");
                    pila.push(lexema);
                    lexema = "";
                }else if(lexema.equals("distinct")){
                     //System.out.print("\ndistinct");
                    pila.push(lexema);
                    lexema = "";
                }else if(lexema.equals("id")){
                    pila.push(lexema);
                    //System.out.print("\nPush id");
                    lexema = "";
                }else if(lexema.equals(".")){
                    //System.out.print("\n.");
                    pila.push(lexema);
                    lexema = "";
                }
                
                
                //reducciones
                if(pila.peek().equals("*")){
                    pila.pop();
                    //System.out.print("\nReduccion pop: "+pila.pop());
                    pila.push("P");
                }
                
                
                
            }
            
            
            //final del if del espacio en blanco
            if(c != ' '){lexema += c;}
            
            if(c == ',' && lexema.length() > 1){
                    String[] lexemaSeparado = lexema.split(",");
                    
                    pila.push(lexemaSeparado[0]);
                    pila.push(",");
                    //System.out.print("\nLexema push ,");
                    lexema = "";
            }else if(c == ','){
                pila.push(",");
                lexema = "";
            }
            //System.out.print("\nfinal pain:"+lexema);
        }
        
        //System.out.println("\n"+lexema);
        while(pila.isEmpty() == false){
            System.out.print("\nPop: "+pila.pop());
        }
    }
    
}
