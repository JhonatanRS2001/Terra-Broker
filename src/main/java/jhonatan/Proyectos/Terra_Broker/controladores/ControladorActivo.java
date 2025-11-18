package jhonatan.Proyectos.Terra_Broker.controladores;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;
import jhonatan.Proyectos.Terra_Broker.repository.UsuarioRepository;
import jhonatan.Proyectos.Terra_Broker.security.MyUserDetails;
import jhonatan.Proyectos.Terra_Broker.util.CarteraService;

@RestController
@RequestMapping("/api")
public class ControladorActivo {

    @Autowired
    private ActivoRepository activoRepository;
    
    @Autowired
    private CarteraService carteraService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @GetMapping("/precios")
    public Map<Long, Double> obtenerPrecios() {

        Map<Long, Double> precios = new HashMap<>();

        for (Activo a : activoRepository.findAll()) {
            precios.put(a.getId(), a.getPrecioActual());
        }

        return precios;
    }
    
    @PostMapping("/comprar/{activoId}")
    public ResponseEntity<?> comprarActivo(@PathVariable Long activoId,@RequestParam Double cantidad, @AuthenticationPrincipal MyUserDetails userDetails) {
        try {
            carteraService.comprarActivo(userDetails.getUsuario().getId(), activoId, cantidad);
            return ResponseEntity.ok(Map.of("mensaje", "Compra realizada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
 //OBTENER CARTERA ACTUALIZADA
    @GetMapping("/cartera")
    public ResponseEntity<?> obtenerCartera(@AuthenticationPrincipal MyUserDetails userDetails) {
        try {
            Long usuarioId = userDetails.getUsuario().getId();
            
            // Actualizar valores de todas las posiciones antes de devolver la cartera
            carteraService.actualizarPosicionesCartera(usuarioId);

            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new Exception("Usuario no encontrado"));

            return ResponseEntity.ok(usuario.getCartera());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
