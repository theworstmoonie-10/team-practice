
import java.util.*;
import java.io.*;

public class Lexical {

    public static Record lut[] = new Record[100];
    public static int index = 0;
    public static ErrorTable e = new ErrorTable();

    public static Boolean Is_SP(char ch) {
        if (ch == ',' || ch == ';' || ch == '{' || ch == '}' || ch == '(' || ch == ')') {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean Is_OP(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=') {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean Is_RW(String tk) {
        if ("DEF".equals(tk) || "BODY".equals(tk) || "int".equals(tk)
                || "Byte".equals(tk) || "print".equals(tk)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Is_Dig(String line) {

        if (line.length() == 0) {
            return false;
        }

        for (int i = 0; i < line.length(); i++) {
            if (!(line.charAt(i) >= '0' && line.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean Is_ID(String line) {

        if (line.length() == 0) {
            return false;
        }

        if (!((line.charAt(0) >= 'a' && line.charAt(0) <= 'z')
                || (line.charAt(0) >= 'A' && line.charAt(0) <= 'Z'))) {
            return false;
        }

        for (int i = 1; i < line.length(); i++) {
            if (!((line.charAt(i) >= 'a' && line.charAt(i) <= 'z')
                    || (line.charAt(i) >= 'A' && line.charAt(i) <= 'Z')
                    || (line.charAt(i) >= '0' && line.charAt(i) <= '9'))) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {

        File f = new File("C:\\Users\\Amna\\Desktop\\FirstProg.txt");

        if (!f.exists()) {
            ErrorTable.printError(0);
            return;
        }

        Scanner in = new Scanner(f);
        char ch;
        String line, tk, com;

        while (in.hasNext()) {

            line = in.nextLine();
            tk = "";
            com = "";

            for (int i = 0; i < line.length(); i++) {

                ch = line.charAt(i);

                if ((ch != ' ') && (ch != '"') && !Is_SP(ch) && !Is_OP(ch)) {
                    tk += ch;
                } else if (ch == '"') {
                    com = line.substring(i);
                    System.out.println(com);
                    com = "";
                    break;
                } else {

                    if (tk.length() > 0) {

                        lut[index] = new Record();
                        lut[index].lexem = tk;

                        if (Is_RW(tk)) {
                            lut[index].type = "Reserved Word";
                        } else if (Is_Dig(tk)) {
                            lut[index].type = "Digit";
                        } else if (Is_ID(tk)) {
                            lut[index].type = "ID";
                        } else {
                            lut[index].type = "Unknown";
                        }

                        index++;
                        tk = "";
                    }
                }

                if (Is_SP(ch)) {
                    lut[index] = new Record();
                    lut[index].lexem = String.valueOf(ch);
                    lut[index].type = "Separator";
                    index++;
                } else if (Is_OP(ch)) {
                    lut[index] = new Record();
                    lut[index].lexem = String.valueOf(ch);
                    lut[index].type = "Operator";
                    index++;
                }
            }

            if (tk.length() > 0) {
                lut[index] = new Record();
                lut[index].lexem = tk;

                if (Is_RW(tk)) {
                    lut[index].type = "Reserved Word";
                } else if (Is_Dig(tk)) {
                    lut[index].type = "Digit";
                } else if (Is_ID(tk)) {
                    lut[index].type = "ID";
                } else {
                    lut[index].type = "Unknown";
                }

                index++;
            }
        }
        boolean def = false;
        boolean body = false;
        for (int i = 0; i < index; i++) {
            if (lut[i].lexem.equals("DEF")) {
                def = true;
                break;
            }
            if (lut[i].lexem.equals("BODY")) {
                body = true;
            break;
            }
        }
        if (!def) {
            ErrorTable.printError(3);
        }
        if (!body) {
            ErrorTable.printError(4);
        }

        boolean openC = false;
        boolean closeC = false;

        for (int i = 0; i < index; i++) {

            if (lut[i].lexem.equals("{")) {
                openC = true;
            }

            if (lut[i].lexem.equals("}")) {
                closeC = true;
            }
        }

        if (!openC) {
            ErrorTable.printError(5);
        }

        if (!closeC) {
            ErrorTable.printError(6);
        }
        boolean openR = false;
        boolean closeR = false;

        for (int i = 0; i < index; i++) {

            if (lut[i].lexem.equals("(")) {
                openR = true;
            }

            if (lut[i].lexem.equals(")")) {
                closeR = true;
            }
        }

        if (!openR) {
            ErrorTable.printError(11);
        }

        if (!closeR) {
            ErrorTable.printError(12);
        }

        boolean semicolon = false;

        for (int i = 0; i < index; i++) {

            if (lut[i].lexem.equals(";")) {
                semicolon = true;
                break;

            }
        }

        if (!semicolon) {
            ErrorTable.printError(7);
        }
        for (int i = 0; i < index; i++) {
            System.out.print(lut[i].lexem);
            System.out.print("\t" + lut[i].type);
            System.out.println();
        }

        in.close();
        e.print();
    }
}
