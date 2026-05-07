package es.universidad.juego;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // TODO: inicializar la interfaz principal
        primaryStage.setTitle("Juego de Exploración");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
