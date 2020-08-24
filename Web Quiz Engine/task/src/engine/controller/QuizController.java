package engine.controller;

import engine.repository.QuizRepository;
import engine.entity.CheckAnswer;
import engine.entity.Quiz;
import engine.entity.Solution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        return optionalQuiz
                .map(quiz -> ResponseEntity.ok().body(quiz))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/quizzes")
    public Quiz createQuiz (@Valid @RequestBody Quiz quizFromRequest) {

        return quizRepository.save(quizFromRequest);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        Optional<List<Quiz>> optionalQuizList = Optional.of(quizRepository.findAll());
        return optionalQuizList
                .map(list -> ResponseEntity.ok().body(list))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<CheckAnswer> solveQuiz(@PathVariable int id, @RequestBody Solution solution) {
        final CheckAnswer rightAnswer = new CheckAnswer(true, "Congratulations, you're right!");
        final CheckAnswer wrongAnswer = new CheckAnswer(false, "Wrong answer! Please, try again.");
        Optional<Quiz> quizById = quizRepository.findById(id);
        if (quizById.isPresent()){
            if ((quizById.get().getAnswer() == null || quizById.get().getAnswer().isEmpty())
                    && (solution.getAnswer() == null || solution.getAnswer().isEmpty())) {
                return ResponseEntity.ok().body(rightAnswer);
            }
            if ((solution.getAnswer().isEmpty() && !quizById.get().getAnswer().isEmpty())
                    || (!solution.getAnswer().isEmpty() && quizById.get().getAnswer() == null)){
                return ResponseEntity.ok(wrongAnswer);
            }
        }
        return quizById
                .map(quiz -> solution.getAnswer().containsAll(quiz.getAnswer()) && solution.getAnswer().size() == quiz.getAnswer().size()
                        ? ResponseEntity.ok().body(rightAnswer)
                        : ResponseEntity.ok(wrongAnswer))
                .orElse(ResponseEntity.notFound().build());
    }

}