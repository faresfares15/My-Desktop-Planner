module esi.tp_poo_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens esi.tp_poo_final to javafx.fxml;
    exports esi.tp_poo_final;
    exports Controllers;
    exports Models;
    exports Databases;
    exports Trash.CalendarComponent;
    exports Trash.TaskComponent;
    exports Controllers.CalendarControllers;
    exports Controllers.TaskControllers;
    exports Models.TaskModel;
    exports Models.ProjectModel;
}