package ru.kalashnikofffs.eCare.utility;

public enum PageName {
    HOME("Home"),
    PROFILE("Profile"),
    EDIT_PROFILE("Edit profile"),
    CONTRACTS("Contracts"),
    VIEW_CONTRACT("Contract"),
    CHANGE_TARIFF("Choose tariff"),
    CHOOSE_OPTIONS("Choose options"),
    CLIENTS("Clients"),
    VIEW_CLIENT("Client"),
    NEW_CONTRACT("New contract"),
    TARIFFS("Tariffs"),
    VIEW_TARIFF("Tariff"),
    NEW_TARIFF("New tariff"),
    NEW_OPTION("New option"),
    VIEW_OPTION("Option"),
    EDIT_OPTION("Edit option");

    private String title;

    PageName(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
