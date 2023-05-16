package BE;

import com.itextpdf.layout.element.Image;

import java.awt.*;

public class ImageAndTitle {
    private com.itextpdf.layout.element.Image image;
    private String title;

    public ImageAndTitle(com.itextpdf.layout.element.Image image, String title) {
        this.image = image;
        this.title = title;
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
}
