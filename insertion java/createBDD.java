import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class createBDD {
    public static void main(String args[])
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
            ResultSet res = stmt.executeQuery("-- Mise en place du schéma :\n" +
                    "drop schema if exists owid_covid_data cascade;\n" +
                    "create schema owid_covid_data;\n" +
                    "\n" +
                    "-- Création des tables :\n" +
                    "CREATE TABLE owid_covid_data.Pays ( \n" +
                    "iso_CodePays varchar(8) PRIMARY KEY,  -- identifiant du pays\n" +
                    "nomPays varchar(32), -- son nom\n" +
                    "nomContinent varchar(32),   -- nom du continent\n" +
                    "population bigint); --population du payss\n" +
                    "\n" +
                    "CREATE TABLE owid_covid_data.Nouveaux_Morts (\n" +
                    "id_Nouveaux_Morts SERIAL PRIMARY KEY, \n" +
                    "date_Nouveaux_Morts date,  --date concernant les nouveaux morts\n" +
                    "stats_Nouveaux_Morts numeric, --stats des nouveaux morts\n" +
                    "stats_Nouveaux_Morts_Smoothed numeric,  --stats des nouveaux morts smoothed\n" +
                    "iso_CodePays varchar(8));\n" +
                    "ALTER TABLE owid_covid_data.Nouveaux_Morts add constraint fk_Nouveaux_Morts_iso_CodePays FOREIGN KEY(iso_CodePays) REFERENCES owid_covid_data.Pays(iso_CodePays);\n" +
                    "\n" +
                    "CREATE TABLE owid_covid_data.Nouveaux_Cas (\n" +
                    "id_Nouveaux_Cas SERIAL PRIMARY KEY, \n" +
                    "date_Nouveaux_Cas date,  --date concernant les nouveaux morts\n" +
                    "stats_Nouveaux_Cas numeric, --stats des nouveaux morts\n" +
                    "stats_Nouveaux_Cas_Smoothed numeric, --stats des nouveaux morts smoothed\n" +
                    "stats_Nouveaux_Cas_Par_Million numeric, --stats des nouveaux morts par million\n" +
                    "stats_Nouveaux_Cas_Smoothed_Par_Million numeric,  --stats des nouveaux morts\n" +
                    "iso_CodePays varchar(8));\n" +
                    "ALTER TABLE owid_covid_data.Nouveaux_Cas add constraint fk_Nouveaux_Cas_iso_CodePays FOREIGN KEY(iso_CodePays) REFERENCES owid_covid_data.Pays(iso_CodePays);\n" +
                    "\n" +
                    "CREATE TABLE owid_covid_data.Total_Cas (\n" +
                    "id_Total_Cas SERIAL PRIMARY KEY, \n" +
                    "date_Total_Cas date, --date concernant les cas totaux\n" +
                    "stats_Total_Cas numeric, --stats des nouveaux cas\n" +
                    "stats_Total_Par_Million numeric,  --stats des nouveaux cas par million\n" +
                    "iso_CodePays varchar(8));\n" +
                    "ALTER TABLE owid_covid_data.Total_Cas add constraint fk_Total_Cas_iso_CodePays FOREIGN KEY(iso_CodePays) REFERENCES owid_covid_data.Pays(iso_CodePays);\n" +
                    "\n" +
                    "CREATE TABLE owid_covid_data.Total_Morts (\n" +
                    "id_Total_Morts SERIAL PRIMARY KEY, \n" +
                    "date_Total_Morts date, --date concernant les totaux du nombre de morts\n" +
                    "stats_Total_Morts numeric, --stats des totaux des morts\n" +
                    "stats_Total_Morts_Par_Million numeric,  --stats des totaux des morts par million\n" +
                    "iso_CodePays varchar(8));\n" +
                    "ALTER TABLE owid_covid_data.Total_Morts add constraint fk_Total_Morts_iso_CodePays FOREIGN KEY(iso_CodePays) REFERENCES owid_covid_data.Pays(iso_CodePays);");
            //étape 4: exécuter la requête
            while(res.next())
                System.out.println(res.getInt(1)+"  "+res.getString(2)
                        +"  "+res.getString(3));
            //étape 5: fermez l'objet de connexion
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}