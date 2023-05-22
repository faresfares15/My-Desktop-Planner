package Models.Task;

public enum Progress {
    CANCELED (0),
    DELAYED(0),
    NOT_REALIZED(0),
    IN_PROGRESS(0.5),
    COMPLETED(1);
    public final double pourcentage;

    Progress(double pourcentage) {
        this.pourcentage = pourcentage;
    }
}
