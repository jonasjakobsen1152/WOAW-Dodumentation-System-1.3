package BLL;

import BE.*;
import BLL.UTIL.*;
import DAL.DB.ProjectManagerDAO_DB;
import DAL.IProjectManagerDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerManager {
    private IProjectManagerDAO projectManagerDAO;

    private CustomerSearcher customerSearcher;

    private ArrayList<Customer> allCustomers;
    private ArrayList<User> allTechnicians;
    private ArrayList<User> allSalesmen;
    private ArrayList<Job> allDocuments;
    private TechnicianSearcher technicianSearcher;
    private SalesmenSearcher salesmenSearcher;
    private JobSearcher jobSearcher;

    public ProjectManagerManager(){
        projectManagerDAO = new ProjectManagerDAO_DB();

        allCustomers = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allSalesmen = new ArrayList<>();
        customerSearcher = new CustomerSearcher();
        technicianSearcher = new TechnicianSearcher();
        salesmenSearcher = new SalesmenSearcher();
        jobSearcher = new JobSearcher();
        allTechnicians = projectManagerDAO.getAllUsers("Technician");
        allSalesmen = projectManagerDAO.getAllUsers("Salesmen");



    }

    public ArrayList<User> getAllUsers(String role){
        return projectManagerDAO.getAllUsers(role);
    }

    public void deleteUser(User selectedUser) {
        projectManagerDAO.deleteUser(selectedUser);
    }

    public ArrayList<Job> getAllDocuments() {
        allDocuments = projectManagerDAO.getAllDocuments();
        return projectManagerDAO.getAllDocuments();
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException {
        allCustomers = projectManagerDAO.getAllCustomers();
        return allCustomers;
    }

    public void deletedDocument(Job selectedDocument) {
        projectManagerDAO.deleteDocument(selectedDocument);
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        projectManagerDAO.deleteCustomer(selectedCustomer);
    }

    public List<Customer> searchCustomer(String query) {
        List<Customer> searchResult = customerSearcher.search(allCustomers,query);
        return searchResult;
    }

    public List<User> searchTechnician(String query) {
        List<User> searchResult = technicianSearcher.search(allTechnicians,query);
        return searchResult;
    }

    public List<User> searchSalesmen(String query){
        List<User> searchResult = salesmenSearcher.search(allSalesmen,query);
        return searchResult;
    }
    public List<Job> searchJobs(String query){
        List<Job> searchResult = jobSearcher.search(allDocuments,query);
        return searchResult;
    }

    public void printPDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        PDFCreator pdfCreator = new PDFCreator();
        pdfCreator.printPDF(allNotes,allImages);
    }

    public void printPrivatePDF(ArrayList<Documentation> allNotes, ArrayList<JobImage> allImages) throws IOException {
        PDFCreator pdfCreator = new PDFCreator();
        pdfCreator.printPrivatePDF(allNotes,allImages);
    }
}
