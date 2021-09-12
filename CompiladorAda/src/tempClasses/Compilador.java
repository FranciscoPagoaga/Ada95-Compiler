package tempClasses;



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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}