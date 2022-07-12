package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {	
		String directoryName = System.getProperty("user.dir");
		System.out.println("Current Working Directory is = " + directoryName);
		launch(args);	
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			
			// Colocando icone e nome
			stage.setTitle("Piratify");
			stage.getIcons().add(new Image(getClass().getResource("front-end/icons/icone-spotify-violet.png").toURI().toString()));
			
			// Carregando a pagina principal
			FXMLLoader loader = new FXMLLoader(getClass().getResource("front-end/HomeScene.fxml"));
						
			// Instancia o controller e coloca no escopo do view
			// Instancia o view e coloca o controller no seu escopo
			// Instancia o player e coloca o view no seu escopo
			Controller controller = new Controller();
			View view = new View(controller);
			Player player = new Player(view);
			Inicializador init = new Inicializador(controller);
			
			player.setInicializador(init);
			// TODO: fazer sistema de receber a playlist do usuário. Esta linha é só para debug
			//player.setOrganizer(player.init.lib.get(player.init.map_playlist.get("c.txt")));
			
			// coloca o player e o view no escopo do controller
			controller.setPlayer(player);
			controller.setView(view);
						
			loader.setController(controller);
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setResizable(false);
			
			stage.show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}