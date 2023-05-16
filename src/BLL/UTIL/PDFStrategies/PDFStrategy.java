package BLL.UTIL.PDFStrategies;

import BE.Documentation;
import BE.ImageAndTitle;
import BE.JobImage;

import java.io.IOException;
import java.util.ArrayList;

public interface PDFStrategy {
    void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException;

     ArrayList<ImageAndTitle> convertImages(ArrayList<JobImage> allImages);
}
