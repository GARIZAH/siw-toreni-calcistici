package it.uniroma3.siw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.PartitaRepository;
import it.uniroma3.siw.repository.TorneoRepository;

@SpringBootApplication
public class SiwTorneoApplication implements CommandLineRunner {

    @Autowired
    private TorneoRepository torneoRepository;
    @Autowired
    private PartitaRepository partitaRepository;

    public static void main(String[] args) {
        SpringApplication.run(SiwTorneoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        classificaLazy();
        classificaJoinFetch();
    }

    private void classificaLazy() {
        StopWatch watch = new StopWatch();
        watch.start("classifica LAZY (N+1)");

        Torneo torneo = torneoRepository.findById(1L).get();
        for (Squadra s : torneo.getSquadre()) {
            for (Partita p : s.getPartiteHome()) { /* accesso ai gol */ }
            for (Partita p : s.getPartiteAway()) { /* accesso ai gol */ }
        }

        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    private void classificaJoinFetch() {
        StopWatch watch = new StopWatch();
        watch.start("classifica JOIN FETCH");

        List<Partita> partite = partitaRepository.findGiocateByTorneoIdConSquadre(1L);
        // qui ricalcoli la classifica usando la lista già fetchata

        watch.stop();
        System.out.println(watch.prettyPrint());
    }
}