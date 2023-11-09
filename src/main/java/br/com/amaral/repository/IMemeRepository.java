package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Meme;

@Transactional
public interface IMemeRepository extends JpaRepository<Meme, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM memes WHERE UPPER(TRIM(name)) = UPPER(TRIM(?1))")
	public boolean isMemeRegistered(String name);

	@Query("SELECT a FROM Meme a WHERE UPPER(TRIM(a.name)) LIKE %?1%")
	List<Meme> findMemeByName(String name);

}
