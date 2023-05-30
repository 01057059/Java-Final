import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;

// 01057057
// 01057059
public class MusicPlayer extends Application {
    private static final double WINDOW_WIDTH = 750;
    private static final double WINDOW_HEIGHT = 450;
    private double x = 0;
    private double y = 0;
    private MediaPlayer mediaPlayer;
    private Label timeLabel = new Label("00:00");
    private Slider timeSlider = new Slider();
    private Slider volumeSlider = new Slider();
    private ToggleButton playButton = new ToggleButton();
    private ListView<String> songList = new ListView<>();
    private List<File> fileList = new ArrayList<>();
    private int currentSongIndex = 0;
    private Label currentSongLabel = new Label("");
    String imagePath = "image.png";
    String gifPath = "Piring.gif";
    String play = "play.png";
    String pause = "pause.png";

    @Override
    public void start(Stage primaryStage) throws Exception {
        // UI elements
        BorderPane root = new BorderPane();
        AnchorPane rightSide = new AnchorPane();
        VBox controlBox = new VBox();
        controlBox.getChildren().add(songList);
        controlBox.setLayoutY(34.0);
        controlBox.setPrefHeight(325.0);
        controlBox.setPrefWidth(230.0);
        songList.setStyle("-fx-control-inner-background: #FFF6BD;");

        // controlBox.setStyle("-fx-background-color: #9DB2BF; " + "-fx-border-color:
        // transparent;");

        Image playImage = new Image(new File("play.png").toURI().toString());
        Image pauseImage = new Image(new File("pause.png").toURI().toString());
        ImageView playImageView = new ImageView(playImage);
        ImageView pauseImageView = new ImageView(pauseImage);
        playImageView.setFitWidth(30);
        playImageView.setPreserveRatio(true);
        pauseImageView.setFitWidth(30);
        pauseImageView.setPreserveRatio(true);
        playButton.setGraphic(playImageView);
        playButton.setLayoutX(129.0);
        playButton.setLayoutY(334.0);
        playButton.setStyle(
                "-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        playButton.setOnMouseEntered(e -> {
            playButton.setStyle("-fx-background-color: #7081aa;");
        });
        playButton.setOnMouseExited(e -> {
            playButton.setStyle("-fx-background-color: transparent;");
        });
        playButton.setOnMousePressed(e -> {
            playButton.setStyle("-fx-background-color: #3e4fae;");
        });
        playButton.setOnMouseReleased(e -> {
            playButton.setStyle("-fx-background-color: transparent;");
        });

