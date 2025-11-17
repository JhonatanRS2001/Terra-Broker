package jhonatan.Proyectos.Terra_Broker.util;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;

@Service
public class PrecioActivoService {

    @Autowired
    private ActivoRepository activoRepository;

    private Random random = new Random();

    @Scheduled(fixedRate = 10000) //10 SEGUNDOS
    public void actualizarPrecios() {

        List<Activo> activos = activoRepository.findAll();

        for (Activo activo : activos) {

            //VARIACION DE PRECIOS
            double variacion = 1 + (random.nextDouble() * 0.06 - 0.03);

            double precioActual = activo.getPrecioActual();
            double nuevoPrecio = precioActual * variacion;

            nuevoPrecio = Math.round(nuevoPrecio * 100.0) / 100.0;
            
         //EVENTOS PUMP O CRASH
            int prob = random.nextInt(100); // número 0 - 99

            if (prob < 1) {  
                boolean crash = random.nextBoolean();

                if (crash) {
                    //CAIDA FUERTE 5-10%
                    double porcentaje = -(5 + random.nextInt(11)); 
                    nuevoPrecio = precioActual + (precioActual * (porcentaje / 100.0));
                    System.out.println("FLASH CRASH en " + activo.getNombre() + ": " + porcentaje + "%");
                } else {
                    //SUBIDA SUERTE 5-10%
                    double porcentaje = 5 + random.nextInt(11); 
                    nuevoPrecio = precioActual + (precioActual * (porcentaje / 100.0));
                    System.out.println("FLASH PUMP en " + activo.getNombre() + ": +" + porcentaje + "%");
                }
            }

            //EVITAR PRECIOS NEGATIVOS
            if (nuevoPrecio < 0.01) nuevoPrecio = 0.01;

            activo.setPrecioActual(nuevoPrecio);
            activoRepository.save(activo);
        }

        System.out.println("Precios simulados actualizados.");
    }
}