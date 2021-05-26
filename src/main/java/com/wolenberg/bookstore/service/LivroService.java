package com.wolenberg.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolenberg.bookstore.domain.Categoria;
import com.wolenberg.bookstore.domain.Livro;
import com.wolenberg.bookstore.dtos.LivroDTO;
import com.wolenberg.bookstore.repositories.LivroRepository;
import com.wolenberg.bookstore.service.exceptions.DataIntegrityViolationException;
import com.wolenberg.bookstore.service.exceptions.ObjectNotfoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	public Livro findbyId(Integer id) {
		Optional<Livro> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotfoundException(
				"Objeto não encontrado! Id:" + id + " Tipo: " + Livro.class.getName()));

	}

	public List<Livro> findAll(Integer id_cat) {
		categoriaService.findbyId(id_cat);
		return repository.findAllByCategoria(id_cat);

	}

	public Livro create(Livro obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Livro update(Integer id, LivroDTO objDto) {
		Livro obj = findbyId(id);
		obj.setTitulo(objDto.getTitulo());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findbyId(id);

		try {
			repository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Livro não pode ser deletada! Possui livros associados");
		}

	}

	public Livro update(Integer id, Livro obj) {
		Livro newObj = findbyId(id);
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Livro newObj, Livro obj) {

		newObj.setTitulo(obj.getTitulo());
		newObj.setNomeAutor(obj.getNomeAutor());
		newObj.setTexto(obj.getTexto());
	}

	public Livro create(Integer id_cat, Livro obj) {

		obj.setId(null);
		Categoria cat = categoriaService.findbyId(id_cat);
		obj.setCategoria(cat);
		return repository.save(obj);
	}

}
