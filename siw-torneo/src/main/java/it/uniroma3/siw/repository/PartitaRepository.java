package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Partita;

public interface PartitaRepository extends CrudRepository<Partita,Long> {

	  @Query("SELECT p FROM Partita p " +
	           "JOIN FETCH p.squadraHome JOIN FETCH p.squadraAway " +
	           "WHERE p.torneo.id = :torneoId AND p.stato = 'PLAYED'")
	    List<Partita> findGiocateByTorneoIdConSquadre(@Param("torneoId") Long torneoId);

}
