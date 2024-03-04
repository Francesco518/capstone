package francescobuonocore.capstone.services;

import francescobuonocore.capstone.entities.Quiz;
import francescobuonocore.capstone.entities.User;
import francescobuonocore.capstone.exceptions.NotFoundException;
import francescobuonocore.capstone.payloads.NewQuizPayload;
import francescobuonocore.capstone.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UsersService usersService;

    public List<Quiz> getQuizzes() {
        return this.quizRepository.findAll();
    }
    public Quiz findById(long id) {
        return this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Quiz save(NewQuizPayload body) {
        User user = usersService.findById(body.getUserId());
        Quiz quiz = new Quiz();
        quiz.setTextQuestion(body.getTextQuestion());
        quiz.setOptionsAnswer(body.getOptionsAnswer());
        quiz.setCorrectAnswerIndex(body.getCorrectAnswerIndex());
        return this.quizRepository.save(quiz);
    }
    public Quiz findAndUpdate(long id, NewQuizPayload newQuiz) {
        Quiz found = this.findById(id);
        found.setTextQuestion(newQuiz.getTextQuestion());
        found.setOptionsAnswer(newQuiz.getOptionsAnswer());
        found.setCorrectAnswerIndex(newQuiz.getCorrectAnswerIndex());

        return quizRepository.save(found);
    }
    public void findAndDelete(long id) {
        Quiz found = this.findById(id);
        this.quizRepository.delete(found);
    }
}
