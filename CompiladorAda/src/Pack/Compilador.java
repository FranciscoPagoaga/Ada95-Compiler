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
            String texto="procedure Hello is Procedure nombre (ID: Integer); function nombre (ID: Integer) return Integer;  PMT: Integer; Function funcion (prueba: out Boolean) return Integer is PMT: Integer; begin Put(1); prueba:= prueba(12); end funcion; procedure prueba (I:Integer) is PMT: Integer; begin Put(1); end prueba;  begin if 1<2 then Put(1); elsif 1<2 or 1<3 and prueba>1 then Put(1); elsif 1<2 then Put(1); else Put(1); loop Put(1); exit when 1<2; end loop; end if; for variable 1..2  loop Put(1); exit when 1<2; end loop;     end Hello; ";
            System.out.println("iniciando anal0isis sintactico..\n");
            Lexer scan = new Lexer(new BufferedReader( new StringReader(texto)));
            parser par = new parser(scan);
            par.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}