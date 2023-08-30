import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;

public class SyllabusPage implements Initializable {

    @FXML
    private Label greetingLabel;
    @FXML
    private ImageView profilePicture;
    @FXML
    private GridPane syllabusPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        greetingLabel.setText("Hello, "/*insertNameHere*/);

        Image profileImage = new Image("default_image.jpg");

        if (false/*profile picture is not null*/) {
            //profilePicture.setImage(userProfilePicture);
        } else {
            profilePicture.setImage(profileImage);
        }
        // sample data
        String[] syllabus1 = {"1", "Projects in Computer Science", "Spring", "2023", "2023-01-17", "2023-05-09"};
        String[] syllabus2 = {"2", "Projects in Computer Science 002", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus3 = {"2", "Projects in Computer Science 003", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus4 = {"2", "Projects in Computer Science 004", "Spring", "2023", "2023-01-18", "2023-05-09"};

        String[][] syllabi = {syllabus1, syllabus2, syllabus3, syllabus4};

        for (int i = 0;i<4 /* replace with list length*/;i++) {
            //add additional relevant labels when they arrive
            Label title = new Label(syllabi[i][1]);
            Label semester = new Label(syllabi[i][2] + " " + syllabi[i][3]);
            Label startEndDates = new Label(syllabi[i][4] + " - " + syllabi[i][5]);
            Button selectSyllabus = new Button("Select");

            GridPane grid = new GridPane();
            grid.getColumnConstraints().add(new ColumnConstraints(210));
            grid.add(title, 0, 0);
            grid.add(semester, 0, 1);
            grid.add(startEndDates, 0, 2);
            grid.add(selectSyllabus, 1, 0);

            if (i % 2 == 0) {
                syllabusPane.add(grid, 0, Math.floorDiv(i, 2));
            } else {
                syllabusPane.add(grid, 1, Math.floorDiv(i, 2));
            }

            int targetIndex = i;
            selectSyllabus.setOnAction(addEvent -> {

                selectSyllabus(targetIndex);
            });
        }
    }

    public void selectSyllabus(int syllabusIndex) {

        iceBreakerFX iceBreaker = new iceBreakerFX();
        iceBreaker.changeSyllabus(syllabusIndex);
        try {
            iceBreaker.changeScene("EventPage.fxml", "Event Calendar");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
