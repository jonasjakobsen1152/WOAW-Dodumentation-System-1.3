package GUI.MODEL;

import BE.Documentation;

import java.sql.SQLException;

public class DocumentationModel {
    private static DocumentationModel instance;

    public DocumentationModel(){

    }

    public static DocumentationModel getInstance() {
        if(instance == null){
            instance = new DocumentationModel();
        }
        return instance;
    }

    public void createDocumentation(Documentation documentation) {

    }
}
