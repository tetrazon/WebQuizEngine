package engine.repository;

import engine.entity.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompletionRepository extends CrudRepository<Completion, Integer> {
    Page findAllByUserName(String userName, Pageable pageable);
    List findAllByUserName(String userName);
}
