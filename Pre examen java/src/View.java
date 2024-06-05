import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import Entities.Cours;
import Entities.Module;
import Entities.Professeur;
import Services.CoursService;
import Services.ModuleService;
import Services.ProfesseurService;

public class View {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ModuleService moduleService = new ModuleService();
        CoursService coursService = new CoursService();
        ProfesseurService professeurService = new ProfesseurService();

        int choix;
        do {
            System.out.println("=============================================");
            System.out.println("|                 MENU                      |");
            System.out.println("=============================================");
            System.out.println("| 1. Ajouter un Module                      |");
            System.out.println("| 2. Lister les Modules                     |");
            System.out.println("| 3. Créer un Cours                         |");
            System.out.println("| 4. Lister tous les Cours                  |");
            System.out.println("| 5. Lister les Cours d'un Module et Prof.  |");
            System.out.println("| 6. Quitter                                |");
            System.out.println("=============================================");
            System.out.print("Faites un choix: ");
            choix = sc.nextInt();
            sc.nextLine();  

            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom du module: ");
                    String nomModule = sc.nextLine();
                    Module module = new Module();
                    module.setNom(nomModule);
                    moduleService.ajouterModule(module);
                    System.out.println("Module ajouté avec succès!");
                    break;
                case 2:
                    List<Module> modules = moduleService.listerModules();
                    System.out.println("Liste des Modules:");
                    for (Module m : modules) {
                        System.out.println("ID: " + m.getId() + ", Nommodule: " + m.getNom());
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du module: ");
                    int moduleId = sc.nextInt();
                    sc.nextLine();  

                    System.out.print("Entrez l'ID du professeur: ");
                    int profId = sc.nextInt();
                    sc.nextLine();  

                    System.out.print("Entrez la date du cours (AAAA-MM-JJ): ");
                    String date = sc.nextLine();

                    System.out.print("Entrez l'heure de début (HH:MM): ");
                    String heureDebut = sc.nextLine();

                    System.out.print("Entrez l'heure de fin (HH:MM): ");
                    String heureFin = sc.nextLine();

                    Cours cours = new Cours();
                    cours.setDate(LocalDate.parse(date));
                    cours.setHeureDebut(LocalTime.parse(heureDebut));
                    cours.setHeureFin(LocalTime.parse(heureFin));
                    cours.setModule(new Module(moduleId));
                    cours.setProfesseur(new Professeur(profId));
                    coursService.ajouterCours(cours);
                    System.out.println("Cours créé avec succès!");
                    break;
                case 4:
                    List<Cours> coursList = coursService.listerCours();
                    System.out.println("Liste des Cours:");
                    for (Cours c : coursList) {
                        System.out.println("ID: " + c.getId() + ", Date: " + c.getDate() + ", Début: " + c.getHeureDebut() + ", Fin: " + c.getHeureFin() +
                                           ", Module: " + c.getModule().getNom() + ", Professeur: " + c.getProfesseur().getNom() + " " + c.getProfesseur().getPrenom());
                    }
                    break;
                case 5:
                    System.out.print("Entrez l'ID du module: ");
                    int modId = sc.nextInt();
                    sc.nextLine();  

                    System.out.print("Entrez l'ID du professeur: ");
                    int professeurId = sc.nextInt();
                    sc.nextLine();  

                    List<Cours> coursModProf = coursService.listerCoursParModuleEtProfesseur(modId, professeurId);
                    System.out.println("Liste des Cours du Module et Professeur:");
                    for (Cours c : coursModProf) {
                        System.out.println("ID: " + c.getId() + ", Date: " + c.getDate() + ", Début: " + c.getHeureDebut() + ", Fin: " + c.getHeureFin() +
                                           ", Module: " + c.getModule().getNom() + ", Professeur: " + c.getProfesseur().getNom() + " " + c.getProfesseur().getPrenom());
                    }
                    break;
                case 6:
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (choix != 6);

        sc.close();
    }
}
