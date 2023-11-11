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
import io.micrometer.core.annotation.Timed;

@RestController
public class ImageController {

	@Autowired
	private IImageRepository imageRepository;
	
	@Autowired
    private LogController<Image> logController;

	@Timed(value = "createImage", description = "Time taken to create an image")
	@ResponseBody
	@PostMapping(value = "**/create-image")
	public ResponseEntity<ImageDTO> createImage(@RequestBody @Valid Image image) {

		image = imageRepository.saveAndFlush(image);

		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setId(image.getId());
		imageDTO.setOriginalImage(image.getOriginalImage());
		imageDTO.setThumbnailImage(image.getThumbnailImage());
		imageDTO.setMeme(image.getMeme().getId());

		logController.logEntity(image);
		
		return new ResponseEntity<>(imageDTO, HttpStatus.OK);
	}
	
	@Timed(value = "deleteImage", description = "Time taken to delete an image")
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
		
		logController.logEntity(image);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@Timed(value = "deleteImageById", description = "Time taken to delete an image by ID")
	@ResponseBody
	@DeleteMapping(value = "**/delete-image-by-id/{id}")
	public ResponseEntity<String> deleteImageById(@PathVariable("id") Long id) throws ExceptionProject {

		Image image = imageRepository.findById(id).orElse(null);
		if (image == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}
		
		image.setIsDeleted(true);
		imageRepository.save(image);
		
		logController.logEntity(image);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@Timed(value = "deleteImagePackage", description = "Time taken to delete an image by package")
	@ResponseBody
	@DeleteMapping(value = "**/delete-image-package/{memeId}")
	public ResponseEntity<String> deleteImagePackage(@PathVariable("memeId") Long memeId) {

		imageRepository.deleteImagePackage(memeId);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@Timed(value = "getImage", description = "Time taken to get a specific image")
	@ResponseBody
	@GetMapping(value = "**/get-image/{id}")
	public ResponseEntity<Image> getImage(@PathVariable("id") Long id) throws ExceptionProject {

		Image image = imageRepository.findById(id).orElse(null);
		if (image == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		logController.logEntity(image);
		
		return new ResponseEntity<>(image, HttpStatus.OK);
	}

	@Timed(value = "findImageByMeme", description = "Time taken to find images by meme")
	@ResponseBody
	@GetMapping(value = "**/find-image-by-meme/{memeId}")
	public ResponseEntity<List<Image>> findImageByMeme(@PathVariable("memeId") Long memeId) {

		List<Image> list = imageRepository.findImageByMeme(memeId);

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
