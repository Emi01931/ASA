import java.util.List;
import java.util.Stack;

public class ASDR implements Parser{

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    private Stack pila = new Stack();
    
    public ASDR(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    @Override
    public boolean parse() {
        Q();

        while(pila.isEmpty() == false){
            System.out.print("\nPop: "+pila.pop());
        }
        
        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("\nConsulta correcta");
            return  true;
        }else {
            System.out.println("\nSe encontraron errores");
        }
        return false;
    }

    // Q -> select D from T
    private void Q(){
        match(TipoToken.SELECT);
        i1();
        //match(TipoToken.FROM);
        //T();
    }

    // D -> distinct P | P
    private void i1(){
        if(hayErrores)
            return;

        System.out.print("\n1: Pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.DISTINCT){
            match(TipoToken.DISTINCT);
            i12();
        }
        else if (preanalisis.tipo == TipoToken.ASTERISCO) {
            match(TipoToken.ASTERISCO);
            i2();
            
        }else if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            i9();
        }else if(pila.peek().equals("D")){
            //match(TipoToken.FROM);
            i11();
        }else if(pila.peek().equals("P")){
            //match(TipoToken.FROM);
            i3();
        }else if(pila.peek().equals("A")){
            //match(TipoToken.FROM);
            i4();
        }else if(pila.peek().equals("A1")){
            //match(TipoToken.FROM);
            i8();
        }
        else{
            hayErrores = true;
            System.out.println("Se esperaba 'distinct' or '*' or 'identificador'");
        }
    }
    
    private void i2(){
        System.out.print("\n2: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        pila.pop();
        pila.push("D");
        i1();
    }
    
    private void i3(){
        System.out.print("\n3: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(pila.peek().equals("P")){
            pila.pop();
            pila.push("D");
            i1();
        }
    }
    
    private void i4(){
        System.out.print("\n4: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(preanalisis.tipo == TipoToken.COMA){
            pila.push(preanalisis.tipo);
            i5();
        }else if(preanalisis.tipo == TipoToken.FROM){
            pila.pop();
            pila.push("P");
            i1();
        }
    }
    
    private void i5(){
        System.out.print("\n5: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            pila.push(preanalisis.tipo);
            i9();
        }else if(pila.peek().equals("A1")){
            i8();
        }
    }
    
    private void i6(){
        System.out.print("\n6: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(pila.peek().equals(TipoToken.IDENTIFICADOR)){
            pila.pop();
            pila.push("A2");
            i9();
        }
    }
    
    private void i7(){
        System.out.print("\n7: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        pila.push("A2");
        i9();
    }
    
    private void i8(){
        System.out.print("\n8: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        String aux = (String) pila.pop();
        if(preanalisis.tipo == TipoToken.FROM){
            if(pila.peek().equals(TipoToken.COMA)){
                pila.pop();
                pila.pop();
                pila.push("A");
                i1();
            }else{
                pila.push("A");
                i1();
            }
        }
    }
    
    private void i9(){
        System.out.print("\n9: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR && pila.peek().equals("A2") == false){
            //match(TipoToken.IDENTIFICADOR);
            i6();
        }else if(preanalisis.tipo == TipoToken.IDENTIFICADOR && pila.peek().equals("A2") == true){
            match(TipoToken.IDENTIFICADOR);
            i10();
        }else if(preanalisis.tipo == TipoToken.FROM){
            i7();
        }
    }
    
    private void i10(){
        System.out.print("\n10: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(preanalisis.tipo == TipoToken.FROM){
            pila.pop();
            pila.pop();
            pila.push("A1");
            i1();
        }
    }
    
    private void i11(){
        System.out.print("\n11: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(preanalisis.tipo == TipoToken.FROM){
            match(TipoToken.FROM);
            i17();
        }
    }
    
    private void i12(){
        System.out.print("\n12: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.ASTERISCO){
            match(TipoToken.ASTERISCO);
            i2();
        }else if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            i9();
        }else if(pila.peek().equals("P")){
            i13();
        }else if(pila.peek().equals("A")){
            i4();
        }else if(pila.peek().equals("A1")){
            i8();
        }
    }
    
    private void i13(){
        System.out.print("\n13: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        if(pila.peek().equals("P")){
            pila.pop();
            pila.pop();
            pila.push("D");
            i1();
        }
    }
    
    private void i14(){
        System.out.print("\ni14: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.COMA){
            match(TipoToken.COMA);
            i15();
        }else if(preanalisis.tipo == TipoToken.EOF){
            //aceptado
        }
    }
    
    private void i15(){
        System.out.print("\n15: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            i19();
        }else if(pila.peek().equals("T1")){
            i16();
        }
    }
    
    private void i16(){
        System.out.print("\n16: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(pila.peek().equals("T1")){
            pila.pop();
            pila.pop();
            i17();
        }
    }
    
    private void i17(){
        System.out.print("\ni17: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.EOF){
            //acc i9();
        }
        
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            match(TipoToken.IDENTIFICADOR);
            i19();
        }
        
        if(pila.peek().equals("T1")){
            i18();
        }
        
        if(pila.peek().equals("T")){
            i14();
        }
    }
    
    private void i18(){
        System.out.print("\n18: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(pila.peek().equals("T1")){
            pila.pop();
            pila.push("T");
            i17();
        }
    }
    
    private void i19(){
        System.out.print("\ni19: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(pila.peek().equals("T2")){
            i20();
        }
        
        if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            //System.out.print("\n\tIf para el i22");
            if(pila.peek().equals("T1") == false){
               //System.out.print("\n\tPara el i22");
               i22();
            }
            
        }
        
        if(preanalisis.tipo == TipoToken.EOF){
            //System.out.print("\n\tIf para el i22, con espacio");
            if(pila.peek().equals("T1") == false){
               System.out.print("\n\tPara el i22, con espacio");
               i22();
            }       
        }
    }
    
    private void i20(){
        System.out.print("\n20: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.EOF && pila.peek().equals("T") == false){
            pila.pop();
            pila.pop();
            pila.push("T1");
            i17();
        }
    }
    
    private void i21(){
        System.out.print("\n21: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.EOF){
            pila.pop();
            pila.push("T2");
            i19();
        }
    }
    
    private void i22(){
        System.out.print("\ni22: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.EOF && pila.peek().equals("T") == false){
            pila.push("T2");
            i19();
        }
        
    }
    

    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            pila.push(preanalisis.tipo);
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error encontrado");
        }

    }

}
