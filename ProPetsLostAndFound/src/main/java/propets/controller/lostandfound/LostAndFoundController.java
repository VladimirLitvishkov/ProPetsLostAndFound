package propets.controller.lostandfound;

import java.security.Principal;
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

import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;
import propets.service.lostandfound.LostAndFoundService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/{lang}/v1")
public class LostAndFoundController {

	@Autowired
	LostAndFoundService lostAndFoundService;

	@PostMapping("/lost")
	public String newLostPet(@RequestBody LostFoundRequestDto lostFoundRequestDto, Principal author) {
		return lostAndFoundService.newLostFoundPet(lostFoundRequestDto, author.getName(), false);
	}

	@PostMapping("/find")
	public String newFoundPet(@RequestBody LostFoundRequestDto lostFoundRequestDto, Principal author) {
		return lostAndFoundService.newLostFoundPet(lostFoundRequestDto, author.getName(), true);
	}

	@GetMapping("/losts")
	public Page<LostFoundResponseDto> lostPets(@RequestParam int page, @RequestParam int size) {
		return lostAndFoundService.lostFoundPets(page, size, false);
	}

	@GetMapping("/finds")
	public Page<LostFoundResponseDto> foundPets(@RequestParam int page, @RequestParam int size) {
		return lostAndFoundService.lostFoundPets(page, size, true);
	}

	@GetMapping("/{id}")
	public LostFoundResponseDto findPostById(@PathVariable long id) {
		return lostAndFoundService.findPostById(id);
	}

	@PostMapping("/lost/filter")
	public Set<LostFoundResponseDto> searchByInfoLostPets(@RequestBody LostFoundRequestDto lostFoundRequestDto,
			@RequestParam int radius, @RequestParam int page, @RequestParam int size) {
		return lostAndFoundService.searchByInfoLostFoundPets(lostFoundRequestDto, false, radius, page, size);
	}

	@PostMapping("/find/filter")
	public Set<LostFoundResponseDto> searchByInfoFoundPets(@RequestBody LostFoundRequestDto lostFoundRequestDto,
			@RequestParam int radius, @RequestParam int page, @RequestParam int size) {
		return lostAndFoundService.searchByInfoLostFoundPets(lostFoundRequestDto, true, radius, page, size);
	}

	@PutMapping("/{id}")
	public String editLostFound(@RequestBody LostFoundRequestDto lostFoundRequestDto, @PathVariable long id) {
		return lostAndFoundService.editLostFound(lostFoundRequestDto, id);
	}

	@DeleteMapping("/{id}")
	public LostFoundResponseDto deleteLostFound(@PathVariable long id) {
		return lostAndFoundService.deleteLostFound(id);
	}

	@GetMapping("/tagsandcolors")
	public Set<String> tagsOfPictureAndColors(@RequestParam String imageUrl, @PathVariable String lang) {
		return lostAndFoundService.tagsAndColorsOfPicture(imageUrl, lang);
	}

}
