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
import io.micrometer.core.annotation.Timed;

@RestController
public class CategoryController {

	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
    private LogController<Category> logController;

	@Timed(value = "createCategory", description = "Time taken to create a category")
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
		
		logController.logEntity(category);
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@Timed(value = "deleteCategory", description = "Time taken to delete a category")
	@ResponseBody
	@PostMapping(value = "**/delete-category")
	public ResponseEntity<String> deleteCategory(@RequestBody Category category) {

		if (!categoryRepository.findById(category.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + category.getId(), HttpStatus.NOT_FOUND);
		}
		
		category.setIsDeleted(true);
		categoryRepository.save(category);
		
		logController.logEntity(category);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@Timed(value = "deleteCategoryById", description = "Time taken to delete a category by ID")
	@ResponseBody
	@DeleteMapping(value = "**/delete-category-by-id/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id) throws ExceptionProject {
		
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}
		
		category.setIsDeleted(true);
		categoryRepository.save(category);
		
		logController.logEntity(category);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@Timed(value = "getCategory", description = "Time taken to get a specific category")
	@ResponseBody
	@GetMapping(value = "**/get-category/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) throws ExceptionProject {

		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		logController.logEntity(category);
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@Timed(value = "findAllCategory", description = "Time taken to find all categories")
	@ResponseBody
	@GetMapping(value = "**/find-all-categories")
	public ResponseEntity<List<Category>> findAllCategory() {

		List<Category> list = categoryRepository.findAll();

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Timed(value = "findCategoryByName", description = "Time taken to find categories by name")
	@ResponseBody
	@GetMapping(value = "**/find-category-by-name/{name}")
	public ResponseEntity<List<Category>> findCategoryByName(@PathVariable("name") String name) {

		List<Category> list = categoryRepository.findCategoryByName(name.toUpperCase());

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
