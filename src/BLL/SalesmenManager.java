package BLL;

import BE.Customer;
import BE.Documentation;
import BE.Job;
import BE.JobImage;
import BLL.UTIL.CustomerSearcher;
import BLL.UTIL.PDFCreator;
import BLL.UTIL.PDFStrategies.PrivatePDFStrategy;
import BLL.UTIL.PDFStrategies.PublicPDFStrategy;
import DAL.DB.SalesMenDAO_DB;
import DAL.ICustomerDAO;
import DAL.ISalesmenDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesmenManager {

    private ISalesmenDAO salesmenDAO;

    private ArrayList<Customer> allCustomers;

    private CustomerSearcher customerSearcher;

    PDFCreator pdfCreator;

    public SalesmenManager(){
        salesmenDAO = new SalesMenDAO_DB();
        allCustomers = new ArrayList<>();
        customerSearcher = new CustomerSearcher();

        pdfCreator = new PDFCreator(new PrivatePDFStrategy());
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        allCustomers = salesmenDAO.getAllCustomers();
        return allCustomers;
    }

    public ArrayList<Job> getAllJobs(Customer selectedCustomer) throws SQLException {
        return salesmenDAO.getAllJobs(selectedCustomer);
    }

    public List<Customer> searchCustomers(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }

    public void setPDFStrategy(String privacy) {
        if(privacy.equals("private")){
            pdfCreator.setStrategy(new PrivatePDFStrategy());
        }
        else {
            pdfCreator.setStrategy(new PublicPDFStrategy());
        }
    }

    public void printPDF(Job selectedJob) throws SQLException, IOException {
        ArrayList<JobImage> allImages = salesmenDAO.getAllJobImages(selectedJob);
        ArrayList<Documentation> allNotes = salesmenDAO.getallDocuments(selectedJob);

        pdfCreator.printPDF(allNotes,allImages);
    }

}
