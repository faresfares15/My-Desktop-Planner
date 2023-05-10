module esi.tp_poo_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens esi.tp_poo_final to javafx.fxml;
    exports esi.tp_poo_final;

    //Exporting models
    exports Models.Calendar;
    exports Models.Day;
    exports Models.FreeSlot;
    exports Models.Project;
    exports Models.Task;
    exports Models.User;

    //Exporting databases
    exports Databases;

    //Exporting Controllers
    exports Controllers.CalendarControllers;
    exports Controllers.TaskControllers;

    //exporting views
    exports Views;

    exports Exceptions;
}