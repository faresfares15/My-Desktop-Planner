module esi.tp_poo_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens esi.tp_poo_final to javafx.fxml;
    exports esi.tp_poo_final;
    exports Controllers;
    //Exporting models
    exports Models.Calendar;
    exports Models.Day;
    exports Models.FreeSlot;
    exports Models.Project;
    exports Models.Task;
    exports Models.User;

    //Exporting Controllers
    exports Controllers.CalendarControllers;

    exports Controllers.TaskControllers;
    exports Exceptions;
}