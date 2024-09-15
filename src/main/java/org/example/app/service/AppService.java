package org.example.app.service;


import org.example.app.controller.UserController;
import org.example.app.exceptions.OptionException;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Message;
import org.example.app.view.AppView;

public class AppService {

    UserController controller = new UserController();

    public void filterChoice(int option) {
        switch (option) {
            case 1 -> controller.createContact();
            case 2 -> controller.readContacts();
            case 3 -> controller.updateContact();
            case 4 -> controller.deleteContact();
            case 5 -> controller.readContactById();
            case 0 -> new AppView().getOutput(Integer.toString(option));
            default -> {
                try {
                    throw new OptionException(Message.INCORRECT_OPTION_MSG.getMessage());
                } catch (OptionException e) {
                    new AppView().getOutput(e.getMessage());
                    AppStarter.startApp();
                }
            }
        }
    }
}
