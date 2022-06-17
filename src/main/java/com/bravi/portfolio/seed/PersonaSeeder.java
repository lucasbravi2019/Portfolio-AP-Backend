package com.bravi.portfolio.seed;

import com.bravi.portfolio.entity.About;
import com.bravi.portfolio.entity.Persona;
import com.bravi.portfolio.repository.AboutRepository;
import com.bravi.portfolio.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PersonaSeeder implements CommandLineRunner {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private AboutRepository aboutRepository;
    @Value("${custom.app.seed.enabled}")
    private Boolean enabled;

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) return;
        if (personaRepository.findAll().size() == 0) {
            Persona persona = personaRepository.save(Persona.builder().firstName("Lucas").lastName("Bravi").build());
            aboutRepository.save(
                    About.builder()
                            .title("Fullstack Web Developer")
                            .about("About text")
                            .image("/assets/blabla")
                            .persona(personaRepository.getReferenceById(persona.getId()))
                            .build()
            );
        }

    }
}
