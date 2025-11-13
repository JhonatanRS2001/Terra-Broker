package jhonatan.Proyectos.Terra_Broker.controladores;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jhonatan.Proyectos.Terra_Broker.dao.TerraBrokerDAO;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;

@Controller
public class ControladorUsuario {
	
	@Autowired
    private TerraBrokerDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder; 
	
	@GetMapping("/registroUsuario")
    public String altaUsuario(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "registroUsuario";
    }

    @PostMapping("/registroUsuario/submit")
    public String altaClienteSubmit(@ModelAttribute Usuario usuario, Model model) {
        System.out.println("ALTA USUARIO");

        //Cifrar la contraseña antes de guardar
        String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrada);

        dao.altaUsuario(usuario);

        model.addAttribute("usuarioForm", usuario);
        return "confirmacionRegistro";
    }
}
