package BE;

public class Job {
    int id;
    String title;
    int userId;
    int customerId;

    public Job(int id, String title, int userId, int customerId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.customerId = customerId;

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", customerId=" + customerId +
                '}';
    }
}
