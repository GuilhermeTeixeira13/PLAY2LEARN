package pt.ubi.di.pmd.play2learn_mobile;
import java.sql.Connection;
import java.sql.DriverManager;

public class P2L_DbHelper {
    public Connection databaseLink;

    public Connection getConnection(){
        String dataBaseName = "sql8586319";
        //String dataBaseName = "play2learndb";

        // String dataBaseUser = "tiago4";
        //String dataBaseUser = "joao2";
        String dataBaseUser = "sql8586319";

        // String url= "jdbc:mysql://192.168.1.71/"+dataBaseName; // Mudar para o IP DO PC
        // String url= "jdbc:mysql://192.168.1.75/"+dataBaseName;
        //String url= "jdbc:mysql://192.168.1.82/"+dataBaseName;
        String url= "jdbc:mysql://sql8.freesqldatabase.com/"+dataBaseName;

        String dataBasePassword = "fP5bY6SyBP";
        //String dataBasePassword = "";
        try{
            databaseLink = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
