package by.zhuk.buber.model;

public class Article {

    private String name;
    private String intro;
    private String body;
    private String codeExample;

    public Article() {
    }

    public Article(String name, String intro, String body, String codeExample) {
        this.name = name;
        this.intro = intro;
        this.body = body;
        this.codeExample = codeExample;
    }

    public String getName() {
        return name;
    }

    public String getIntro() {
        return intro;
    }

    public String getBody() {
        return body;
    }

    public String getCodeExample() {
        return codeExample;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCodeExample(String codeExample) {
        this.codeExample = codeExample;
    }

    @Override
    public String toString() {
        return name;
    }
}