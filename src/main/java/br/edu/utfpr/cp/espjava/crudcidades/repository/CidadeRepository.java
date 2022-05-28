package br.edu.utfpr.cp.espjava.crudcidades.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.cp.espjava.crudcidades.domain.CidadeEntidade;

public interface CidadeRepository extends JpaRepository<CidadeEntidade, Long> {

    public Optional<CidadeEntidade> findByNomeAndEstado(String nome, String estado);
}
