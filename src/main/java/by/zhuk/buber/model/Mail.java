package by.zhuk.buber.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mail mail = (Mail) o;
        return Objects.equals(head, mail.head) &&
                Objects.equals(content, mail.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(head, content);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "head='" + head + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}