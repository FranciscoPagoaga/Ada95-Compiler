package Pack;

public class Scope {
    private static int count=0;
    
    public static String getNewScope(){
     return String.format("s%d", count++);
    }
    
    public static void resetCount() {
        Scope.count = 0;
    }
}
