package basilseed.ui;

import basilseed.BasilSeed;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private BasilSeed basilSeed;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image basilSeedImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setBasilSeed(BasilSeed basilSeed) {
        this.basilSeed = basilSeed;
        dialogContainer.getChildren().add(
                DialogBox.getBasilSeedDialog(basilSeed.getGreeting(), basilSeedImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().equals("bye")) {
            dialogContainer.getChildren().add(
                    DialogBox.getBasilSeedDialog(basilSeed.getFarewell(), basilSeedImage)
            );
            Platform.exit();
            return;
        }
        String response = basilSeed.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBasilSeedDialog(response, basilSeedImage)
        );
        userInput.clear();
    }
}
