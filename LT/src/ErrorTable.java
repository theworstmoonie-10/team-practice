public class ErrorTable {

    static String error[] = {
        "File not found",
        "String not accepte",
        "File location LEXICAL error(s)",
        "DEF missing",
        "BODY missing",
        "{ missing",
        "} missing",
        "; missing",
        "ID missing",
        ", missing",
        "= missing",
        "( missing",
        ") missing",
        "File contain SYNTAX error(s)",
        "Wrong Expression",
        "Undefined ID",
        "can put INT in BYTE or BYTE in INT",
        "Multiple Declaration",
        "Division by zero",
        "File contain SEMANTIC error(s)"
    };

    public static void print() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Error Table: ");
        for (int i = 0; i < error.length; i++) {
            System.out.println(i + " - " + error[i]);
        }
    }

    public static void printError(int code) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Error Code: " + code);
        System.out.println("Message: " + error[code]);
    }
}