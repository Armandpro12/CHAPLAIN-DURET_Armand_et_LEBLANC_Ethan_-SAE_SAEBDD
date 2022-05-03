import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insertBDDGrenada {
    public static void main(String args[]) throws FileNotFoundException
    {

        try
        {
            //étape 1: charger la classe de driver
            Class.forName("org.postgresql.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres","postgres","ethan121203");
            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            String filePath = "C:/Users/ethan/Desktop/CSV/Grenada.csv";
            FileReader fileReader = new FileReader(filePath);

            String line = "";
            final String delimiter = ";";
            BufferedReader reader = new BufferedReader(fileReader);
            int numLigne =0;
            System.out.println("insert into owid_covid_data.pays values ('GRD', 'Granada', 'North America', 113015);");
            //stmt.executeUpdate("insert into owid_covid_data.pays values ('GRD', 'Granada', 'North America', 113015)");
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                String[] token = line.split(delimiter);    // separate every token by comma
                if(line.equals(line)){
                    numLigne++;
                    if(numLigne>1){
                        for(int i = 0; i < token.length; i++){
                            if(token[i].equals("")){
                                token[i] = null;
                            }
                        }
                        //System.out.println(token[0] + " | "+ token[1]+ " | "+ token[2]+ " |"+ token[3] + "|" + token[4] + "|" + token[5] + "|"+ token[6] + "|"+ token[7] + "|"+ token[8] + "|");
                        System.out.println("insert into owid_covid_data.nouveaux_morts values (default, '"+token[3]+"', "+token[8]+", "+token[9] +", '"+token[0]+"');");
                        System.out.println("insert into owid_covid_data.nouveaux_cas values (default, '"+token[3]+"', "+token[5]+", "+token[6]+", "+token[11]+", "+token[12]+", '"+token[0]+"');");
                        System.out.println("insert into owid_covid_data.total_cas values (default, '"+token[3]+"', "+token[4]+", "+token[10]+", '"+token[0]+"');");
                        System.out.println("insert into owid_covid_data.total_morts values (default, '"+token[3]+"', "+token[7]+", "+token[13]+", '"+token[0]+"');");
                        //stmt.executeUpdate("insert into owid_covid_data.nouveaux_morts values (default, '"+token[3]+"', "+token[8]+", "+token[9]+", '"+token[0]+"')");
                        //stmt.executeUpdate("insert into owid_covid_data.nouveaux_cas values (default, '"+token[3]+"', "+token[5]+", "+token[6]+", "+token[11]+", "+token[12]+", '"+token[0]+"')");
                        //stmt.executeUpdate("insert into owid_covid_data.total_cas values (default, '"+token[3]+"', "+token[4]+", "+token[10]+", '"+token[0]+"')");
                        //stmt.executeUpdate("insert into owid_covid_data.total_morts values (default, '"+token[3]+"', "+token[7]+", "+token[13]+", '"+token[0]+"')");
                    }
                    //System.out.println("ligne : " + numLigne);
                }

            }

            //étape 5: fermez l'objet de connexion
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
