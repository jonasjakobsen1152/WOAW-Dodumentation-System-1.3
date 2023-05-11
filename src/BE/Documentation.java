package BE;

public class Documentation {
    public String title;
    public String publicText;
    public String privateText;

    public Documentation(String title, String publicText, String privateText) {
        this.title = title;
        this.publicText = publicText;
        this.privateText = privateText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicText() {
        return publicText;
    }

    public void setPublicText(String publicText) {
        this.publicText = publicText;
    }

    public String getPrivateText() {
        return privateText;
    }

    public void setPrivateText(String privateText) {
        this.privateText = privateText;
    }
}
