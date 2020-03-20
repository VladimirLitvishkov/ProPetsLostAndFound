package propets.model.lostandfound;

import java.io.Serializable;

//import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
//@Embeddable
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;

	String country;

	String city;

	String street;

	Integer building;

	Double latitude;

	Double longitude;

}
