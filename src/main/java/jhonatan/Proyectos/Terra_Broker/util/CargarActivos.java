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
            activoRepository.save(new Activo("BTC", "Bitcoin", "BTC.jpg", 100000.00));
            activoRepository.save(new Activo("SOL", "Solana", "sol.jpeg", 150.00));
            activoRepository.save(new Activo("ETH", "Ethereum", "ETH.jpg", 3000.00));
        }
	}

}
