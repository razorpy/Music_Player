package application;


import javafx.scene.image.Image;


public class MusicaInfo {
	int id = -1;
	// Media media;
	String nome_musica;
	String nome_album;
	String nome_artista;
	String path;
	Image capa;
	
	public MusicaInfo(int id, String nome_musica, String nome_album, String nome_artista, Image capa) {
		setId(id);
		this.nome_musica = nome_musica;
		this.nome_album = nome_album;
		this.nome_artista = nome_artista;
		this.capa = capa;
		// BUG: NÃO CONSIGO CHAMAR A FUNÇÃO AQUI, PORTANTO CHAMO EM Controller.java
		// atualizaDados();
	}

	public MusicaInfo(String nome_musica, String nome_album, String nome_artista, Image capa) {
		this.id = -1;
		this.nome_musica = nome_musica;
		this.nome_album = nome_album;
		this.nome_artista = nome_artista;
		this.capa = capa;
		// BUG: NÃO CONSIGO CHAMAR A FUNÇÃO AQUI, PORTANTO CHAMO EM Controller.java
		// atualizaDados();
	}
	
	public MusicaInfo(Object array[]) {
		this.id = (int)array[4];
		this.nome_musica = (String)array[0];
		this.nome_album = (String)array[1];
		this.nome_artista = (String)array[2];
		this.capa = (Image)array[3];
		// BUG: NÃO CONSIGO CHAMAR A FUNÇÃO AQUI, PORTANTO CHAMO EM Controller.java
		// atualizaDados();
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

	public String getNome_album() {
		return nome_album;
	}

	public String getNome_artista() {
		return nome_artista;
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

	@Override
	public String toString() {
		return "MusicaInfo [id=" + id + ", nome_musica=" + nome_musica + ", nome_album=" + nome_album
				+ ", nome_artista=" + nome_artista + ", capa=" + capa + "]";
	}
	
}