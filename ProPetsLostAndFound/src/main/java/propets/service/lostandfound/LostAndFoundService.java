package propets.service.lostandfound;

import java.util.Set;

import org.springframework.data.domain.Page;

import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;
import propets.dto.lostandfound.SearchByInfoRequestDto;

public interface LostAndFoundService {

	LostFoundResponseDto newLostFoundPet(LostFoundRequestDto lostFoundRequestDto, String author, boolean lostFound);

	Page<LostFoundResponseDto> lostFoundPets(int page, int size, boolean lostFound);

	LostFoundResponseDto findPostById(String id);

	Page<LostFoundResponseDto> searchByInfoLostFoundPets(SearchByInfoRequestDto searchRequestDto, boolean lostFound,
			int radius, int page, int size);

	LostFoundResponseDto editLostFound(LostFoundRequestDto lostFoundRequestDto, String id);

	LostFoundResponseDto deleteLostFound(String id);
	
	Set<String> tagsAndColorsOfPicture(String imageUrl, String language);
	
//	Set<LostFoundResponseDto> findPostsByAllId(Set<String> allId);

}
