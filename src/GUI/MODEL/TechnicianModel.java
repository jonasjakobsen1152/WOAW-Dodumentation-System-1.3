package GUI.MODEL;

import BLL.TechnicianManager;

public class TechnicianModel {
    TechnicianManager technicianManager;
    private static TechnicianModel instance;

    private TechnicianModel(){
        technicianManager = new TechnicianManager();
    }

    public static TechnicianModel getInstance(){
        if(instance == null){
            instance = new TechnicianModel();
        }
        return  instance;
    }
}
