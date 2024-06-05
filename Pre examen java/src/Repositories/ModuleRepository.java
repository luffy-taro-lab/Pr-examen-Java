package Repositories;

import Entities.Module;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepository {

        private static final String INSERT_QUERY = "INSERT INTO module (nom_module) VALUES (?)";
    
        public void insert(Module module) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen", "root", "");
    
                PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
                statement.setString(1, module.getNom());
    
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le module a été ajouté avec succès !");
                } else {
                    System.out.println("Échec de l'ajout du module.");
                }
    
                statement.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                System.out.println("Erreur de chargement du driver JDBC : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Erreur SQL lors de l'ajout du module : " + e.getMessage());
            }
        }

    public List<Module> selectAll() {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM module";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen"
                    , "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getInt("id"));
                module.setNom(rs.getString("nom"));
                modules.add(module);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }
}
