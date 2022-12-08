package pt.ubi.di.pmd.play2learn_mobile;
import java.sql.Connection;
import java.sql.DriverManager;

public class P2L_DbHelper {
    public Connection databaseLink;

    public Connection getConnection(){
        String dataBaseName = "play2learndb";
        String dataBaseUser = "tiago4";
        String dataBasePassword = "";
        String url= "jdbc:mysql://192.168.1.229/"+dataBaseName; // Mudar para o IP DO PC


        try{
            databaseLink = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
