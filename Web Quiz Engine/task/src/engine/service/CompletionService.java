package engine.service;

import engine.entity.Completion;
import engine.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompletionService {
    @Autowired
    private CompletionRepository completionRepository;


    public List findAllByUserName( String userName) {
        return completionRepository.findAllByUserName(userName);
    }

    public Page findAllByUserName(String userName, Pageable pageable) {
        return completionRepository.findAllByUserName(userName, pageable);
    }

    public void saveCompletion(int id) {
        Completion completion = new Completion();
        completion.setQuizId(id);
        completion.setCompletedAt(LocalDateTime.now());
        completion.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("new completion: " + completion.getUserName() + " at: " + completion.getCompletedAt());
        completionRepository.save(completion);
    }
}
