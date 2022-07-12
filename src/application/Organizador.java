package application;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * Classe responsável pelas filas de reprodução usada pelo player e que representam as playlists.
 * 
 */
public class Organizador {
	ArrayList<Musica> fila;
	ArrayList<Musica> historico;
	ArrayList<Musica> listaInicial;
	ObservableList<MusicaInfo> listaInfo;
	Controller controller;
	File pasta;
	final int ATUAL = 0;
	final int TAMANHO = 100;
	
	String nome_playlist;
	
	Button playlist;
	Button play;
	Button remove;
	
	/**
	 * Cria as estruturas de dados e define a pasta de músicas.
	 * 
	 * @param path Caminho da pasta de músicas.
	 */
	public Organizador(String path, Controller c) {
		this.controller = c;
		this.fila = new ArrayList<Musica>();
		this.listaInicial = new ArrayList<Musica>();
		this.historico = new ArrayList<Musica>(TAMANHO);
		this.listaInfo = FXCollections.observableArrayList();
		this.setPasta(path);
		this.encontrarMusica();
	}
	
	/**
	 * Cria as estruturas de dados e define a pasta de músicas.
	 * 
	 * @param pasta File da pasta de músicas.
	 */
	public Organizador(File pasta, Controller c) {
		this.controller = c;
		this.fila = new ArrayList<Musica>();
		this.listaInicial = new ArrayList<Musica>();
		this.historico = new ArrayList<Musica>(TAMANHO);
		this.listaInfo = FXCollections.observableArrayList();
		this.setPasta(pasta);
		this.encontrarMusica();
	}

	/**
	 * Cria as estruturas de dados e preenche a fila com músicas.
	 * 
	 * @param musicas Arraylist de músicas.
	 */
	public Organizador(ArrayList<Musica> a, Controller c) {
		this.controller = c;
		this.fila = new ArrayList<Musica>();
		this.historico = new ArrayList<Musica>(TAMANHO);
		this.listaInicial = new ArrayList<Musica>(TAMANHO);
		this.listaInicial.addAll(a);
		this.listaInfo = FXCollections.observableArrayList();
		listaInicial.trimToSize();
		resetarFila();
	}
	
	public void setPasta(String path){
		this.pasta = new File(path);
		if (!this.pasta.isDirectory()) {
			this.pasta = null;
		}
	}
	
	public void setPasta(File pasta) {
		if (pasta.isDirectory()) {
			this.pasta = null;
			return;
		}
		this.pasta = pasta;
	}
		
	public ArrayList<Musica> getListaInicial(){
		return this.listaInicial;
	}
	
	public ArrayList<Musica> getFila(){
		return this.fila;
	}
	
	public ArrayList<Musica> getHistorico(){
		return this.historico;
	}

	/**
	 * .
	 * @return Um objeto Música quando o índice é válido. null caso contrário.
	 */
	public Musica getMusica(int index) {
		if(index >= 0 && index < fila.size()) {
			return this.fila.get(index);
		}
		else
			return null;
	}

	/**
	 * @param id O id de uma música.
	 * @return O path da Música, quando há uma Música com esse id na lista inicial. Uma string vazia caso contrário.
	 */
	public String getMusica_path(int id) {
		for (Musica m : listaInicial) {
			if (m.getId() == id) return m.getPath();
		}
		return "";
	}
	
	public String getNome_playlist() {
		return nome_playlist;
	}

	public void setNome_playlist(String nome_playlist) {
		if (nome_playlist.contains(".txt")) {
			nome_playlist = nome_playlist.replace(".txt", "");
		}
		this.nome_playlist = nome_playlist;
		
		setPlaylist();
		setPlay();
		setRemove();
	}

	public Button getPlaylist() {
		return playlist;
	}

	public void setPlaylist() {
		this.playlist = new Button();
		this.playlist.setText(nome_playlist);
		this.playlist.setOnMouseClicked(event ->{
			controller.change_to_Playlist(this);
		});
	}

	public Button getPlay() {
		return play;
	}

	// botao de play da playlist
	public void setPlay() {
		this.play = new Button();
		this.play.setText("▶");
		this.play.setOnMouseClicked(event ->{
			controller.janela_atual = this;
			controller.playInd(0);
		});
	}

