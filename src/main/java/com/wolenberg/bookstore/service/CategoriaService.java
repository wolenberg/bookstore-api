package com.wolenberg.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolenberg.bookstore.domain.Categoria;
import com.wolenberg.bookstore.dtos.CategoriaDTO;
import com.wolenberg.bookstore.repositories.CategoriaRepository;
import com.wolenberg.bookstore.service.exceptions.DataIntegrityViolationException;
import com.wolenberg.bookstore.service.exceptions.ObjectNotfoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria findbyId(Integer id) {
		Optional<Categoria> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotfoundException(
				"Objeto não encontrado! Id:" + id + " Tipo: " + Categoria.class.getName()));

	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Categoria create(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDto) {
		Categoria obj = findbyId(id);
		obj.setNome(objDto.getNome());
		obj.setDescricao(objDto.getDescricao());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findbyId(id);

		try {
			repository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Categoria não pode ser deletada! Possui livros associados");
		}

	}

}
