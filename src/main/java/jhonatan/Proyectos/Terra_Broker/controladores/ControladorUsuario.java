package jhonatan.Proyectos.Terra_Broker.controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jhonatan.Proyectos.Terra_Broker.dao.TerraBrokerDAO;
import jhonatan.Proyectos.Terra_Broker.modelo.Transaccion;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;
import jhonatan.Proyectos.Terra_Broker.repository.TransaccionRepository;
import jhonatan.Proyectos.Terra_Broker.repository.UsuarioRepository;
import jhonatan.Proyectos.Terra_Broker.security.MyUserDetails;

@Controller
public class ControladorUsuario {
	
	@Autowired
    private TerraBrokerDAO dao;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;

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

        //CIFRAR CONTRASEÑA
        String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrada);

        dao.altaUsuario(usuario);

        model.addAttribute("usuarioForm", usuario);
        return "confirmacionRegistro";
    }
    
    @PostMapping("/miCuenta/actualizar")
    public String actualizarMisDatos(Usuario usuario) {
        dao.modificarUsuario(usuario);
        return "confirmacion";
    }
    
    @GetMapping("/miCuenta")
    public String accederCuenta(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
    	Usuario usuario = usuarioRepository.findById(userDetails.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "miCuenta";
    }
    
    @PostMapping("/api/ingresarSaldo")
    public ResponseEntity<?> ingresarSaldo(@RequestParam double cantidad, @AuthenticationPrincipal MyUserDetails userDetails) {
    	if (cantidad <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "La cantidad debe ser mayor que 0"));
        }
        Usuario usuario = usuarioRepository.findById(userDetails.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setSaldo(usuario.getSaldo() + cantidad);
        usuarioRepository.save(usuario);

        //CREAR TRANSACCION DE TIPO INGRESO
        Transaccion ingreso = new Transaccion();
        ingreso.setUsuario(usuario);
        ingreso.setTipo("INGRESO");
        ingreso.setCantidad(cantidad);
        ingreso.setTotal(cantidad);
        ingreso.setFecha(LocalDateTime.now());
        transaccionRepository.save(ingreso);

        return ResponseEntity.ok(Map.of("mensaje", "Saldo ingresado correctamente"));
    }
    
    @GetMapping("/historialSaldo")
    @ResponseBody
    public List<Map<String, Object>> historialSaldo(@AuthenticationPrincipal MyUserDetails userDetails) {
        Usuario usuario = usuarioRepository.findById(userDetails.getUsuario().getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Transaccion> transacciones = transaccionRepository.findByUsuarioOrderByFechaAsc(usuario);
        List<Map<String, Object>> historial = new ArrayList<>();
        double saldoAcumulado = 0.0;

        for (Transaccion t : transacciones) {
            switch (t.getTipo()) {
                case "COMPRA":
                    saldoAcumulado -= t.getTotal();
                    break;
                case "VENTA":
                    saldoAcumulado += t.getTotal();
                    break;
                case "INGRESO":
                    saldoAcumulado += t.getTotal();
                    break;
            }
            
            Map<String, Object> punto = new HashMap<>();
            punto.put("fecha", t.getFecha());
            punto.put("saldo", saldoAcumulado);
            historial.add(punto);
        }

        return historial; 
    }
}
