package propets.dto.lostandfound;

import java.util.Map;

import lombok.Getter;

@Getter
public class TagDto {	
	Double confidence;
	Map<String, String> tag;

}
