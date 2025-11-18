package jhonatan.Proyectos.Terra_Broker.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jhonatan.Proyectos.Terra_Broker.modelo.Cartera;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;
import jhonatan.Proyectos.Terra_Broker.repository.CarteraRepository;
import jhonatan.Proyectos.Terra_Broker.repository.UsuarioRepository;
import jhonatan.Proyectos.Terra_Broker.security.MyUserDetails;
import jhonatan.Proyectos.Terra_Broker.util.CarteraService;

@Controller
public class MainController {
	
	@Autowired
    private ActivoRepository activoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CarteraRepository carteraRepository;
	
	@Autowired
	private CarteraService carteraService;
	
	
    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, @AuthenticationPrincipal MyUserDetails userDetails) throws Exception {
        Usuario usuario = usuarioRepository.findById(userDetails.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //CREAR CARTERA SI NO EXISTE
        if (usuario.getCartera() == null) {
            Cartera cartera = new Cartera();
            cartera.setUsuario(usuario);
            usuario.setCartera(cartera);

            //GUARDAR LA CARTERA
            carteraRepository.save(cartera);
            usuarioRepository.save(usuario);
        }
        
        // Actualizar valores de todas las posiciones
        carteraService.actualizarPosicionesCartera(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("activos", activoRepository.findAll());
        return "dashboard";
    }
}