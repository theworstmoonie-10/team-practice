
public class ST_Rec {

    String IDName, IDType;
    int Value;

    ST_Rec(String IDName, String IDType) {
        this.IDName = IDName;
        this.IDType = IDType;
    }

    String GetIDN() {
        return IDName;
    }

    String GetIDT() {
        return IDType;
    }
}
