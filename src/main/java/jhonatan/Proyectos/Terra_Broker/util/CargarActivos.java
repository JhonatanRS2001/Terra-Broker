package jhonatan.Proyectos.Terra_Broker.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;

@Component
public class CargarActivos implements CommandLineRunner {

    @Autowired
    private ActivoRepository activoRepository;

    @Override
    public void run(String... args) throws Exception {
        //ACTIVOS QUE AVERIGUAMOS SI EXISTEN
        List<Activo> activosPorAgregar = List.of(
            new Activo("bitcoin", "Bitcoin", "BTC.jpg", 82480.00),
            new Activo("solana", "Solana", "sol.jpeg", 122.60),
            new Activo("ethereum", "Ethereum", "ETH.jpg", 2756.00),
            new Activo("xrp", "XRP", "xrp.jpg", 1.84),
            new Activo("ada", "Cardano", "ADA.jpg", 0.40),
            new Activo("dogecoin", "Dogecoin", "DOGE.png", 0.137)
        );

        for (Activo a : activosPorAgregar) {
            //COMPROBAR SI YA EXISTE UN ACTIVO CON ESE NOMBRE
            boolean existe = activoRepository.existsByNombre(a.getNombre());
            if (!existe) {
                activoRepository.save(a);
            }
        }
    }
}
