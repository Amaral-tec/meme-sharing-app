package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Category;

@Transactional
public interface ICategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM categories WHERE UPPER(TRIM(name)) = UPPER(TRIM(?1))")
	public boolean isCategoryRegistered(String name);

	@Query("SELECT a FROM Category a WHERE UPPER(TRIM(a.name)) LIKE %?1%")
	public List<Category> findCategoryByName(String name);

}
