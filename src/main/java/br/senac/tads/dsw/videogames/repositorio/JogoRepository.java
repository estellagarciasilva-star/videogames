package br.senac.tads.dsw.videogames.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads.dsw.videogames.entidade.JogoEntity;

// O JpaRepository já traz findAll, findById, save e delete prontos
@Repository
public interface JogoRepository extends JpaRepository<JogoEntity, Integer> {
}