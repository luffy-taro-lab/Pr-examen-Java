package Entities;

public class Module {
    private int id;
    private String nom;

    // Constructors, getters, and setters
    public Module() {}

    public Module(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
