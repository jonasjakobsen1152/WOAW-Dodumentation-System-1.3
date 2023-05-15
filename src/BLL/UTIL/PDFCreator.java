package BLL.UTIL;

import BE.Documentation;
import BE.JobImage;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFCreator {

    private ArrayList<Image> images;
    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        //ArrayList<Image> convertedImages = convertImages(allImages); // Converts the images so they can be added to pdf

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                images = convertImages(allImages); // Converts the images so they can be added to pdf
            }
        });
        thread.start();

        //Creates a pdf called Ticket.pdf if already created it overrides the already existing Ticket.pdf
        PdfWriter pdfWriter = new PdfWriter("Output.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Creates a document for writing
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);


        for (Image image: images) {
        document.add(image);
        }

        for (Documentation note: allNotes) {
            Paragraph paragraph = new Paragraph();
            paragraph.add(note.title);
            paragraph.add(note.publicText);
            paragraph.add(note.privateText);
        }

        document.close();

        File file = new File("Output.pdf");
        Desktop.getDesktop().open(file);
    }

    private ArrayList<Image> convertImages(ArrayList<JobImage> allImages) {
        ArrayList<Image> images = new ArrayList<>();
        for (JobImage imageToConvert: allImages) {
            byte[] imageData = imageToConvert.getData();
            ImageData imageDataFactory = ImageDataFactory.create(imageData);
            Image image = new Image(imageDataFactory);
            images.add(image);
        }
        return images;
    }

    public static void main(String[] args){

    }

}
