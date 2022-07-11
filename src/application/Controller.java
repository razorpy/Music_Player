package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller{

	Organizador janela_atual = null;
	Player player;
	View view;
	boolean meta_adq = false;
	
	// fx:controller="application.Controller"
	
	// HomeScene.fxml
	@FXML
	ImageView capa;
	@FXML
	Label band;
	@FXML
	Label titulo;
	@FXML
	JFXSlider sound_Slider;
	@FXML
	BorderPane main_Pane;
	@FXML
	ProgressBar progress_Bar;
	@FXML
	ImageView sound_Img;
	
	// Musics_Pane.fxml
	@FXML
	TableView<MusicaInfo> table;
	@FXML
	TableColumn<MusicaInfo, String> nome;
	@FXML
	TableColumn<MusicaInfo, String> album;
	@FXML
	TableColumn<MusicaInfo, String> artista;
	@FXML
	TableColumn<MusicaInfo, Button> play;
	
	// Criar_Playlist_Pane.fxml
	@FXML
	TextField nome_playlist;
	@FXML
	JFXButton criar_playlist_Button;
	
	// Playlist_Pane.fxml
	@FXML
	TableView<Organizador> tabela;
	@FXML
    TableColumn<Organizador, Button> lista_de_playlists;
    @FXML
    TableColumn<Organizador, Button> play_playlist;
    @FXML
    TableColumn<Organizador, Button> remove_playlist;
    
    // Musics_Playlist_Pane.fxml
    @FXML
    Button add_Playlist_Button;
    @FXML
    TextField buscar_musicPlaylist;
    @FXML
    Button buscar_musicPlaylist_Button;
    @FXML
    Button delete_Playlist_Button;
    @FXML
    Button play_Playlist_Button;
    @FXML
    Label playlist_Name_Top;
    @FXML
    TableView<MusicaInfo> table2;
    @FXML
    TableColumn<MusicaInfo, String> album_playlist;
    @FXML
    TableColumn<MusicaInfo, String> band_playlist;
    @FXML
    TableColumn<MusicaInfo, String> music_playlist;
    @FXML
    private TableColumn<MusicaInfo, Button> play_playlist2;
    @FXML
    private TableColumn<MusicaInfo, Button> remove;
	
	@FXML
	void pegar_nome(ActionEvent e) {
		String nome_arq = nome_playlist.getText();
		if (nome_arq.equals("")) return;
		nome_playlist.setText("");
		getPlayer().init.criarPlaylist(nome_arq);
		
		/*
		TESTE, APAGUE
		getPlayer().init.addMusica(nome_arq, 10);
		getPlayer().init.addMusica(nome_arq, 15);
		getPlayer().init.addMusica(nome_arq, 20);
		
		getPlayer().init.removeMusica(nome_arq, 15);
		getPlayer().init.addMusica(nome_arq, 30);
		*/
	}
	
	@FXML
	void change_to_Home(ActionEvent e) {
		getView().change_to_Home();
	}
	
	@FXML
	void change_to_Settings(ActionEvent e) {
		getView().change_to_Settings();
	}
	
	@FXML
	void change_to_Library(ActionEvent e) {
		attPlaylists();
		getView().change_to_Library();
		atualizaPlaylistsPane();
	}
	
	public void change_to_Playlist(Organizador p) {
		janela_atual = p;
		attDados();
		getView().change_to_Playlist(p);
		atualizaPlaylistMusicasPane();
		System.out.println(p.nome_playlist + " carregada");
	}
	
	@FXML
	void change_to_YourBeats(ActionEvent e) {
		janela_atual = getPlayer().init.lib.get(0);
		attDados();
		getView().change_to_YourBeats();
		atualizaMusicasPane();
	}
	
	@FXML
	void change_to_CreatePlaylist(ActionEvent e) {
		getView().change_to_CreatePlaylist();
	}
	
	@FXML
	void changeImg(ActionEvent e) {
		if (getPlayer().getVolume() > 0) {
			getPlayer().setVolume(0);
			getView().changeSomIMG(true);
		}else {
			// desmuta
			newVol();
			if (getPlayer().getVolume() != 0) {
				getView().changeSomIMG(false);
			}
		}
	}
	
	@FXML
	void newVol() {
		getPlayer().setVolume(sound_Slider.getValue()*0.01);
	}
	
	@FXML
	public void random(ActionEvent e) {
		getPlayer().random();
	}

	@FXML
	public void nextMusic(ActionEvent e) {
		attDados();
		getPlayer().nextMusic();
	}

	@FXML
	public void prevMusic(ActionEvent e) {
		attDados();
		getPlayer().prevMusic();
	}

	@FXML
	public void repeat(ActionEvent e) {
		getPlayer().repeat();
	}
	
	@FXML
	public void playPause(ActionEvent e){
		attDados();
		getPlayer().playPause();
	}
		
	public void apagarPlaylist(String nome_playlist) {
		getPlayer().init.apagarPlaylist(nome_playlist);
		attPlaylists();
		atualizaPlaylistsPane();
	}
	
	public void playInd(int id) {
		System.out.println(id);
		if (!janela_atual.equals(getPlayer().org)) {
			getPlayer().setOrganizer(janela_atual);
		}
		getPlayer().playSelected(id);
	}
		
	public Player getPlayer() {
		return this.player;
	}
	
	public View getView() {
		return this.view;
	}
	
	public ObservableList<MusicaInfo> getLista() {
		janela_atual.organizaDados();
		return janela_atual.listaInfo;
	}
	
	public ObservableList<Organizador> getLista2(){
		return getPlayer().init.libshow;
	}
		
	public void setPlayer(Player p) {
		this.player = p;
	}

	public void setView(View view) {
		this.view = view;
	}
	
	void attDados() {
        if (!meta_adq) {
            getPlayer().setOrganizer(player.init.lib.get(0));
            getPlayer().playPause();
            getPlayer().setVolume(0);
            getPlayer().pauseMedia();
            getPlayer().setVolume(0.5);
            meta_adq = true;
        }
    }
	
	void attPlaylists() {
		getPlayer().init.readPlaylists();
		System.out.println(getPlayer().init.libshow.size());
		for (Organizador o : getPlayer().init.libshow) {
			System.out.println(o);
		}
	}
	
	public void atualizaMusicasPane () {
		 
		ObservableList<MusicaInfo> list = getLista();
		
		nome.setCellValueFactory(new PropertyValueFactory<>("nome_musica"));
		album.setCellValueFactory(new PropertyValueFactory<>("nome_album"));
		artista.setCellValueFactory(new PropertyValueFactory<>("nome_artista"));
		play.setCellValueFactory(new PropertyValueFactory<>("play"));
		
		table.setItems(list);
	}
	
	public void atualizaPlaylistsPane() {
		
		ObservableList<Organizador> list = getLista2();
		
		lista_de_playlists.setCellValueFactory(new PropertyValueFactory<>("playlist"));
		play_playlist.setCellValueFactory(new PropertyValueFactory<>("play"));
		remove_playlist.setCellValueFactory(new PropertyValueFactory<>("remove"));
		
		tabela.setItems(list);
	}
	
	public void atualizaPlaylistMusicasPane() {
		
		ObservableList<MusicaInfo> list = getLista();
		System.out.println(list.size());
		
		play_playlist2.setCellValueFactory(new PropertyValueFactory<>("play"));
		music_playlist.setCellValueFactory(new PropertyValueFactory<>("nome_musica"));
		album_playlist.setCellValueFactory(new PropertyValueFactory<>("nome_album"));
		band_playlist.setCellValueFactory(new PropertyValueFactory<>("nome_artista"));
		remove.setCellValueFactory(new PropertyValueFactory<>("remove"));
		
		table2.setItems(list);
	}
		
}