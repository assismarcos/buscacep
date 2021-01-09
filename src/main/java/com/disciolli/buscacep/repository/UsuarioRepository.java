package com.disciolli.buscacep.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.disciolli.buscacep.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Usuario findByNome(String nome);

	Boolean existsByNome(String nome);

	@Transactional
	void deleteByNome(String nome);
}