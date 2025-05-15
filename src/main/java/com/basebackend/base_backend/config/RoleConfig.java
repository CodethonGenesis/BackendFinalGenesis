package com.basebackend.base_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.basebackend.base_backend.entities.Categoria;
import com.basebackend.base_backend.entities.Estado;
import com.basebackend.base_backend.entities.Rol;
import com.basebackend.base_backend.repositories.CategoryRepository;
import com.basebackend.base_backend.repositories.EstadoRepository;
import com.basebackend.base_backend.repositories.RolRepository;

@Configuration
public class RoleConfig {

    @Bean
    CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                // Crea ROLE_ADMIN si no existe
                Rol adminRole = new Rol("ROLE_ADMIN");
                rolRepository.save(adminRole);

                // Crea ROLE_USER si no existe
                Rol userRole = new Rol("ROLE_USER");
                rolRepository.save(userRole);

                // Crea ROLE_USER si no existe
                Rol pendingRole = new Rol("ROLE_PENDIENTE");
                rolRepository.save(pendingRole);

                // Crea ROLE_ORGANIZACION si no existe
                Rol organizationRole = new Rol("ROLE_ORGANIZACION");
                rolRepository.save(organizationRole);
            }
        };
    }

    @Bean
    CommandLineRunner initCategorias(CategoryRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Crea ROLE_ADMIN si no existe
                Categoria social = new Categoria("Social", "Eventos que promueven la interacción y el encuentro entre personas, ya sea de manera profesional o informal.");
                repository.save(social);
                Categoria medioAmbiente = new Categoria("MedioAmbiente", "Actividades dedicadas a la preservación del medio ambiente y la conciencia ecológica.");
                repository.save(medioAmbiente);
                Categoria ocio = new Categoria("Ocio", "Eventos para el disfrute y relajación, ideales para desconectar de la rutina diaria.");
                repository.save(ocio);
                Categoria deporte = new Categoria("Deporte", "Actividades relacionadas con la práctica y la competición deportiva.");
                repository.save(deporte);
                Categoria musica = new Categoria("Musica", "Conciertos y actividades relacionadas con la música, tanto en vivo como en otros formatos.");
                repository.save(musica);

            }
        };
    }

    @Bean
    CommandLineRunner initEstados(EstadoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Crea ROLE_ADMIN si no existe
                Estado confirmar = new Estado("Por confirmar");
                repository.save(confirmar);
                Estado confirmado = new Estado("Confirmado");
                repository.save(confirmado);
                Estado progreso = new Estado("En progreso");
                repository.save(progreso);
                Estado finalizado = new Estado("Finalizado");
                repository.save(finalizado);
                Estado cancelado = new Estado("Cancelado");
                repository.save(cancelado);

            }
        };
    }

}
