public class ST_Table {
int STSize=0;
int index;
public ST_Rec [] ST=new ST_Rec[10];  

public void insert_ST(String IDName,String IDType){
   ST[STSize]=new ST_Rec(IDName, IDType);
   STSize ++;
   
}
String getN(int index){
    return ST[index].IDName;
}
String getT(int index){
    return ST[index].IDType;
}
boolean member(String ID){
    for (int i = 0; i <index; i++){
          if (!ID.equals(ST[i]))   
         
    }
}


}