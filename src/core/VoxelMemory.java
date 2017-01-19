package core;

import javafx.application.Application;
import javafx.stage.Stage;
import stages.*;

/**
 * Created by Mateusz on 19.01.2017.
 * Project InferenceEngine
 */
public class VoxelMemory extends Application {

    public void start(Stage primaryStage) throws Exception {
        mainStage = new MainStage();
        mainStage.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
    public static MainStage mainStage;
}
