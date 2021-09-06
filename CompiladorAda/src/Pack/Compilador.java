package Pack;
import java.io.BufferedReader;
import java.io.StringReader;


public class Compilador {
    
    public static void main(String[] args) {
        run();
    }
    
    private static void run(){
        try {
            String ruta = "src/Pack/"; //ruta donde tenemos los archivos con extension .jflex y .cup
            String opcFlex[] = { ruta + "lexer.flex", "-d", ruta };
            jflex.Main.generate(opcFlex);
            String opcCUP[] = { "-destdir", ruta, "-parser", "parser", ruta + "parser.cup" };
            java_cup.Main.main(opcCUP);
            String texto="LOOP Put(1); End loop;";
            System.out.println("iniciando anal0isis sintactico..\n");
            Lexer scan = new Lexer(new BufferedReader( new StringReader(texto)));
            parser par = new parser(scan);
            par.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}