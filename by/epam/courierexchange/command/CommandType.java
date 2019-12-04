package by.epam.courierexchange.command;

import by.epam.courierexchange.command.impl.*;

public enum CommandType {
    ADMIN_PROCESSING_OFFER {
        {
            this.command = new AdminProcessingOfferCommand();
        }
    },
    ADMIN_SHOW_USER {
        {
            this.command = new AdminShowUserCommand();
        }
    },
    ADMIN_SHOW_OFFER {
        {
            this.command = new AdminShowOfferCommand();
        }
    },
    CABINET {
        {
            this.command = new CabinetCommand();
        }
    },
    CLIENT_CANCEL_RESPOND {
        {
            this.command = new ClientCancelRespondCommand();
        }
    },
    CLIENT_DELETE_OFFER {
        {
            this.command = new ClientDeleteOfferCommand();
        }
    },
    CLIENT_FINISH_DEAL {
        {
            this.command = new ClientFinishDealCommand();
        }
    },
    CLIENT_PROCESSING_RESPOND {
        {
            this.command = new ClientProcessingRespondCommand();
        }
    },
    CLIENT_SEND_OFFER {
        {
            this.command = new ClientSendOfferCommand();
        }
    },
    CLIENT_SEND_RESPOND {
        {
            this.command = new ClientSendRespondCommand();
        }
    },
    CLIENT_SHOW_DEAL {
        {
            this.command = new ClientShowDealCommand();
        }
    },
    CLIENT_SHOW_OFFER {
        {
            this.command = new ClientShowOfferCommand();
        }
    },
    CLIENT_SHOW_COURIER_OFFER {
        {
            this.command = new ClientShowCourierOfferCommand();
        }
    },
    COURIER_CANCEL_RESPOND {
        {
            this.command = new CourierCancelRespondCommand();
        }
    },
    COURIER_DELETE_OFFER {
        {
            this.command = new CourierDeleteOfferCommand();
        }
    },
    COURIER_FINISH_DEAL {
        {
            this.command = new CourierFinishDealCommand();
        }
    },
    COURIER_PROCESSING_RESPOND {
        {
            this.command = new CourierProcessingRespondCommand();
        }
    },
    COURIER_RESPOND_CLIENT_OFFER {
        {
            this.command = new CourierRespondClientOfferCommand();
        }
    },
    COURIER_SEND_OFFER {
        {
            this.command = new CourierSendOfferCommand();
        }
    },
    COURIER_SHOW_DEAL {
        {
            this.command = new CourierShowDealCommand();
        }
    },
    COURIER_SHOW_CLIENT_OFFER {
        {
            this.command = new CourierShowClientOfferCommand();
        }
    },
    COURIER_SHOW_OFFER {
        {
            this.command = new CourierShowOfferCommand();
        }
    },
    EDIT_PROFILE{
        {
            this.command = new EditProfileCommand();
        }
    },
    LOCALE {
        {
            this.command = new LocaleCommand();
        }
    },
    LOGIN{
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT{
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION{
        {
            this.command = new RegistrationCommand();
        }
    };

    Command command;

    public Command getCommand() {
        return command;
    }
}
