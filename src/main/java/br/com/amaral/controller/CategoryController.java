package br.com.amaral.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.Category;
import br.com.amaral.repository.ICategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	private ICategoryRepository categoryRepository;

	@ResponseBody
	@PostMapping(value = "**/create-category")
	public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category)
			throws ExceptionProject {

		boolean result = categoryRepository.isCategoryRegistered(category.getName());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name " + category.getName());
		}

		categoryRepository.saveAndFlush(category);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-category")
	public ResponseEntity<String> deleteCategory(@RequestBody Category category) {

		if (!categoryRepository.findById(category.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + category.getId(), HttpStatus.NOT_FOUND);
		}
		
		category.setIsDeleted(true);
		categoryRepository.save(category);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-category-by-id/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id) throws ExceptionProject {
		
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}
		
		category.setIsDeleted(true);
		categoryRepository.save(category);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-category/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) throws ExceptionProject {

		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-categories")
	public ResponseEntity<List<Category>> findAllCategory() {

		List<Category> list = categoryRepository.findAll();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-category-by-name/{name}")
	public ResponseEntity<List<Category>> findCategoryByName(@PathVariable("name") String name) {

		List<Category> list = categoryRepository.findCategoryByName(name.toUpperCase());

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
