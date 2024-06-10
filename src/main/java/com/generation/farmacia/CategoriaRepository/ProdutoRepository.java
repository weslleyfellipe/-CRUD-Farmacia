package com.generation.farmacia.CategoriaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.farmacia.model.Produto;



public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	//SELECT * FROM tb_produtos WHERE descricao LIKE "%Nomes%";
	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("Nomes") String nome);
	List<Produto> findAllByOrderByPrecoDesc();
	List<Produto> findAllByOrderByPrecoAsc();
   
}
