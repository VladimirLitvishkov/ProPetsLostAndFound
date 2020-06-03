package propets.service.lostandfound;

import java.util.Set;

import org.springframework.data.domain.Page;

import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;

public interface LostAndFoundService {

	LostFoundResponseDto newLostFoundPet(LostFoundRequestDto lostFoundRequestDto, String author, boolean lostFound);

	Page<LostFoundResponseDto> lostFoundPets(int page, int size, boolean lostFound);

	LostFoundResponseDto findPostById(String id);

	Set<LostFoundResponseDto> searchByInfoLostFoundPets(LostFoundRequestDto lostFoundRequestDto, boolean lostFound,
			int radius, int page, int size);

	String editLostFound(LostFoundRequestDto lostFoundRequestDto, String id);

	LostFoundResponseDto deleteLostFound(String id);
	
	Set<String> tagsAndColorsOfPicture(String imageUrl, String language);
	
//	Set<LostFoundResponseDto> findPostsByAllId(Set<String> allId);

}
