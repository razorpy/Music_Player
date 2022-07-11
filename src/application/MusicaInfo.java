package application;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Contém todas as informações de metadados das músicas
 */
public class MusicaInfo {
	int id = -1;
	Controller c;
	String nome_musica;
	String nome_arquivo;
	String nome_album;
	String nome_artista;
	String path;
	Image capa;
	Button play;
	Button remove;
	
	public MusicaInfo(int id, String nome_musica, String nome_album, String nome_artista, Image capa) {
		setId(id);
		this.nome_musica = nome_musica;
		this.nome_album = nome_album;
		this.nome_artista = nome_artista;
		this.capa = capa;

	}

	public MusicaInfo(String nome_musica, String nome_album, String nome_artista, Image capa) {
		this.id = -1;
		this.nome_musica = nome_musica;
		this.nome_album = nome_album;
		this.nome_artista = nome_artista;
		this.capa = capa;
	}
	
	public MusicaInfo(Object array[]) {
		this.nome_musica = (String)array[0];
		this.nome_album = (String)array[1];
		this.nome_artista = (String)array[2];
		this.capa = (Image)array[3];
		this.nome_arquivo = (String)array[4];
	}
	
	public MusicaInfo() {
		this.id = -1;
		this.nome_musica = null;
		this.nome_album = null;
		this.nome_artista = null;
		this.nome_arquivo = null;
		this.capa  = null;
	}
	
	public MusicaInfo(String nomeMus, String nomeAlb, String nomeArt, String nomeArq) {
		this.id = -1;
		this.nome_musica = nomeMus;
		this.nome_album = nomeAlb;
		this.nome_artista = nomeArt;
		this.nome_arquivo = nomeArq;
		this.capa  = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0) return;
		this.id = id;
	}

	public String getNome_musica() {
		return nome_musica;
	}
	public void setNome_musica(String s) {
		this.nome_musica = s;
	}
	
	public String getNome_arquivo() {
		return nome_arquivo;
	}
	public void setNome_arquivo(String s) {
		this.nome_arquivo = s;
	}

	public String getNome_album() {
		return nome_album;
	}
	public void setNome_album(String s) {
		this.nome_album = s;
	}

	public String getNome_artista() {
		return nome_artista;
	}
	public void setNome_artista(String s) {
		this.nome_artista = s;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public Image getCapa() {
		return capa;
	}
	
	public void setPlay() {
		this.play = new Button();
		this.play.setText("▶");
		this.play.setOnMouseClicked(event ->{
			getController().playInd(id);
		});
	}
	
	public Button getPlay() {
		return this.play;
	}
	
	public void setRemove() {
		this.remove = new Button();
		this.remove.setText("🗑");
		this.remove.setOnMouseClicked(event ->{

		});
	}
	
	public Button getRemove() {
		return this.remove;
	}
	
	public void setController (Controller c) {
		this.c = c;
	}
	
	public Controller getController() {
		return this.c;
	}

	@Override
	public String toString() {
		return "MusicaInfo [id=" + id + ", nome_musica=" + nome_musica + ", nome_arquivo=" + nome_arquivo
				+ ", nome_album=" + nome_album + ", nome_artista=" + nome_artista + ", path=" + path + ", capa=" + capa
				+ ", play=" + play + ", remove=" + remove + "]";
	}
	
}