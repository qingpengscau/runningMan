package scau.zxck.entity.market;

import scau.zxck.base.dao.entity.Unique;
import scau.zxck.base.dao.annotation.Column;
import scau.zxck.base.dao.annotation.Table;
@Table(name = "secure_info")
public class Secure extends Unique {
    @Column(name="student_id")
    private String student_id;
    @Column(name="secure_question")
    private String secure_question;
    @Column(name="secure_answer")
    private String secure_answer;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSecure_question() {
        return secure_question;
    }

    public void setSecure_question(String secure_question) {
        this.secure_question = secure_question;
    }

    public String getSecure_answer() {
        return secure_answer;
    }

    public void setSecure_answer(String secure_answer) {
        this.secure_answer = secure_answer;
    }
}
