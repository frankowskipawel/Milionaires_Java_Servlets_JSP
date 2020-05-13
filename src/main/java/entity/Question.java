package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUE_ID")
    private Integer id;
    @Column(name = "QUE_TEXT")
    private String text;
    @Column(name = "QUE_ANSWER_A")
    private String answerA;
    @Column(name = "QUE_ANSWER_B")
    private String answerB;
    @Column(name = "QUE_ANSWER_C")
    private String answerC;
    @Column(name = "QUE_ANSWER_D")
    private String answerD;
    @Column(name = "QUE_CORRECT_ANSWER")
    private String correctAnswer;




    public Question() {
    }
}
