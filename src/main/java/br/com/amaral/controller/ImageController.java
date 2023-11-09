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
import br.com.amaral.model.Image;
import br.com.amaral.model.dto.ImageDTO;
import br.com.amaral.repository.IImageRepository;

@RestController
public class ImageController {

	@Autowired
	private IImageRepository imageRepository;

	@ResponseBody
	@PostMapping(value = "**/create-image")
	public ResponseEntity<ImageDTO> createImage(@RequestBody @Valid Image image) {

		image = imageRepository.saveAndFlush(image);

		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setId(image.getId());
		imageDTO.setOriginalImage(image.getOriginalImage());
		imageDTO.setThumbnailImage(image.getThumbnailImage());
		imageDTO.setMeme(image.getMeme().getId());


		return new ResponseEntity<>(imageDTO, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-image")
	public ResponseEntity<String> deleteImage(@RequestBody Image image) {

		if (!imageRepository.existsById(image.getId())) {
			return new ResponseEntity<>(
					"Operation not performed: Not included in the ID database: " + image.getId(),
					HttpStatus.NOT_FOUND);
		}

		image.setIsDeleted(true);
		imageRepository.save(image);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-image-by-id/{id}")
	public ResponseEntity<String> deleteImageById(@PathVariable("id") Long id) throws ExceptionProject {

		Image image = imageRepository.findById(id).orElse(null);
		if (image == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}
		
		image.setIsDeleted(true);
		imageRepository.save(image);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/delete-image-package/{memeId}")
	public ResponseEntity<String> deleteImagePackage(@PathVariable("memeId") Long memeId) {

		imageRepository.deleteImagePackage(memeId);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-image/{id}")
	public ResponseEntity<Image> getImage(@PathVariable("id") Long id) throws ExceptionProject {

		Image image = imageRepository.findById(id).orElse(null);
		if (image == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		return new ResponseEntity<>(image, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-image-by-meme/{memeId}")
	public ResponseEntity<List<Image>> findImageByMeme(@PathVariable("memeId") Long memeId) {

		List<Image> images = imageRepository.findImageByMeme(memeId);

		return new ResponseEntity<>(images, HttpStatus.OK);
	}
}
