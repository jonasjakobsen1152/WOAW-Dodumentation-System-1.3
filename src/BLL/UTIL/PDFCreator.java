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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PDFCreator {

    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        ArrayList<ImageAndTitle> convertedImages = convertImages(allImages); // Converts the images so they can be added to pdf

        //Creates a pdf called Ticket.pdf if already created it overrides the already existing Ticket.pdf
        PdfWriter pdfWriter = new PdfWriter("Output.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Creates a document for writing
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        float[] textWidth = {400f};
        Table textTable = new Table(textWidth);
        textTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        int textCounter = 0;
        for (Documentation note: allNotes) {
            textCounter++;
            Paragraph p1 = new Paragraph(textCounter + ". "+ note.title).setBold().setFontSize(15);
            Paragraph p2 = new Paragraph( note.publicText + "\r\n");
            Cell cell = new Cell();
            cell.add(p1);
            cell.add(p2);
            Paragraph privateInformation = new Paragraph("Private PDF information for note nr. " + textCounter + ":");
            privateInformation.setFontSize(10);
            Paragraph privateNote = new Paragraph( note.privateText);
            Cell privateCell = new Cell();
            privateCell.add(privateInformation);
            privateCell.add(privateNote);

            textTable.addCell(cell);
            textTable.addCell(privateCell);
        }
        document.add(textTable);

        float[] width = {100f,400f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);

        int counter = 0;
        for (ImageAndTitle imageAndTitle: convertedImages) {
            counter++;
            Paragraph paragraph = new Paragraph(counter + ". " + imageAndTitle.getTitle());
            paragraph.setBold().setFontSize(15);
            Cell cell = new Cell();
            cell.add(paragraph);

            table.addCell(cell);
            Image image = imageAndTitle.getImage().setAutoScale(true);
            table.addCell(new Cell().add(image));
        }
        document.add(table);

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

            ImageAndTitle imageAndTitle = new ImageAndTitle(image, imageToConvert.getTitle(), imageToConvert.getPrivacy());
            images.add(imageAndTitle);
        }
        return images;
    }

    public void printPrivatePDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        ArrayList<ImageAndTitle> convertedImages = convertImages(allImages); // Converts the images so they can be added to pdf

        //Creates a pdf called Ticket.pdf if already created it overrides the already existing Ticket.pdf
        PdfWriter pdfWriter = new PdfWriter("Public.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Creates a document for writing
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        float[] textWidth = {400f};
        Table textTable = new Table(textWidth);
        textTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        int textCounter = 0;
        for (Documentation note: allNotes) {
            textCounter++;
            Paragraph p1 = new Paragraph(textCounter + ". "+ note.title).setBold().setFontSize(15);
            Paragraph p2 = new Paragraph(note.publicText);
            Cell cell = new Cell();
            cell.add(p1);
            cell.add(p2);

            textTable.addCell(cell);
        }
        document.add(textTable);

        float[] width = {100f,400f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);

        int counter = 0;
        for (ImageAndTitle imageAndTitle: convertedImages) {
            System.out.println(imageAndTitle.getPrivacy());
            if(imageAndTitle.getPrivacy().equals("public")) {
                counter++;
                Paragraph paragraph = new Paragraph(counter + ". " + imageAndTitle.getTitle());
                paragraph.setBold().setFontSize(15);
                Cell cell = new Cell();
                cell.add(paragraph);

                table.addCell(new Cell().add(paragraph));
                Image image = imageAndTitle.getImage().setAutoScale(true);
                table.addCell(new Cell().add(image));
            }
        }
        document.add(table);

        document.close();

        File file = new File("Public.pdf");
        Desktop.getDesktop().open(file);
    }
}
