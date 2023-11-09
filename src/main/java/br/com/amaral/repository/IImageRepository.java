package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Image;

@Transactional
public interface IImageRepository extends JpaRepository<Image, Long> {

	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE FROM images SET is_deleted = True WHERE meme_id = ?1")
	void deleteImagePackage(Long memeId);
	
	@Query("SELECT a FROM Image a WHERE a.meme.id = ?1")
	List<Image> findImageByMeme(Long memeId);

}
