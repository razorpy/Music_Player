package application;

import java.io.File;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


/**
 * Responsável por fazer todas as atualizações na GUI.
 */
public class View {
	
	Controller controller;
	Image nao_mutado, mutado, padrao, miniPlay, miniRemove;
	
	/**
	 * Pega as imagens dos botões e seta o controller.
	 * @param c Controller
	 */
	public View(Controller c) {
		try{
			this.controller = c;
			this.padrao = null;
			this.nao_mutado = new Image(getClass().getResource("front-end/icons/sound.png").toURI().toString());
			this.mutado = new Image(getClass().getResource("front-end/icons/sound-off.png").toURI().toString());
			this.miniPlay = new Image(getClass().getResource("front-end/icons/delete.png").toURI().toString());
			this.miniRemove =  new Image(getClass().getResource("front-end/icons/miniplay-button.png").toURI().toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public Controller getController() {
		return this.controller;
	}
	
	public void setCapa() {
		Image img = this.getController().getPlayer().getCurrent().getCapa();
		if (img != null) {			
			this.getController().capa.setImage(img);
			return;
		}
		this.getController().capa.setImage(padrao);
	}
	
	public void setTitle(String nome_musica) {
		this.getController().titulo.setText(nome_musica);
	}
	
	public void setBand(String nome_banda) {
		this.getController().band.setText(nome_banda);
	}
	
	public void setProgresso(double t) {
		getController().progress_Bar.setProgress(t);
	}
	
	/**
	 * @param current Tempo atual na música
	 * @param end Tempo final da música
	 */
	public void setProgresso(double current, double end) {
		getController().progress_Bar.setProgress(current/end);
	}
	
	/**
	 * Muda o painel principal para a tela incicial
	 */
	public void change_to_Home() {
		getController().main_Pane.setCenter(FxmlLoader.getPage("home", controller));
	}
	
	/**
	 * Muda o painel principal para a tela de configurações
	 */
	public void change_to_Settings() {
		getController().main_Pane.setCenter(FxmlLoader.getPage("Settings_Pane", controller));
	}
	
	/**
	 * Muda o painel principal para a tela de lista de playlists
	 */
	public void change_to_Library() {
		getController().main_Pane.setCenter(FxmlLoader.getPage("Playlists_Pane", controller));
	}
	
	/**
	 * Muda o painel principal para a tela de biblioteca de músicas
	 */
	public void change_to_YourBeats() {
		getController().main_Pane.setCenter(FxmlLoader.getPage("Musics_Pane", controller));
	}
	
	/**
	 * Muda o painel principal para a tela de criar playlist
	 */
	public void change_to_CreatePlaylist() {
		getController().main_Pane.setCenter(FxmlLoader.getPage("Criar_Playlist_Pane", controller));
	}
	
	public void change_to_Playlist(Organizador p) {
		getController().main_Pane.setCenter(FxmlLoader.getPage("Musics_Playlist_Pane", controller));
		getController().playlist_Name_Top.setText(p.nome_playlist);
	}
	
	/**
	 * Muda a imagem do botão de som/mudo
	 * @param mute Booleano que indica se está mudo ou não.
	 */
	public void changeSomIMG(boolean mute) {
		if (mute)
			getController().sound_Img.setImage(mutado);
		else
			getController().sound_Img.setImage(nao_mutado);
		
	}
	
}