        Image openFileImage = new Image(new File("file.png").toURI().toString()); // 13
        ImageView openFileView = new ImageView(openFileImage); // 13
        openFileView.setFitWidth(30); // 13
        openFileView.setPreserveRatio(true); // 13
        Button openFileButton = new Button(); // 13
        openFileButton.setGraphic(openFileView); // 13
        openFileButton.setLayoutY(362.0); // 13
        openFileButton.setStyle(
                "-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        openFileButton.setOnMouseEntered(e -> {
            openFileButton.setStyle("-fx-background-color: #7081aa;");
        });
        openFileButton.setOnMouseExited(e -> {
            openFileButton.setStyle("-fx-background-color: transparent;");
        });
        openFileButton.setOnMousePressed(e -> {
            openFileButton.setStyle("-fx-background-color: #3e4fae;");
        });
        openFileButton.setOnMouseReleased(e -> {
            openFileButton.setStyle("-fx-background-color: transparent;");
        });

        Image openFolderImage = new Image(new File("folder.png").toURI().toString()); // 12
        ImageView openFolderView = new ImageView(openFolderImage); // 12
        openFolderView.setFitWidth(30); // 12
        openFolderView.setPreserveRatio(true); // 12
        Button openFolderButton = new Button(); // 12
        openFolderButton.setGraphic(openFolderView); // 12
        openFolderButton.setLayoutX(60.0); // 12
        openFolderButton.setLayoutY(364.0); // 12
        openFolderButton.setStyle(
                "-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        openFolderButton.setOnMouseEntered(e -> {
            openFolderButton.setStyle("-fx-background-color: #7081aa;");
        });
        openFolderButton.setOnMouseExited(e -> {
            openFolderButton.setStyle("-fx-background-color: transparent;");
        });
        openFolderButton.setOnMousePressed(e -> {
            openFolderButton.setStyle("-fx-background-color: #3e4fae;");
        });
        openFolderButton.setOnMouseReleased(e -> {
            openFolderButton.setStyle("-fx-background-color: transparent;");
        });

        Image deleteImage = new Image(new File("delete.png").toURI().toString()); // 11
        ImageView deleteView = new ImageView(deleteImage); // 11
        deleteView.setFitWidth(30); // 11
        deleteView.setPreserveRatio(true); // 11
        Button deleteButton = new Button(); // 11
        deleteButton.setGraphic(deleteView); // 11
        deleteButton.setLayoutX(120.0); // 11
        deleteButton.setLayoutY(362.0); // 11
        deleteButton.setStyle(
                "-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        deleteButton.setOnMouseEntered(e -> {
            deleteButton.setStyle("-fx-background-color: #7081aa;");
        });
        deleteButton.setOnMouseExited(e -> {
            deleteButton.setStyle("-fx-background-color: transparent;");
        });
        deleteButton.setOnMousePressed(e -> {
            deleteButton.setStyle("-fx-background-color: #3e4fae;");
        });
        deleteButton.setOnMouseReleased(e -> {
            deleteButton.setStyle("-fx-background-color: transparent;");
        });

        Image clearAllImage = new Image(new File("clear.png").toURI().toString()); // 10
        ImageView clearAllView = new ImageView(clearAllImage); // 10
        clearAllView.setFitWidth(30); // 10
        clearAllView.setPreserveRatio(true); // 10
        Button clearAllButton = new Button(); // 10
        clearAllButton.setGraphic(clearAllView); // 10
        clearAllButton.setLayoutX(180.0); // 10
        clearAllButton.setLayoutY(362.0); // 10
        clearAllButton.setStyle(
                "-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        clearAllButton.setOnMouseEntered(e -> {
            clearAllButton.setStyle("-fx-background-color: #7081aa;");
        });
        clearAllButton.setOnMouseExited(e -> {
            clearAllButton.setStyle("-fx-background-color: transparent;");
        });
        clearAllButton.setOnMousePressed(e -> {
            clearAllButton.setStyle("-fx-background-color: #3e4fae;");
        });
        clearAllButton.setOnMouseReleased(e -> {
            clearAllButton.setStyle("-fx-background-color: transparent;");
        });

        Text libraryText = new Text("Library :");
        libraryText.setLayoutY(27.0);
        libraryText.setWrappingWidth(244.693359375);
        libraryText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        rightSide.getChildren().addAll(controlBox, openFileButton, openFolderButton, deleteButton, clearAllButton,
                libraryText);
        root.setRight(rightSide);

        AnchorPane center = new AnchorPane();
        Image previousImage = new Image(new File("previous.png").toURI().toString()); // 9
        ImageView previousImageView = new ImageView(previousImage); // 9
        previousImageView.setFitWidth(30); // 9
        previousImageView.setPreserveRatio(true); // 9
        Button previousButton = new Button(); // 9
        previousButton.setGraphic(previousImageView); // 9
        previousButton.setLayoutX(136.0); // 9
        previousButton.setLayoutY(332.0); // 9
        previousButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;");
        previousButton.setOnMouseEntered(e -> {
            previousButton.setStyle("-fx-background-color: #7081aa;");
        });
        previousButton.setOnMouseExited(e -> {
            previousButton.setStyle("-fx-background-color: transparent;");
        });
        previousButton.setOnMousePressed(e -> {
            previousButton.setStyle("-fx-background-color: #3e4fae;");
        });
        previousButton.setOnMouseReleased(e -> {
            previousButton.setStyle("-fx-background-color: transparent;");
        });

        Image nextImage = new Image(new File("next.png").toURI().toString()); // 7
        ImageView nextImageView = new ImageView(nextImage); // 7
        nextImageView.setFitWidth(30); // 7
        nextImageView.setPreserveRatio(true); // 7
        Button nextButton = new Button(); // 7
        nextButton.setGraphic(nextImageView); // 7
        nextButton.setLayoutX(260.0); // 7
        nextButton.setLayoutY(332.0); // 7
        nextButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;");
        nextButton.setOnMouseEntered(e -> {
            nextButton.setStyle("-fx-background-color: #7081aa;");
        });
        nextButton.setOnMouseExited(e -> {
            nextButton.setStyle("-fx-background-color: transparent;");
        });
        nextButton.setOnMousePressed(e -> {
            nextButton.setStyle("-fx-background-color: #3e4fae;");
        });
        nextButton.setOnMouseReleased(e -> {
            nextButton.setStyle("-fx-background-color: transparent;");
        });

        timeSlider.setLayoutX(33.0);
        timeSlider.setLayoutY(307.0);
        timeSlider.setPrefHeight(14.0);
        timeSlider.setPrefWidth(275.0);

        volumeSlider.setLayoutX(278.0);
        volumeSlider.setLayoutY(340.0);
        volumeSlider.setPrefHeight(14.0);
        volumeSlider.setPrefWidth(61.0);

        ImageView albumCover = new ImageView(); // 1
        albumCover.setFitHeight(250.0); // 1
        albumCover.setFitWidth(250.0); // 1
        albumCover.setLayoutX(100.0); // 1
        albumCover.setLayoutY(22.0); // 1
        albumCover.setPreserveRatio(true); // 1

        Image image = new Image(new File(imagePath).toURI().toString());
        albumCover.setImage(image);

        Image volumeImage = new Image(new File("volume.png").toURI().toString()); // 6
        ImageView volumeView = new ImageView(volumeImage); // 6
        volumeView.setFitWidth(13); // 6
        volumeView.setPreserveRatio(true); // 6
        Button volumeLabel = new Button(); // 6
        volumeLabel.setGraphic(volumeView); // 6
        volumeLabel.setLayoutX(346.0); // 6
        volumeLabel.setLayoutY(338.0); // 6
        volumeLabel.setStyle("-fx-background-color: transparent; " + "-fx-border-color: transparent;"); // 6

        currentSongLabel.setLayoutX(32.0); // 4
        currentSongLabel.setLayoutY(290.0); // 4

        timeLabel.setLayoutX(430.0); // 2
        timeLabel.setLayoutY(308.0); // 2

        HBox volumeBox = new HBox(volumeSlider); // 5
        volumeSlider.setLayoutX(370.0); // 5
        volumeSlider.setLayoutY(337.0); // 5
        volumeSlider.setPrefHeight(30.0); // 5
        volumeSlider.setPrefWidth(90.0); // 5

        HBox timeBox = new HBox(timeSlider); // 3
        timeSlider.setLayoutX(28.0); // 3
        timeSlider.setLayoutY(297.0); // 3
        timeSlider.setPrefHeight(40.0); // 3
        timeSlider.setPrefWidth(400.0); // 3

        playButton.setLayoutX(200.0); // 8
        playButton.setLayoutY(332.0); // 8

        center.getChildren().addAll(previousButton, nextButton, timeSlider, volumeSlider, albumCover, volumeLabel,
                currentSongLabel, timeLabel, volumeBox, timeBox, playButton);
        root.setCenter(center);
        playButton.setDisable(true);
        timeSlider.setDisable(true);
        volumeSlider.setValue(50);
        previousButton.setDisable(true);
        nextButton.setDisable(true);

        // primaryStage.setTitle("Music App - 057,059");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        Rectangle rect = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT);
        rect.setArcHeight(30.0);
        rect.setArcWidth(30.0);
        root.setClip(rect);

        HBox header = new HBox();
        header.setStyle("-fx-background-color: #86C8BC;"); // HEADER
        header.setPrefHeight(40);
        header.setAlignment(Pos.CENTER_RIGHT);

        Image closeImage = new Image(new File("close.png").toURI().toString());
        ImageView closeView = new ImageView(closeImage);
        closeView.setFitWidth(30);
        closeView.setPreserveRatio(true);
        Button closeButton = new Button();
        closeButton.setGraphic(closeView);
        closeButton.setStyle("-fx-background-color: transparent; " + "-fx-border-color: transparent;");
        closeButton.setOnAction(event -> primaryStage.close());
        header.getChildren().add(closeButton);
        root.setTop(header);
        Label titleLabel = new Label("Music Player ");
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        HBox headerContent = new HBox(titleLabel, closeButton);
        headerContent.setAlignment(Pos.CENTER_LEFT);
        headerContent.setSpacing(590);

        header.getChildren().add(headerContent);
        root.setTop(header);

        header.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        header.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        root.setStyle("-fx-background-color: #CEEDC7;"); // BACKGROUND
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // function for button
        openFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
            if (selectedFiles != null) {
                fileList.addAll(selectedFiles);
                updateSongList();
                playButton.setDisable(false);
                nextButton.setDisable(false);
                previousButton.setDisable(false);
                currentSongLabel.setText("Now Playing: None");
            }
        });

        openFolderButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File folder = directoryChooser.showDialog(primaryStage);
            if (folder != null) {
                List<File> folderFiles = Arrays.asList(folder.listFiles((dir, name) -> name.endsWith(".mp3")));
                fileList.addAll(folderFiles);
                updateSongList();
                playButton.setDisable(false);
                nextButton.setDisable(false);
                previousButton.setDisable(false);
                currentSongLabel.setText("Now Playing: None");
            }
        });

        songList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int index = songList.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < fileList.size()) {
                    currentSongIndex = index;
                    playSong(fileList.get(currentSongIndex));
                    Image gifImage = new Image(new File(gifPath).toURI().toString());
                    albumCover.setImage(gifImage);
                    playButton.setGraphic(pauseImageView);
                }
            }
        });

        playButton.setOnAction(event -> {
            if (mediaPlayer == null && !fileList.isEmpty()) {
                currentSongIndex = 0;
                playSong(fileList.get(currentSongIndex));
                Image gifImage = new Image(new File(gifPath).toURI().toString());
                albumCover.setImage(gifImage);
                playButton.setGraphic(pauseImageView);
            } else if (mediaPlayer != null) {
                if (mediaPlayer.getStatus() == Status.PLAYING) {
                    mediaPlayer.pause();
                    albumCover.setImage(image);
                    playButton.setGraphic(playImageView);
                } else {
                    mediaPlayer.play();
                    Image gifImage = new Image(new File(gifPath).toURI().toString());
                    albumCover.setImage(gifImage);
                    playButton.setGraphic(pauseImageView);
                }
            }
        });

        previousButton.setOnAction(event -> {
            currentSongIndex = (currentSongIndex - 1 + fileList.size()) % fileList.size();
            playSong(fileList.get(currentSongIndex));
            Image gifImage = new Image(new File(gifPath).toURI().toString());
            albumCover.setImage(gifImage);
            playButton.setGraphic(pauseImageView);
        });

        nextButton.setOnAction(event -> {
            currentSongIndex = (currentSongIndex + 1) % fileList.size();
            Image gifImage = new Image(new File(gifPath).toURI().toString());
            playSong(fileList.get(currentSongIndex));
            albumCover.setImage(gifImage);
            playButton.setGraphic(pauseImageView);
        });

        deleteButton.setOnAction(event -> {
            int selectedIndex = songList.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < fileList.size()) {
                fileList.remove(selectedIndex);
                updateSongList();
                if (selectedIndex < currentSongIndex) {
                    currentSongIndex--;
                } else if (selectedIndex == currentSongIndex) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    timeSlider.setDisable(true);
                    playButton.setGraphic(playImageView);
                }
            }

            if (mediaPlayer == null) {
                currentSongLabel.setText("Now Playing: None");
                timeLabel.setText("00:00");
                volumeSlider.setValue(50);
                albumCover.setImage(image);
            }
        });

        clearAllButton.setOnAction(event -> {
            fileList.clear();
            currentSongLabel.setText("Playlist Cleared");
            timeLabel.setText("00:00");
            updateSongList();
            mediaPlayer.stop();
            mediaPlayer = null;
            playButton.setDisable(true);
            timeSlider.setDisable(true);
            nextButton.setDisable(true);
            previousButton.setDisable(true);
            volumeSlider.setValue(50);
            albumCover.setImage(image);
            playButton.setGraphic(playImageView);
        });

        timeSlider.setOnMousePressed(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                updateElapsedTime(mediaPlayer.getCurrentTime());
            }
        });

        timeSlider.setOnMouseDragged(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                updateElapsedTime(mediaPlayer.getCurrentTime());
            }
        });

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            }
        });
    }

    private void updateSongList() {
        songList.getItems().clear();
        for (File file : fileList) {
            songList.getItems().add(file.getName());
        }
    }

    private String getFormattedSongName(File file) {
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf(".mp3"));
        return fileName;
    }

    private void playSong(File file) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        playButton.setSelected(true);
        currentSongLabel.setText("Now Playing: " + getFormattedSongName(file));
        currentSongLabel.setStyle("-fx-font-weight: bold;");
        mediaPlayer.setOnReady(() -> {
            timeSlider.setDisable(false);
            timeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            updateElapsedTime(Duration.ZERO);
            updateRemainingTime(Duration.ZERO, mediaPlayer.getTotalDuration());
        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Error occurred while playing the media.");
            mediaPlayer.stop();
            mediaPlayer = null;
            playButton.setSelected(false);
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateElapsedTime(mediaPlayer.getCurrentTime());
                updateRemainingTime(mediaPlayer.getCurrentTime(), mediaPlayer.getTotalDuration());
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            // to play the next song
            currentSongIndex = (currentSongIndex + 1) % fileList.size();
            playSong(fileList.get(currentSongIndex));
        });
        if (fileList.isEmpty()) {
            timeLabel.setText("00:00");
        }
    }

    private void updateElapsedTime(Duration elapsed) {
        timeSlider.setValue(elapsed.toSeconds());
        timeLabel.setText(formatTime(elapsed));
    }

    private void updateRemainingTime(Duration currentTime, Duration totalDuration) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING) {
            Duration remainingTime = totalDuration.subtract(currentTime);
            timeLabel.setText(" " + formatTime(remainingTime));
        } else {
            timeLabel.setText("00:00");
        }
    }

    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////