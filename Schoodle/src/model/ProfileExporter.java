package model;

public class ProfileExporter {

    private Users user;

    public ProfileExporter(Users user) {
        this.user = user;
    }

    public String retrieveExportSettings() {
        return String.format("%d!%s!%d", user.getUserId(), user.getFullName(), user.getFullName().length());
    }

}