	public Button getRemove() {
		return remove;
	}

	public void setRemove() {
		this.remove = new Button();
		this.remove.setText("🗑");
		this.remove.setOnMouseClicked(event ->{
			controller.apagarPlaylist(nome_playlist);
		});
	}

	/**
	 * Procura na pasta atribuída ao organizador por todas as músicas e as adiciona à fila.
	 */
	public void encontrarMusica() {
		int i = 0;
		for (File file : pasta.listFiles()) {
			if (!file.isDirectory()) {
				fila.add(new Musica(file.getAbsoluteFile()));
				i++;
			}
		}
		listaInicial.addAll(fila);
		
		
		System.out.println(i + " Musicas carregadas");
		
	}
	
	/**
	 * Adiciona uma música à fila.
	 * 
	 * @param musica Objeto Música a ser adicionado
	 */
	public void adicionarMusica(Musica m) {
		fila.add(m);
	}
	
	public void filaNormal() {
		Collections.sort(fila, new Comparator<Musica>() {
			@Override
			public int compare(Musica a, Musica b) {
				return a.getNome_arquivo().compareTo(b.getNome_arquivo());
			}
		});
	}
	
	/**
	 * Remove a música atual da fila e a adiciona no histórico.
	 * @param repeat true se deseja repetir fila.
	 */
	public void next(boolean repeat) {
		if(size() >= 1) {
			ArrayList<Musica> aux = new ArrayList<Musica>(TAMANHO);
			aux.add(fila.get(ATUAL));
			aux.addAll(historico);
			fila.remove(ATUAL);
			historico = aux;
			historico.trimToSize();
		}
		if(size() == 0 && repeat) {
			resetarFila();
		}
	}
	
	/**
	 * Adiciona a mais recente música do histórico e adiciona na fila como a atual.
	 */
	public Musica prev() {
		if(!historico.isEmpty()) {
			ArrayList<Musica> aux = new ArrayList<Musica>();
			aux.add(historico.get(ATUAL));
			aux.addAll(fila);
			historico.remove(ATUAL);
			fila = aux;
		}
		
		return fila.get(ATUAL);
	}
	
	/**
	 * Coloca a fila em ordem aleatória.
	 */
	public void filaRandom() {
		if(!fila.isEmpty()) {
			ArrayList<Musica> aux = new ArrayList<Musica>();
			aux.addAll(listaInicial);
			aux.remove(fila.get(ATUAL));
			fila.removeAll(aux);
			Collections.shuffle(aux);
			fila.addAll(aux);
		}
	}
	
	public void limparFila() {
		fila.clear();
	}
	public void apagarFila() {
		fila.clear();
		listaInicial.clear();
	}
	
	public void organizaDados() {
		int i = 0;
		listaInfo.clear();
		for (Musica m : listaInicial) {
			m.organizaDados();
			m.getMusica_info().setId(i++);
			m.getMusica_info().setController(controller);
			m.getMusica_info().setPlay();
			m.getMusica_info().setRemove();
			listaInfo.add(m.getMusica_info());
		}
	}
	
	public void listaFila() {
		for (Musica m : fila) {
			System.out.println(m.getNome_musica());
		}
	}
	
	public void listaMetaDados() {
		System.out.println("METADADOS");
		for (Musica m : listaInicial) {
			System.out.println(m.getMedia().getMetadata());
		}
	}
	
	public int size() {
		return this.fila.size();
	}
	
	/**
	 * Reseta a fila para o seu estado original.
	 */
	void resetarFila() {
		fila.clear();
		fila.addAll(listaInicial);
	}
	
	/**
	 * Reseta a fila e remove dela todas as músicas de índices anteriores ao índice passado como parâmetro.
	 * 
	 */
	public void setCurrent(int index) {
		resetarFila();
		for(int i = 0; i < index; i++) {
			fila.remove(0);
		}
	}
	
	public boolean equals(Organizador b) {
		return nome_playlist.equals(b.nome_playlist);
	}
	
	@Override
	public String toString() {
		return "Organizador [nome_playlist=" + nome_playlist + ", playlist=" + playlist + ", play=" + play + ", remove="
				+ remove + "]";
	}
}