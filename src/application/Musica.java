package application;

import java.io.File;

import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * Classe responsável pela mídia e pelas informações de cada música.
 */
public class Musica {
	String path;
	Media media;
	MusicaInfo musica_info;
	int id;
	
	public Musica(Media media) {
		this.media = media;
	}
	
	/**
	 * @param path Path da mídia
	 */
	public Musica(String path) {
		this.media = new Media(new File(path).toURI().toString());
		this.path = path;
	}
	
	/**
	 * @param file_media Objeto File da mídia
	 */
	public Musica(File file_media) {
		this.media = new Media(file_media.toURI().toString());
		this.path = file_media.getAbsolutePath();
	}
	
	/**
	 * Pega os metadados da mídia e coloca em musica_info.
	 */
	public void organizaDados() { 
		
		Object array[] = new Object[5];
		ObservableMap<String, Object> metadados = this.media.getMetadata();
		
		for (String key : metadados.keySet()) {
			key = key.toLowerCase();
			if (key.contains("title") && !key.contains("album") && !key.contains("ep")) {
				array[0] = (String)metadados.get(key);
			}
			else if (key.contains("album") || key.contains("ep")) {
				array[1] = (String)metadados.get(key);
			}
			else if (key.contains("artist") || key.contains("band")) {
				array[2] = (String)metadados.get(key);
			}
			else if (key.contains("image")) {
				array[3] = (Image)metadados.get(key);
			}
			array[4] = (new File(path)).getName();
		}
		
		this.musica_info = new MusicaInfo(array);
		musica_info.setPath(path);
	}

	public Media getMedia() {
		return media;
	}

	public MusicaInfo getMusica_info() {
		return musica_info;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
		if (this.getMusica_info() != null)
			getMusica_info().id = id;
	}

	public String getNome_musica() {
		if (this.getMusica_info() != null)
			return getMusica_info().getNome_musica();
		return "infonull";
	}

	public String getNome_album() {
		if (this.getMusica_info() != null)
			return getMusica_info().getNome_album();
		return "infonull";
	}

	public String getNome_artista() {
		if (this.getMusica_info() != null)
			return getMusica_info().getNome_artista();
		return "infonull";
	}
	
	public String getPath() {
		if (this.getMusica_info() != null)
			return getMusica_info().getPath();
		return "infonull";
	}
	
	public String getNome_arquivo() {
		if (this.getMusica_info() != null)
			return getMusica_info().getNome_arquivo();
		return "infonull";
	}

	public Image getCapa() {
		if (this.getMusica_info() != null)
			return getMusica_info().getCapa();
		System.out.println("Erro ao carregar a capa");
		return null;
	}

	@Override
	public String toString() {
		return "Musica [musica_info=" + musica_info + "; MEDIA=" + media + "]";
	}
}