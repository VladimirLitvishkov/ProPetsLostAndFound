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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@Entity
@EqualsAndHashCode(of = "id")

public class LostFound implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue
//	Long id;
	@Id
	String id;

	Boolean typePost;

	String type;
	Location location;
//	@ElementCollection
	List<String> photos;
//	@ElementCollection
	Set<String> tags;
	String author;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Default
	LocalDateTime datePost = LocalDateTime.now();

}
