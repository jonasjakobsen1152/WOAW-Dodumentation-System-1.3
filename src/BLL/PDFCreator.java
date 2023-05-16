package BLL;

import BE.Documentation;
import BE.JobImage;
import BLL.UTIL.PDFStrategies.PDFStrategy;

import java.io.IOException;
import java.util.ArrayList;

class PDFCreator {
    private PDFStrategy strategy;

    public PDFCreator(PDFStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PDFStrategy strategy) {
        this.strategy = strategy;
    }

    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        strategy.printPDF(allNotes, allImages);
    }
}