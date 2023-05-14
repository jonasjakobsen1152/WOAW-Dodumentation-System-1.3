package BE;

public class Image {
    int id;
    String title;
    String filePath;
    int jobId;
    String privacy;

    public Image(int id, String title, String filePath, int jobId, String privacy) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.jobId = jobId;
        this.privacy = privacy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
