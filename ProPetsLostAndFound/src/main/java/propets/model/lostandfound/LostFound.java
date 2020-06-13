package propets.model.lostandfound;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")

public class LostFound implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	String id;

	Boolean typePost;

	String type;
	String breed;
	String sex;
	Address address;
	Coordinates coordinates;
	@Singular("photos")
	List<String> photos;
	@Singular("tags")
	Set<String> tags;
	String userLogin;
	String userName;
	String avatar;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Default
	LocalDateTime datePost = LocalDateTime.now();

}
