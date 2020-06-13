package propets.dto.lostandfound;

import java.util.Set;

import lombok.Getter;
import propets.model.lostandfound.Address;

@Getter
public class SearchByInfoRequestDto {

	String type;

	String breed;

	String sex;

	Address address;

	Set<String> tags;

}
