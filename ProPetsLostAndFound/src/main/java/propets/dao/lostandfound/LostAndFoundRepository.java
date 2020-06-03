package propets.dao.lostandfound;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import propets.model.lostandfound.LostFound;

public interface LostAndFoundRepository extends MongoRepository<LostFound, String> {

	List<LostFound> findByTypePost(Boolean typePost, Pageable pageable);

}
