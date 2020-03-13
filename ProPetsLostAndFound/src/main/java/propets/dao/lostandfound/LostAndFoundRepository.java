package propets.dao.lostandfound;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import propets.model.lostandfound.LostFound;

public interface LostAndFoundRepository extends JpaRepository<LostFound, Long> {
	
	List<LostFound> findByTypePost(Boolean typePost, Pageable pageable);
	
}
