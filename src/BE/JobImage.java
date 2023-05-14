package BE;

import java.util.Arrays;

public class JobImage {
    int id;
    String title;
    private byte[] data;
    int jobId;
    String privacy;

    public JobImage(int id, String title, byte[] data, int jobId, String privacy) {
        this.id = id;
        this.title = title;
        this.data = data;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "JobImage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", data=" + Arrays.toString(data) +
                ", jobId=" + jobId +
                ", privacy='" + privacy + '\'' +
                '}';
    }
}
