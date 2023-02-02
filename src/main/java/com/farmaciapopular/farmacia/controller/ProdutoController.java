package com.farmaciapopular.farmacia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.farmaciapopular.farmacia.Repository.ProdutoRepository;
import com.farmaciapopular.farmacia.model.Produto;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins= "*", allowedHeaders="*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return repository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
		@GetMapping("/nome/{nome}")
		public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
			return ResponseEntity.ok(repository
					.findAllByNomeContainingIgnoreCase(nome));
		
		}
		@PostMapping
		public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
		}
		@PutMapping
		public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto ){
			return repository.findById(produto.getId()).map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
					.body(repository.save(produto))).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Produto> produto= repository.findById(id);
			
			if(produto.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			repository.deleteById(id);
			
			
					
		}


	}
	


