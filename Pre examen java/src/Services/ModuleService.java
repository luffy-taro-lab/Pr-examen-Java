package Services;

import java.util.List;
import Entities.Module;
import Repositories.ModuleRepository;

public class ModuleService {
    private ModuleRepository moduleRepository = new ModuleRepository();

    public void ajouterModule(Module module) throws ClassNotFoundException {
        moduleRepository.insert(module);
    }

    public List<Module> listerModules() {
        return moduleRepository.selectAll();
    }
}
