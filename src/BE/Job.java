package BE;

public class Job {
    int id;
    String title;
    int customerId;

    public Job(int id, String title, int customerId) {
        this.id = id;
        this.title = title;
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
