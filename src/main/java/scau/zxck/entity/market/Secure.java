package scau.zxck.entity.market;

public class Secure {
    private String id;
    private String student_id;
    private String secure_question;
    private String secure_answer;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
