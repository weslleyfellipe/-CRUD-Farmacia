package com.generation.farmacia.CategoriaController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.CategoriaRepository.CategoriaRepository;
import com.generation.farmacia.CategoriaRepository.ProdutoRepository;
import com.generation.farmacia.model.Produto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ProdutoController {

	@Autowired 
	private ProdutoRepository produtoRepository;	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		
		return ResponseEntity.ok(produtoRepository.findAll());

	}
    
	@GetMapping("/{id}")
	public ResponseEntity<Produto>getByid(@PathVariable Long id ){
	
 	return produtoRepository.findById(id)     
			
 	.map(resposta -> ResponseEntity.ok(resposta))    
	.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //
		
		}
		
	
	@GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByTitulo(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }
  @PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRepository.save(produto));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse produto não existe!", null);
	}


    @PutMapping
    
	  public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		
		   if(produtoRepository.existsById(produto.getId())){
		   	
		   if(categoriaRepository.existsById(produto.getCategoria().getId()))
			 
	   		return ResponseEntity.status(HttpStatus.OK)
          .body(produtoRepository.save(produto)); 
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não existe !", null);
		 
	 }
     	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
    }
    @DeleteMapping ("/{id}")
	 public void  delete(@PathVariable Long id) {
		 Optional<Produto> postagem = produtoRepository.findById(id);
		 if(postagem.isEmpty())// .
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 
		 produtoRepository.deleteById(id);
			 
    
    }
    
    @GetMapping("/ordenados-por-maior-preco")
    public List<Produto> listarProdutosOrdenadosPorPrecomaior() {
        return produtoRepository.findAllByOrderByPrecoDesc();
    }

    @GetMapping("/ordenados-por-menor-preco")
    public List<Produto> listarProdutosOrdenadosPorPrecomenor() {
        return produtoRepository.findAllByOrderByPrecoAsc();
    }

}
	
	

