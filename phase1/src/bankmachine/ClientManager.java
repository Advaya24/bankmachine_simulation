package bankmachine;

import bankmachine.Client;

import java.util.ArrayList;

public class ClientManager {
    private static ArrayList<Client> clients;

    public ClientManager(){}

    private void addClient(Client client) {
        if (clients.contains(client)) {
            System.out.print("This client is already in the system.");
        } else { clients.add(client); }
    }
}
