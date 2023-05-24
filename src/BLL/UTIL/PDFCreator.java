package BLL.UTIL;

import BE.Documentation;
import BE.JobImage;
import BLL.UTIL.PDFStrategies.PDFStrategy;

import java.io.IOException;
import java.util.ArrayList;

public class PDFCreator {
    private PDFStrategy strategy;

    public PDFCreator(PDFStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PDFStrategy strategy) {
        this.strategy = strategy; //Used to set the chosen strategy
    }


    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        strategy.printPDF(allNotes, allImages); // Uses the set strategy to use the method printPDF.
    }
}