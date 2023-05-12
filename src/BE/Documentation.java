package BE;

public class Documentation {
    public int id;
    public String title;
    public String publicText;
    public String privateText;
    public int jobId;

    public Documentation(int id, String title, String publicText, String privateText, int jobId) {
        this.id = id;
        this.title = title;
        this.publicText = publicText;
        this.privateText = privateText;
        this.jobId = jobId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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

    @Override
    public String toString() {
        return "Documentation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicText='" + publicText + '\'' +
                ", privateText='" + privateText + '\'' +
                ", jobId=" + jobId +
                '}';
    }
}
