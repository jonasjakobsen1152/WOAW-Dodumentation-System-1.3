package BLL.UTIL.PDFStrategies;

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

public class PublicPDFStrategy implements PDFStrategy {
    @Override
    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        //Creates a pdf called Output.pdf if already created it overrides the already existing pdf
        PdfWriter pdfWriter = new PdfWriter("Output.pdf");
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //Creates a document for writing
        pdfDocument.setDefaultPageSize(PageSize.A4);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        //Adds text to the table
        Table textTable = addTextToPublicTable(allNotes);
        document.add(textTable);

        //Adds images to the table
        Table imageTable = addImageToPublicTable(allImages);

        document.add(imageTable);

        //Closes the document
        document.close();

        //Opens the created PDF file
        File file = new File("Output.pdf");
        Desktop.getDesktop().open(file);
    }

    private Table addImageToPublicTable(ArrayList<JobImage> allImages) {
        ArrayList<ImageAndTitle> convertedImages = convertImages(allImages); // Converts the images so they can be added to pdf
        float[] width = {100f,400f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);

        int counter = 0;
        for (ImageAndTitle imageAndTitle: convertedImages) {
            if(imageAndTitle.getPrivacy().equals("public")) {
                counter++;
                //Adds a paragraph with the title of the image
                Paragraph paragraph = new Paragraph(counter + ". " + imageAndTitle.getTitle());
                paragraph.setBold().setFontSize(15);
                Cell cell = new Cell();
                cell.add(paragraph);

                table.addCell(new Cell().add(paragraph));
                //Adds the image to the table
                Image image = imageAndTitle.getImage().setAutoScale(true);
                table.addCell(new Cell().add(image));
            }
        }
        return table;
    }

    private Table addTextToPublicTable(ArrayList<Documentation> allNotes){
        float[] textWidth = {400f};
        Table textTable = new Table(textWidth);
        textTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        int textCounter = 0;
        for (Documentation note: allNotes) {
            textCounter++;
            //Adds a paragraph with the title of the note
            Paragraph p1 = new Paragraph(textCounter + ". "+ note.title).setBold().setFontSize(15);
            //Adds a paragraph with the public text of the note
            Paragraph p2 = new Paragraph(note.publicText);
            Cell cell = new Cell();
            cell.add(p1);
            cell.add(p2);

            textTable.addCell(cell);
        }
        return textTable;
    }

    @Override
    public ArrayList<ImageAndTitle> convertImages(ArrayList<JobImage> allImages) {
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
}
