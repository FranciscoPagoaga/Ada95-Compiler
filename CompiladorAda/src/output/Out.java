
package output;

import java.io.BufferedReader;
import java.io.StringReader;
import Pack.parser;
import Pack.Lexer;



public class Out {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String texto="--hey male";
            System.out.println("iniciando analisis sintactico..\n");
            Lexer scan = new Lexer(new BufferedReader( new StringReader(texto)));
            parser par = new parser(scan);
            par.parse();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
