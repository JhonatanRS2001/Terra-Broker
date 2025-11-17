package jhonatan.Proyectos.Terra_Broker.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;

@RestController
@RequestMapping("/api")
public class ControladorActivo {

    @Autowired
    private ActivoRepository activoRepository;

    @GetMapping("/precios")
    public Map<Long, Double> obtenerPrecios() {

        Map<Long, Double> precios = new HashMap<>();

        for (Activo a : activoRepository.findAll()) {
            precios.put(a.getId(), a.getPrecioActual());
        }

        return precios;
    }
}
