package BLL.UTIL;

import BE.Documentation;
import BE.ImageAndTitle;
import BE.JobImage;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFCreator {

    private ArrayList<Image> images;
    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        ArrayList<ImageAndTitle> convertedImages = convertImages(allImages); // Converts the images so they can be added to pdf

        //Creates a pdf called Ticket.pdf if already created it overrides the already existing Ticket.pdf
        PdfWriter pdfWriter = new PdfWriter("Output.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Creates a document for writing
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        float[] width = {400f,400f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        for (ImageAndTitle imageAndTitle: convertedImages) {
            //table.addCell(new Cell().add(new Paragraph(imageAndTitle.getTitle())));
            //table.addCell(new Cell().add(image.scaleAbsolute(100,400)));
            Image image = imageAndTitle.getImage();
            Paragraph paragraph = new Paragraph(imageAndTitle.getTitle());
            Cell cell = new Cell();
            cell.add(paragraph);
            cell.add(image.scale(300,400));

            table.addCell(cell);
            document.add(table);
        }

        for (Documentation note: allNotes) {
            Paragraph p1 = new Paragraph(note.title).setBold().setFontSize(15);
            Paragraph p2 = new Paragraph( note.publicText + "\r\n" + note.privateText + "\r\n" + "\r\n");
            document.add(p1);
            document.add(p2);
        }

        document.close();

        File file = new File("Output.pdf");
        Desktop.getDesktop().open(file);
    }

    private ArrayList<ImageAndTitle> convertImages(ArrayList<JobImage> allImages) {
        ArrayList<ImageAndTitle> images = new ArrayList<>();
        for (JobImage imageToConvert: allImages) {
            byte[] imageData = imageToConvert.getData();
            ImageData imageDataFactory = ImageDataFactory.create(imageData);
            Image image = new Image(imageDataFactory);

            ImageAndTitle imageAndTitle = new ImageAndTitle(image, imageToConvert.getTitle());
            images.add(imageAndTitle);
        }
        return images;
    }

    public static void main(String[] args){

    }

}
