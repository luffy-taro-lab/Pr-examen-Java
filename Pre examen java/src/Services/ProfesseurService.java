package Services;

import java.util.List;
import Entities.Professeur;
import Repositories.ProfesseurRepository;

public class ProfesseurService {
    private ProfesseurRepository professeurRepository = new ProfesseurRepository();

    public List<Professeur> listerProfesseurs() throws ClassNotFoundException {
        return professeurRepository.selectAll();
    }
}
