package propets.service.lostandfound;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import propets.configuration.lostandfound.LostAndFoundConfiguration;
import propets.dao.lostandfound.LostAndFoundRepository;
import propets.dto.lostandfound.LostFoundRequestDto;
import propets.dto.lostandfound.LostFoundResponseDto;
import propets.dto.lostandfound.ResponseDto;
import propets.dto.lostandfound.SearchByInfoRequestDto;
import propets.exceptions.lostandfound.LostFoundIdNotFoundException;
import propets.model.lostandfound.LostFound;

@Service
@EnableBinding(Source.class)
public class LostAndFoundServiceImpl implements LostAndFoundService {

	@Autowired
	LostAndFoundConfiguration configuration;

	@Autowired
	LostAndFoundRepository lostFoundRepository;

	@Autowired
	MessageChannel output;

	@Override
	public LostFoundResponseDto newLostFoundPet(LostFoundRequestDto lostFoundRequestDto, String author,
			boolean lostFound) {
		LostFound model = LostFound.builder().type(lostFoundRequestDto.getType()).typePost(lostFound).userLogin(author)
				.userName(lostFoundRequestDto.getUserName()).avatar(lostFoundRequestDto.getAvatar())
				.address(lostFoundRequestDto.getAddress()).coordinates(lostFoundRequestDto.getCoordinates())
				.photos(lostFoundRequestDto.getPhotos()).breed(lostFoundRequestDto.getBreed())
				.sex(lostFoundRequestDto.getSex()).tags(lostFoundRequestDto.getTags()).build();
		lostFoundRepository.save(model);
		LostFoundResponseDto result = buildResponseDto(model);
		output.send(MessageBuilder.withPayload(result).build());
		return result;
	}

	private LostFoundResponseDto buildResponseDto(LostFound model) {
		return LostFoundResponseDto.builder().id(model.getId()).userLogin(model.getUserLogin())
				.userName(model.getUserName()).avatar(model.getAvatar()).datePost(model.getDatePost())
				.type(model.getType()).typePost(model.getTypePost()).tags(model.getTags()).photos(model.getPhotos())
				.breed(model.getBreed()).sex(model.getSex()).address(model.getAddress())
				.coordinates(model.getCoordinates()).build();
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
	public LostFoundResponseDto findPostById(String id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		return buildResponseDto(model);
	}

	@Override
	public Page<LostFoundResponseDto> searchByInfoLostFoundPets(SearchByInfoRequestDto searchRequestDto,
			boolean lostFound, int radius, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size, Direction.DESC, "id");

		return null;
	}

	@Override
	public LostFoundResponseDto editLostFound(LostFoundRequestDto lostFoundRequestDto, String id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		if (lostFoundRequestDto.getType() != null && !lostFoundRequestDto.getType().isEmpty()) {
			model.setType(lostFoundRequestDto.getType());
		}
		if (lostFoundRequestDto.getAddress() != null) {
			model.setAddress(lostFoundRequestDto.getAddress());
		}
		if (lostFoundRequestDto.getCoordinates() != null) {
			model.setCoordinates(lostFoundRequestDto.getCoordinates());
		}
		if (lostFoundRequestDto.getPhotos() != null && !lostFoundRequestDto.getPhotos().isEmpty()) {
			model.setPhotos(lostFoundRequestDto.getPhotos());
		}
		if (lostFoundRequestDto.getTags() != null && !lostFoundRequestDto.getTags().isEmpty()) {
			model.setTags(lostFoundRequestDto.getTags());
		}
		if (lostFoundRequestDto.getBreed() != null && !lostFoundRequestDto.getBreed().isEmpty()) {
			model.setBreed(lostFoundRequestDto.getBreed());
		}
		if (lostFoundRequestDto.getSex() != null && !lostFoundRequestDto.getSex().isEmpty()) {
			model.setSex(lostFoundRequestDto.getSex());
		}
		lostFoundRepository.save(model);
		LostFoundResponseDto result = buildResponseDto(model);
		output.send(MessageBuilder.withPayload(result).build());
		return result;
	}

	@Override
	public LostFoundResponseDto deleteLostFound(String id) {
		LostFound model = lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException());
		LostFoundResponseDto response = buildResponseDto(model);
		lostFoundRepository.deleteById(id);
		RestTemplate restTemplate = new RestTemplate();
		URI urlDeleteInElastic = null;
		try {
			urlDeleteInElastic = new URI(configuration.getUrlDeleteInElastic()+id);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RequestEntity<LostFoundResponseDto> request = new RequestEntity<LostFoundResponseDto>(response,
				HttpMethod.DELETE, urlDeleteInElastic);
		restTemplate.exchange(request, String.class);
		return response;
	}

	@Override
	public Set<String> tagsAndColorsOfPicture(String imageUrl, String language) {
		// TODO
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
		RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, builderTags.build().toUri());
		ResponseEntity<ResponseDto> responseTags = restTemplate.exchange(request, ResponseDto.class);
		Set<String> tags = responseTags.getBody().getResult().getTags().stream().map(t -> t.getTag().get(language))
				.collect(Collectors.toSet());
		UriComponentsBuilder builderColors = UriComponentsBuilder.fromHttpUrl(urlColors).queryParam("image_url",
				imageUrl);
		request = new RequestEntity<>(headers, HttpMethod.GET, builderColors.build().toUri());
		ResponseEntity<ResponseDto> responseColors = restTemplate.exchange(request, ResponseDto.class);
		Set<String> colors = responseColors.getBody().getResult().getColors().getForeground_colors().stream()
				.map(imgC -> imgC.getHtml_code()).collect(Collectors.toSet());
		tags.addAll(colors);
		return tags;
	}

//	@Override
//	public Set<LostFoundResponseDto> findPostsByAllId(Set<String> allId) {
//		return allId.stream()
//				.map((id) -> buildResponseDto(
//						lostFoundRepository.findById(id).orElseThrow(() -> new LostFoundIdNotFoundException())))
//				.collect(Collectors.toSet());
//	}

}
