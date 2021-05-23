package com.wolenberg.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolenberg.bookstore.domain.Categoria;
import com.wolenberg.bookstore.repositories.CategoriaRepository;
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
}
