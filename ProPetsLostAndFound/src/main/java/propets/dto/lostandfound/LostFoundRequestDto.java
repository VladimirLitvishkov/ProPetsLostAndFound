package propets.dto.lostandfound;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import propets.model.lostandfound.Address;
import propets.model.lostandfound.Coordinates;

@Getter
public class LostFoundRequestDto {
	
	String type;
	
	String breed;
	
	String sex;
	
	Address address;
	
	Coordinates coordinates;
	
	List<String> photos;
	
	Set<String> tags;
	
	String userName;
	
	String avatar;

}
