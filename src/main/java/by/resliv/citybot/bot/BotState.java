package by.resliv.citybot.bot;

public enum BotState {
    START,
    WATCH_CITIES("watch cities"),
    WATCH_CITY_INFO,
    MANAGE_CITIES("manage cities"),
    EDIT_CITY("edit city"),
    EDIT_CITY_NAME("edit city"),
    EDIT_CITY_DESCRIPTION("edit city"),
    DELETE_CITY("delete city"),
    DELETE_GET_CITY_NAME("delete city"),
    ADD_CITY("add city"),
    ADD_GET_CITY_NAME("add city"),
    GET_CITY_DESCRIPTION("add city");

    private String commandName;

    BotState() {
    }

    BotState(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}