package com.economic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.economic.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u inner join fetch u.permissoes p "
			+ "where u.email = :email and u.ativo = true")
	Optional<Usuario> buscarPorEmail(@Param("email") String email);
	
	List<Usuario> findByPermissoesDescricao(String descricaoPermissao);
	
}
