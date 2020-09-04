package engine.controller;

import engine.entity.CheckAnswer;
import engine.entity.Completion;
import engine.entity.Quiz;
import engine.entity.Solution;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.service.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizRepository quizRepository;

    @Autowired
    private CompletionService completionService;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        return optionalQuiz
                .map(quiz -> ResponseEntity.ok().body(quiz))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuiz(@PathVariable int id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent() && !userName.equals(optionalQuiz.get().getUserName())) {
            return ResponseEntity.status(403).body("this user can't delete the quiz #" + id);
        }
        return optionalQuiz
                .map(quiz -> {
                    quizRepository.delete(quiz);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Quiz createQuiz (@Valid @RequestBody Quiz quizFromRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        quizFromRequest.setUserName(userName);
        return quizRepository.save(quizFromRequest);
    }

    @GetMapping
    public ResponseEntity getAllQuizzes(@RequestParam Optional<Integer> page,
                                                    @RequestParam Optional<Integer> size) {
        Page pageResult = quizRepository.findAll(PageRequest
                .of(page.orElse(0), size.orElse(10)));
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
//        Optional<List<Quiz>> optionalQuizList = Optional.of(quizRepository.findAll());
//        return optionalQuizList
//                .map(list -> ResponseEntity.ok().body(pageResult))
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/completed")
    public Page getCompletedQuizzes() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //return completionService.findAllByUserName(userName);
        //List completionsList = completionRepository.findAllByUserName(userName);
        return completionService.findAllByUserName(userName, PageRequest.of(0, 5));
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<CheckAnswer> solveQuiz(@PathVariable int id, @RequestBody Solution solution) {
        final CheckAnswer rightAnswer = new CheckAnswer(true, "Congratulations, you're right!");
        final CheckAnswer wrongAnswer = new CheckAnswer(false, "Wrong answer! Please, try again.");
        Optional<Quiz> quizById = quizRepository.findById(id);
        if (quizById.isPresent()){
            if ((quizById.get().getAnswer() == null || quizById.get().getAnswer().isEmpty())
                    && (solution.getAnswer() == null || solution.getAnswer().isEmpty())) {
                completionService.saveCompletion(id);
                return ResponseEntity.ok().body(rightAnswer);
            }
            if ((solution.getAnswer().isEmpty() && !quizById.get().getAnswer().isEmpty())
                    || (!solution.getAnswer().isEmpty() && quizById.get().getAnswer() == null)){
                return ResponseEntity.ok(wrongAnswer);
            }
        }
        ResponseEntity responseEntity =  quizById
                .map(quiz -> solution.getAnswer().containsAll(quiz.getAnswer()) && solution.getAnswer().size() == quiz.getAnswer().size()
                        ? ResponseEntity.ok().body(rightAnswer)
                        : ResponseEntity.ok(wrongAnswer))
                .orElse(ResponseEntity.notFound().build());
        if (responseEntity.getStatusCode().value() == 200) {
            completionService.saveCompletion(id);
        }
        return responseEntity;
    }

}