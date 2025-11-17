package jhonatan.Proyectos.Terra_Broker.util;

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
		if (activoRepository.count() == 0) { // solo si no hay activos
            activoRepository.save(new Activo("bitcoin", "Bitcoin", "BTC.jpg", 82480.00));
            activoRepository.save(new Activo("solana", "Solana", "sol.jpeg", 122.60));
            activoRepository.save(new Activo("ethereum", "Ethereum", "ETH.jpg", 2756.00));
        }
	}

}
