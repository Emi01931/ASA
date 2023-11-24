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

        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Se encontraron errores");
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

        System.out.print("\ni1: Pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        
        if(preanalisis.tipo == TipoToken.DISTINCT){
            match(TipoToken.DISTINCT);
            i12();
        }
        else if (preanalisis.tipo == TipoToken.ASTERISCO) {
            match(TipoToken.ASTERISCO);
            i2();
            
        }else if(preanalisis.tipo == TipoToken.IDENTIFICADOR){
            
        }else if(pila.peek().equals("D")){
            match(TipoToken.FROM);
            i11();
        }
        else{
            hayErrores = true;
            System.out.println("Se esperaba 'distinct' or '*' or 'identificador'");
        }
    }
    
    private void i2(){
        System.out.print("\ni2: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
        pila.pop();
        pila.push("D");
        i1();
    }
    
    private void i11(){
        System.out.print("\ni11: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
    }
    
    private void i12(){
        System.out.print("\ni12: pila: "+pila.peek()+"\tpreanalisis: "+preanalisis.lexema);
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
