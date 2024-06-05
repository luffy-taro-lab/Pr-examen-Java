package Repositories;

import Entities.Professeur;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfesseurRepository {

    private static final String INSERT_QUERY = "INSERT INTO professeur (nom, prenom, tel) VALUES (?, ?, ?)";

    public Professeur insert(Professeur professeur) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen", "root", "");

            PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
            statement.setString(1, professeur.getNom());
            statement.setString(2, professeur.getPrenom());
            statement.setString(3, professeur.getTel());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le professeur a été ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout du professeur.");
            }

            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver JDBC : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'ajout du professeur : " + e.getMessage());
        }
        return professeur;
    }
}
