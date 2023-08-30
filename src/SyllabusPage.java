import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;

import javax.imageio.ImageIO;

public class SyllabusPage implements Initializable {

    @FXML
    private Label greetingLabel;
    @FXML
    private ImageView profilePicture;
    @FXML
    private GridPane syllabusPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Main main = new Main();
        try {
            User user = main.getUserInfo();
            greetingLabel.setText("Hello, " + user.name + "!");
            Image profileImage = new Image("default_image.jpg");

            if (user.profile_photo_path != null) {
                profileImage = new Image(user.profile_photo_path);

            } else if (user.profile_photo_url != null) {
                URL photoURL = new URL(user.profile_photo_url);
                try {
                    BufferedImage image = ImageIO.read(photoURL);
                    if (image != null) {
                        profileImage = SwingFXUtils.toFXImage(image, null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            profilePicture.setImage(profileImage);
        } catch (Exception e) {
            greetingLabel.setText("Hello!");
            e.printStackTrace();
        }

        /* sample data
        String[] syllabus1 = {"1", "Projects in Computer Science", "Spring", "2023", "2023-01-17", "2023-05-09"};
        String[] syllabus2 = {"2", "Projects in Computer Science 002", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus3 = {"2", "Projects in Computer Science 003", "Spring", "2023", "2023-01-18", "2023-05-09"};
        String[] syllabus4 = {"2", "Projects in Computer Science 004", "Spring", "2023", "2023-01-18", "2023-05-09"};

        String[][] syllabi = {syllabus1, syllabus2, syllabus3, syllabus4}; */

        try {
            SyllabusResponse syllabusResponseObject = main.getSyllabi();
            int i = 0;
            for (Syllabus syllabus : syllabusResponseObject.syllabi) {
                //add additional relevant labels when they arrive
                Label title = new Label("Course: " + syllabus.course_name);
                Label semester = new Label("Semester: " + syllabus.course_semester + " " + syllabus.course_year);
                Label startEndDates = new Label("Duration: " + syllabus.start_date + " - " + syllabus.end_date);
                boolean isLink = true;
                Label discordLabel = new Label("Discord Channel: ");
                Label discord = new Label();
                Hyperlink discordLink = new Hyperlink();
                if (syllabus.discord_channel == null) {
                    isLink = false;
                    discord.setText("None");
                    discord.setWrapText(true);
                    discord.setMaxWidth(210);
                } else {
                    discordLink.setText("https://discord.com/channel/" + syllabus.discord_channel);
                    discordLink.setWrapText(true);
                    discordLink.setMaxWidth(210);
                }


                Button selectSyllabus = new Button("Select");

                title.setWrapText(true);
                title.setMaxWidth(200);
                semester.setWrapText(true);
                semester.setMaxWidth(210);
                startEndDates.setWrapText(true);
                startEndDates.setMaxWidth(210);


                GridPane grid = new GridPane();
                grid.getColumnConstraints().add(new ColumnConstraints(210));
                grid.setVgap(10);
                grid.add(title, 0, 0);
                grid.add(semester, 0, 1);
                grid.add(startEndDates, 0, 2);
                grid.add(discordLabel, 0, 3);
                if (isLink) {
                    grid.add(discordLink, 0, 4);
                } else {
                    grid.add(discord, 0, 4);
                }
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
