module esi.tp_poo_final {
    requires javafx.controls;
    requires javafx.fxml;

    opens esi.tp_poo_final to javafx.fxml;
    opens Controllers.TaskControllers to javafx.fxml;
    opens Controllers.UserControllers to javafx.fxml;
    opens Controllers.CalendarControllers to javafx.fxml;
    opens Controllers.FreeSlotControllers to javafx.fxml;
    opens Controllers.ProjectControllers to javafx.fxml;
    opens Controllers.CategoryControllers to javafx.fxml;

    exports esi.tp_poo_final;

    //Exporting models
    exports Models.Calendar;
    exports Models.Day;
    exports Models.FreeSlot;
    exports Models.Project;
    exports Models.Task;
    exports Models.User;
    exports Models.Category;

    //Exporting databases
    exports Databases;

    //Exporting Controllers
    exports Controllers.CalendarControllers;
    exports Controllers.TaskControllers;
    exports Controllers.UserControllers;

    exports Exceptions;
    exports Controllers.FreeSlotControllers;
    exports Controllers.CategoryControllers;
}