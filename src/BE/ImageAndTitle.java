package BE;

import com.itextpdf.layout.element.Image;

import java.awt.*;

public class ImageAndTitle {
    private com.itextpdf.layout.element.Image image;
    private String title;

    private String privacy;

    public ImageAndTitle(Image image, String title, String privacy) {
        this.image = image;
        this.title = title;
        this.privacy = privacy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
