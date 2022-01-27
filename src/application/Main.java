package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml")); // manipular a tela antes de carregar.
			ScrollPane scrollPane = loader.load();
			
			scrollPane.setFitToHeight(true);// macete pra deixar o scrollpane ajustado na janela
			scrollPane.setFitToWidth(true);// macete pra deixar o scrollpane ajustado na janela
			
			Scene mainScene = new Scene(scrollPane); // cena principal
			primaryStage.setScene(mainScene); // palco principal da cena 
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
