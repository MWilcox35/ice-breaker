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
        /* sample data
        String[] syllabus1 = {"1", "Projects in Computer Science", "Spring", "2023", "2023-01-17", "2023-05-09"};
        String[] syllabus2 = {"2", "Projects in Computer Science 002", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus3 = {"2", "Projects in Computer Science 003", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus4 = {"2", "Projects in Computer Science 004", "Spring", "2023", "2023-01-18", "2023-05-09"};

        String[][] syllabi = {syllabus1, syllabus2, syllabus3, syllabus4}; */
        Main main = new Main();
        try {
            SyllabusResponse syllabusResponseObject = main.getSyllabi();
            int i = 0;
            for (Syllabus syllabus : syllabusResponseObject.syllabi) {
                //add additional relevant labels when they arrive
                Label title = new Label("Course: " + syllabus.course_name);
                Label semester = new Label("Semester: " + syllabus.course_semester + " " + syllabus.course_year);
                Label startEndDates = new Label("Date: " + syllabus.start_date + " - " + syllabus.end_date);
                Label discord = new Label("Discord Channel: " + syllabus.discord_channel);
                Button selectSyllabus = new Button("Select");

                title.setWrapText(true);
                title.setMaxWidth(200);
                semester.setWrapText(true);
                semester.setMaxWidth(210);
                startEndDates.setWrapText(true);
                startEndDates.setMaxWidth(210);
                discord.setWrapText(true);
                discord.setMaxWidth(210);

                GridPane grid = new GridPane();
                grid.getColumnConstraints().add(new ColumnConstraints(210));
                grid.setVgap(10);
                grid.add(title, 0, 0);
                grid.add(semester, 0, 1);
                grid.add(startEndDates, 0, 2);
                grid.add(discord, 0, 3);
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
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
