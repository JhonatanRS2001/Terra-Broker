package jhonatan.Proyectos.Terra_Broker.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;

@Controller
public class MainController {
	
	@Autowired
    private ActivoRepository activoRepository;

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Activo> activos = activoRepository.findAll();
        model.addAttribute("activos", activos);
        return "dashboard";
    }
}