package propets.dto.lostandfound;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import propets.model.lostandfound.Location;

@Getter
public class LostFoundRequestDto {
	
	String type;
	
	String breed;
	
	String sex;
	
	Location location;
	
	List<String> photos;
	
	Set<String> tags;
	
	String userName;
	
	String avatar;

}
