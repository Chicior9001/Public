package lab11.laboratoria12;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField textField;

    @FXML
    private TextArea outputArea;
    private Client client;

    @FXML
    private ListView<String> userList;

    @FXML
    protected void onSendButtonClick() {
        String message = textField.getText();
        if(!message.isEmpty()) {
            outputArea.appendText(message + "\n");
            client.setController(this);
            client.send(message);
        }
        textField.clear();
    }

    public void receive(String message) {
        outputArea.appendText(message + "\n");
    }

    public void setClient(Client client) {
        this.client = client;
        client.setController(this);
    }
}