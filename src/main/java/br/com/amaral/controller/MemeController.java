package br.com.amaral.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import br.com.amaral.model.Meme;
import br.com.amaral.model.Image;
import br.com.amaral.repository.IMemeRepository;
import br.com.amaral.service.ImageService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@RestController
public class MemeController {

	@Autowired
	private IMemeRepository memeRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
    private LogController<Meme> logController;

	@Counted(value = "memeController.createMemeAccessCount", description = "Count of accesses to createMeme endpoint")
	@Timed(value = "createMeme", description = "Time taken to create a meme")
	@ResponseBody
	@PostMapping(value = "**/create-meme")
	public ResponseEntity<Meme> createMeme(@RequestBody @Valid Meme meme)
			throws ExceptionProject, IOException {

		if (memeRepository.isMemeRegistered(meme.getName())) {
            throw new ExceptionProject("Operation not carried out: Already registered with the name " + meme.getName());
        }

        List<Image> images = meme.getImages();
        if (images == null || images.isEmpty() || images.size() > 6) {
            throw new ExceptionProject("The meme must contain between 1 and 6 associated images.");
		}
		if (meme.getId() == null) {

			imageService.saveMemeImages(meme);
		}

		memeRepository.save(meme);
		
		logController.logEntity(meme);

		return new ResponseEntity<>(meme, HttpStatus.OK);
	}
	
	@Counted(value = "memeController.deleteMemeAccessCount", description = "Count of accesses to deleteMeme endpoint")
	@Timed(value = "deleteMeme", description = "Time taken to delete a meme")
	@ResponseBody
	@PostMapping(value = "**/delete-meme")
	public ResponseEntity<String> deleteMeme(@RequestBody Meme meme) {

		if (!memeRepository.findById(meme.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database " + meme.getId(),
					HttpStatus.NOT_FOUND);
		}

		meme.setIsDeleted(true);
		memeRepository.save(meme);
		
		logController.logEntity(meme);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@Counted(value = "memeController.deleteMemeByIdAccessCount", description = "Count of accesses to deleteMemeById endpoint")
	@Timed(value = "deleteMemeById", description = "Time taken to delete a meme by ID")
	@ResponseBody
	@DeleteMapping(value = "**/delete-meme-by-id/{id}")
	public ResponseEntity<String> deleteMemeById(@PathVariable("id") Long id) throws ExceptionProject {

		Meme meme = memeRepository.findById(id).orElse(null);
		if (meme == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		meme.setIsDeleted(true);
		memeRepository.save(meme);
		
		logController.logEntity(meme);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@Counted(value = "memeController.getMemeOfTheDayAccessCount", description = "Count of accesses to getMemeOfTheDay endpoint")
	@Timed(value = "getMemeOfTheDay", description = "Time taken to get the meme of the day")
	@ResponseBody
	@GetMapping(value = "**/get-meme-of-the-day")
    public ResponseEntity<Meme> getMemeOfTheDay() throws ExceptionProject {
		
        List<Meme> allMemes = memeRepository.findAll();
        
        if (allMemes.isEmpty()) {
			throw new ExceptionProject("No memes available");
        }
        
        Random random = new Random();
        int randomIndex = random.nextInt(allMemes.size());
        
        Meme randomMeme = allMemes.get(randomIndex);
        
        logController.logEntity(randomMeme);
        
        return new ResponseEntity<>(randomMeme, HttpStatus.OK);
    }

	@Counted(value = "memeController.getMemeAccessCount", description = "Count of accesses to getMeme endpoint")
	@Timed(value = "getMeme", description = "Time taken to get a specific meme")
	@ResponseBody
	@GetMapping(value = "**/get-meme/{id}")
	public ResponseEntity<Meme> getMeme(@PathVariable("id") Long id) throws ExceptionProject {

		Meme meme = memeRepository.findById(id).orElse(null);
		if (meme == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		logController.logEntity(meme);
		
		return new ResponseEntity<>(meme, HttpStatus.OK);
	}
	
	@Counted(value = "memeController.findAllMemeAccessCount", description = "Count of accesses to findAllMeme endpoint")
	@Timed(value = "findAllMeme", description = "Time taken to find all memes")
	@ResponseBody
	@GetMapping(value = "**/find-all-meme")
	public ResponseEntity<List<Meme>> findAllMeme() {

		List<Meme> list = memeRepository.findAll();

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Counted(value = "memeController.findMemeByNameAccessCount", description = "Count of accesses to findMemeByName endpoint")
	@Timed(value = "findMemeByName", description = "Time taken to find memes by name")
	@ResponseBody
	@GetMapping(value = "**/find-meme-by-name/{name}")
	public ResponseEntity<List<Meme>> findMemeByName(@PathVariable("name") String name) {

		List<Meme> list = memeRepository.findMemeByName(name.toUpperCase());

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
