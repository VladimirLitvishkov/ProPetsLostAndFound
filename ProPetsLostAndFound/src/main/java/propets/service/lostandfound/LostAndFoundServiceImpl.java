package propets.service.lostandfound;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import propets.configuration.lostandfound.LostAndFoundConfiguration;
import propets.dao.lostandfound.LostAndFoundRepository;
import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;
import propets.dto.lostandfound.ResponseDto;
import propets.exceptions.lostandfound.LostFoundIdNotFoundException;
import propets.model.lostandfound.LostFound;

@Service
public class LostAndFoundServiceImpl implements LostAndFoundService {
	
	@Autowired
	LostAndFoundConfiguration configuration;

	@Autowired
	LostAndFoundRepository lostFoundRepository;

	@Override
	public String newLostFoundPet(LostFoundRequestDto lostFoundRequestDto, String author, boolean lostFound) {
		LostFound model = LostFound.builder().type(lostFoundRequestDto.getType()).typePost(lostFound).author(author)
				.location(lostFoundRequestDto.getLocation()).photos(lostFoundRequestDto.getPhotos())
				.tags(lostFoundRequestDto.getTags()).build();
		lostFoundRepository.save(model);
		return "buildResponseDto(model)";
	}

	private LostFoundResponseDto buildResponseDto(LostFound model) {
		return LostFoundResponseDto.builder().id(model.getId()).author(model.getAuthor()).datePost(model.getDatePost())
				.type(model.getType()).typePost(model.getTypePost()).tags(model.getTags()).photos(model.getPhotos())
				.location(model.getLocation()).build();
	}

	@Override
	public Page<LostFoundResponseDto> lostFoundPets(int page, int size, boolean lostFound) {
		Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");
		List<LostFound> list = lostFoundRepository.findByTypePost(lostFound, pageable);
		Page<LostFoundResponseDto> listResponse = new PageImpl<LostFoundResponseDto>(
				list.stream().map(this::buildResponseDto).collect(Collectors.toList()));
		return listResponse;
	}

	@Override
	public LostFoundResponseDto findPostById(long id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		return buildResponseDto(model);
	}

	@Override
	public Set<LostFoundResponseDto> searchByInfoLostFoundPets(LostFoundRequestDto lostFoundRequestDto,
			boolean lostFound, int radius, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editLostFound(LostFoundRequestDto lostFoundRequestDto, long id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		if (lostFoundRequestDto.getType() != null && !lostFoundRequestDto.getType().isEmpty()) {
			model.setType(lostFoundRequestDto.getType());
		}
		if (lostFoundRequestDto.getLocation() != null) {
			model.setLocation(lostFoundRequestDto.getLocation());
		}
		if (lostFoundRequestDto.getPhotos() != null && !lostFoundRequestDto.getPhotos().isEmpty()) {
			model.setPhotos(lostFoundRequestDto.getPhotos());
		}
		if (lostFoundRequestDto.getTags() != null && !lostFoundRequestDto.getTags().isEmpty()) {
			model.setTags(lostFoundRequestDto.getTags());
		}
		lostFoundRepository.save(model);
		return "buildResponseDto(model)";
	}

	@Override
	public LostFoundResponseDto deleteLostFound(Long id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		LostFoundResponseDto response = buildResponseDto(model);
		lostFoundRepository.deleteById(id);
		return response;
	}

	@Override
	public Set<String> tagsAndColorsOfPicture(String imageUrl, String language) {
		//TODO
//		String auth = "Basic YWNjXzIxM2M0ZTU1Y2U5MjJiOTo0NTJlYThhYzU5ZmNkZGViNjQxZTZjOGFkOWNhNDljNA==";
//		String urlTags = "https://api.imagga.com/v2/tags";
//		String urlColors = "https://api.imagga.com/v2/colors";
		String auth = configuration.getAuthorizationImagga();
		String urlTags = configuration.getRequestTags();
		String urlColors = configuration.getRequestColors();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", auth);
		UriComponentsBuilder builderTags = UriComponentsBuilder.fromHttpUrl(urlTags).queryParam("image_url", imageUrl)
				.queryParam("language", language).queryParam("threshold", 50);
		RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET,builderTags.build().toUri());
		ResponseEntity<ResponseDto> responseTags = restTemplate.exchange(request, ResponseDto.class);
		Set<String> tags = responseTags.getBody().getResult().getTags().stream()
				.map(t -> t.getTag().get(language)).collect(Collectors.toSet());
		UriComponentsBuilder builderColors = UriComponentsBuilder.fromHttpUrl(urlColors).queryParam("image_url", imageUrl);
		request = new RequestEntity<>(headers, HttpMethod.GET,builderColors.build().toUri());
		ResponseEntity<ResponseDto> responseColors = restTemplate.exchange(request, ResponseDto.class);
		Set<String> colors = responseColors.getBody().getResult().getColors().getForeground_colors().stream()
				.map(imgC -> imgC.getHtml_code()).collect(Collectors.toSet());
		tags.addAll(colors);
		return tags;
	}

}
