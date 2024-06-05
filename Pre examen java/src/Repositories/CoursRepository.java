package Repositories;

import Entities.Cours;
import Entities.Module;
import Entities.Professeur;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CoursRepository {

        private static final String INSERT_QUERY = "INSERT INTO cours (date_cours, heure_debut, heure_fin, id_prof, id_module) VALUES (?, ?, ?, ?, ?)";
    
        public void insert(Cours cours) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen", "root", "");
    
                PreparedStatement statement = conn.prepareStatement(INSERT_QUERY);
                statement.setDate(1, Date.valueOf(cours.getDate()));
                statement.setTime(2, Time.valueOf(cours.getHeureDebut()));
                statement.setTime(3, Time.valueOf(cours.getHeureFin()));
                statement.setInt(4, cours.getProfesseur().getId());
                statement.setInt(5, cours.getModule().getId());
    
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Le cours a été ajouté avec succès !");
                } else {
                    System.out.println("Échec de l'ajout du cours.");
                }
    
                statement.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                System.out.println("Erreur de chargement du driver JDBC : " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Erreur SQL lors de l'ajout du cours : " + e.getMessage());
            }
        }
    public List<Cours> selectAll() {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT c.id, c.date, c.heureDebut, c.heureFin, p.id AS prof_id, p.nom AS prof_nom, p.prenom AS prof_prenom, p.tel AS prof_tel, " +
                       "m.id AS module_id, m.nom AS module_nom " +
                       "FROM cours c " +
                       "JOIN professeur p ON c.professeur_id = p.id " +
                       "JOIN module m ON c.module_id = m.id";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setDate(rs.getDate("date").toLocalDate());
                cours.setHeureDebut(rs.getTime("heureDebut").toLocalTime());
                cours.setHeureFin(rs.getTime("heureFin").toLocalTime());

                Professeur prof = new Professeur();
                prof.setId(rs.getInt("prof_id"));
                prof.setNom(rs.getString("prof_nom"));
                prof.setPrenom(rs.getString("prof_prenom"));
                prof.setTel(rs.getString("prof_tel"));
                cours.setProfesseur(prof);

                Module module = new Module();
                module.setId(rs.getInt("module_id"));
                module.setNom(rs.getString("module_nom"));
                cours.setModule(module);

                coursList.add(cours);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coursList;
    }

    public List<Cours> selectByModuleAndProf(int moduleId, int profId) {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT c.id, c.date, c.heureDebut, c.heureFin, p.id AS prof_id, p.nom AS prof_nom, p.prenom AS prof_prenom, p.tel AS prof_tel, " +
                       "m.id AS module_id, m.nom AS module_nom " +
                       "FROM cours c " +
                       "JOIN professeur p ON c.professeur_id = p.id " +
                       "JOIN module m ON c.module_id = m.id " +
                       "WHERE c.module_id = ? AND c.professeur_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_examen"
        , "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, moduleId);
            stmt.setInt(2, profId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cours cours = new Cours();
                    cours.setId(rs.getInt("id"));
                    cours.setDate(rs.getDate("date").toLocalDate());
                    cours.setHeureDebut(rs.getTime("heureDebut").toLocalTime());
                    cours.setHeureFin(rs.getTime("heureFin").toLocalTime());

                    Professeur prof = new Professeur();
                    prof.setId(rs.getInt("prof_id"));
                    prof.setNom(rs.getString("prof_nom"));
                    prof.setPrenom(rs.getString("prof_prenom"));
                    prof.setTel(rs.getString("prof_tel"));
                    cours.setProfesseur(prof);

                    Module module = new Module();
                    module.setId(rs.getInt("module_id"));
                    module.setNom(rs.getString("module_nom"));
                    cours.setModule(module);

                    coursList.add(cours);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coursList;
    }
}
