package propets.dto.lostandfound;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import propets.model.lostandfound.Location;

@Getter
public class LostFoundRequestDto {
	
	String type;
	
	Location location;
	
	List<String> photos;
	
	Set<String> tags;

}
