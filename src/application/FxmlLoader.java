package application;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class FxmlLoader {
	public static AnchorPane view;
	
	public static AnchorPane getPage(String fileName, Controller c) {
		try{
			URL fileUrl = Main.class.getResource("front-end/" + fileName + ".fxml");
			if(fileUrl == null) {
				throw new java.io.FileNotFoundException();
			}
			FXMLLoader loader = new FXMLLoader(fileUrl);
			loader.setController(c);
			view = loader.load();
		}
		catch(Exception e) {
			System.out.println("erro" + e.getMessage());
		}
		return view;
	}
}
