package propets.controller.lostandfound;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propets.configuration.lostandfound.LostAndFoundConfiguration;
import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;
import propets.dto.lostandfound.SearchByInfoRequestDto;
import propets.service.lostandfound.LostAndFoundService;

@CrossOrigin(origins = "*", exposedHeaders = "X-token")
@RestController
@RequestMapping("/{lang}/v1")
public class LostAndFoundController {

	@Autowired
	LostAndFoundService lostAndFoundService;

	@Autowired
	LostAndFoundConfiguration configuration;

	@PostMapping("/lost/{login:.*}")
	public LostFoundResponseDto newLostPet(@RequestBody LostFoundRequestDto lostFoundRequestDto,
			@PathVariable("login") String author) {
		return lostAndFoundService.newLostFoundPet(lostFoundRequestDto, author, false);
	}

	@PostMapping("/find/{login:.*}")
	public LostFoundResponseDto newFoundPet(@RequestBody LostFoundRequestDto lostFoundRequestDto,
			@PathVariable("login") String author) {
		return lostAndFoundService.newLostFoundPet(lostFoundRequestDto, author, true);
	}

	@GetMapping("/losts")
	public Page<LostFoundResponseDto> lostPets(@RequestParam int page, int size) {
		return lostAndFoundService.lostFoundPets(page, configuration.getPageSize(), false);
	}

	@GetMapping("/finds")
	public Page<LostFoundResponseDto> foundPets(@RequestParam int page, int size) {
		return lostAndFoundService.lostFoundPets(page, configuration.getPageSize(), true);
	}

	@GetMapping("/{id}")
	public LostFoundResponseDto findPostById(@PathVariable String id) {
		return lostAndFoundService.findPostById(id);
	}

	@PostMapping("/lost/filter")
	public Page<LostFoundResponseDto> searchByInfoLostPets(@RequestBody SearchByInfoRequestDto searchRequestDto,
			@RequestParam int page) {
		return lostAndFoundService.searchByInfoLostFoundPets(searchRequestDto, false, configuration.getRadius(), page,
				configuration.getPageSize());
	}

	@PostMapping("/find/filter")
	public Page<LostFoundResponseDto> searchByInfoFoundPets(@RequestBody SearchByInfoRequestDto searchRequestDto,
			@RequestParam int page) {
		return lostAndFoundService.searchByInfoLostFoundPets(searchRequestDto, true, configuration.getRadius(), page,
				configuration.getPageSize());
	}

	@PutMapping("/{id}")
	public LostFoundResponseDto editLostFound(@RequestBody LostFoundRequestDto lostFoundRequestDto, @PathVariable String id) {
		return lostAndFoundService.editLostFound(lostFoundRequestDto, id);
	}

	@DeleteMapping("/{id}")
	public LostFoundResponseDto deleteLostFound(@PathVariable String id) {
		return lostAndFoundService.deleteLostFound(id);
	}

	@GetMapping("/tagsandcolors")
	public Set<String> tagsOfPictureAndColors(@RequestParam String imageUrl, @PathVariable String lang) {
		return lostAndFoundService.tagsAndColorsOfPicture(imageUrl, lang);
	}

//	@PostMapping("/posts/allid")
//	public Set<LostFoundResponseDto> findPostsByAllId(@RequestBody Set<String> allId){
//		return lostAndFoundService.findPostsByAllId(allId);
//	}

}
