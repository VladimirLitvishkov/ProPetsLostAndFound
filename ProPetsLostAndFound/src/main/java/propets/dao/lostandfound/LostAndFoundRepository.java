package propets.dao.lostandfound;

import java.util.List;

import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import propets.model.lostandfound.LostFound;

//public interface LostAndFoundRepository extends JpaRepository<LostFound, Long> {
//	
//	List<LostFound> findByTypePost(Boolean typePost, Pageable pageable);
//	
//}

public interface LostAndFoundRepository extends MongoRepository<LostFound, Long> {
	
	List<LostFound> findByTypePost(Boolean typePost, Pageable pageable);
	
}
