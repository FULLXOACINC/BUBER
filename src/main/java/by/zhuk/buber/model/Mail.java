package by.zhuk.buber.model;

public class Mail {
    private String head;
    private String content;

    public Mail(String head, String content) {
        this.head = head;
        this.content = content;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}