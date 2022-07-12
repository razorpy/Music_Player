package application;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Responsável por tocar a música e todas as operações relacionadas à reprodução de uma fila de reprodução.
 */
public class Player {

	View view;
	Inicializador init;
	Organizador org;
	Musica atual;
	MediaPlayer mediaPlayer;
	Timer timer;
	TimerTask task;
	private double vol;
	private boolean rand;
	private boolean repeat;
	private boolean repeatSingle;
	private boolean dadosOrganizados;
	private boolean running = false;
	
	/**
	 * Seta todos os valores booleanos usados na reprodução e o View. Inicializador precisa ser setado.
	 * 
	 * @param v Classe View
	 */
	public Player(View v){
		this.view = v;
		vol = 0.5;
		rand = false;
		repeat = false;
		repeatSingle = false;
		dadosOrganizados = false;
	}
	
	public void setInicializador(Inicializador init) {
		this.init = init;
		org = this.init.getSuperOrg();
	}
	
	/**
	 * Chama a função play() do mediaPlayer
	 */
	public void playMedia() {
		beginTimer();
		mediaPlayer.play();
		running = true;
		view.changeplay_pause(running);
	}
	
	/**
	 * Chama a função pause() do mediaPlayer
	 */
	public void pauseMedia() {
		cancelTimer();
		mediaPlayer.pause();
		running = false;
		view.changeplay_pause(running);
	}
	
	/*
	 * Alterna a ordem da fila entre aleatória e não aleatória.
	 */
	public void random() {
		// ativar underline ou desativar underline
		// se underline tiver ativado, ao clicar, será desativado e não será random
		// vice e versa...
		if (this.rand) {
			System.out.println("ORDEM DE NOME");
			this.rand = false;
			org.filaNormal();
		}
		else {
			System.out.println("DESORDENADO");
			this.rand = true;
			org.filaRandom();
		}
		org.listaFila();
	}
	
	/*
	 * Toca a próxima música da fila.
	 */
	public void nextMusic() {
		if(!repeatSingle)
			org.next(repeat);
		setCurrentSong();
		playPause();
	}
	
	/**
	 * Toca a música mais recente do histórico.
	 */
	public void prevMusic() {
		org.prev();
		setCurrentSong();
		playPause();
	}
	
	/**
	 * Alterna entre os modos sem repeat, repeat e repeat single
	 */
	public void repeat() {
		if(repeat == false)
			repeat = true;
		else {
			if(repeatSingle == false)
				repeatSingle = true;
			else {
				repeat = false;
				repeatSingle = false;
			}
		}
		System.out.println("Repeat: " + repeat + "\nRepeat Single: " + repeatSingle);
	}
	
	/**
	 * Chama as funções playMedia ou pauseMedia dependendo do estado atual do mediaPlayer.
	 */
	public void playPause(){
		if(mediaPlayer == null) {
			setCurrentSong();
		}
		else if(mediaPlayer.getStatus().toString() == "PLAYING") {
			pauseMedia();
		}
		else
			playMedia();
	}
	
	/**
	 * Seta a música atual do player e pega os metadados
	 */
	public void setCurrentSong(){
		if(org.size() == 0){
			releasePlayer();
			return;
		}
		
		atual = org.getMusica(org.ATUAL);
		if (!dadosOrganizados) {			
			organiza();
		}
		
		// sets do view
		view.setProgresso(0);
		view.setCapa();
		if(atual.getNome_musica() != "infonull")
			view.setTitle(atual.getNome_musica());
		else
			view.setTitle(atual.getNome_arquivo());
		view.setBand(atual.getNome_artista());

		releasePlayer();
		System.out.println("TOCANDO: " + atual.getNome_musica());
		Media media = atual.getMedia();
		System.out.println(atual.getMedia().getMetadata());

		mediaPlayer = new MediaPlayer(media);
		this.setVolume(this.getVolume());
		mediaPlayer.setCycleCount(1);
		playMedia();
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			
			
			@Override
			public void run() {
				nextMusic();
			}
		});
		
		mediaPlayer.setOnReady(new Runnable() {
			
			@Override
			public void run() {
				organiza();
				view.setTitle(atual.getNome_musica());
				view.setCapa();
				view.setBand(atual.getNome_artista());
			}
		});
	}
	
	public void organiza() {
		org.organizaDados();
		dadosOrganizados = true;
		for (MusicaInfo mf : org.listaInfo) {
			System.out.println(mf);
		}
	}
	
	/**
	 * Para a reprodução atual e nulifica o mediaPlayer
	 */
	private void releasePlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}
	
	/**
	 * Inicia o timer de reprodução
	 */
	private void beginTimer() {
		timer = new Timer(); 
		task = new TimerTask() {
			public void run() {
				double current = -1;
				double end = -1;
				if (getMediaPlayer() != null) {
					current = getMediaPlayer().getCurrentTime().toSeconds();
					end = getMediaPlayer().getTotalDuration().toSeconds();
				}
				view.setProgresso(current, end);	
				if (current/end == 1) {
					cancelTimer();
				}
				if (current == -1 || end == -1) {
					view.setProgresso(0);
				}
			}
			
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	/**
	 * Cancela o timer de reprodução
	 */
	private void cancelTimer() {
		timer.cancel();
	}
		

	public void setVolume(double vol) {
		if (mediaPlayer != null)
			mediaPlayer.setVolume(vol);
		this.vol = vol;
		if (vol <= 0) {
			view.changeSomIMG(true);
		}
		else {
			view.changeSomIMG(false);
		}
	}

	public double getVolume() {
		return this.vol;
	}
	
	public Musica getCurrent(){

		if(org.size() == 0) {
			return null;
		}
		else
			return atual;
	}
	
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}
	
	public void setOrganizer(Organizador o) {
		this.org = o;
	}
	
	/**
	 * Toca uma música específica de uma playlist específica.
	 * 
	 * @param playlist Nome do arquivo .txt da playlist
	 * 
	 */
	public void playSelected(String playlist, int index) {
		releasePlayer();
		if (playlist.equals("Principal.txt")) {
			playSelected(index);
			return;
		}
		this.org = init.getPlaylistOrganizer(playlist);
		this.org.limparFila();
		this.org.setCurrent(index);
		//this.nextMusic();
		playPause();
	}
	
	/**
	 * Toca uma música específica da biblioteca de todas as músicas
	 * 
	 * 
	 */
	public void playSelected(int index) {
		this.org.limparFila();
		this.org.listaInicial.addAll(init.lib.get(0).listaInicial);
		this.org.setCurrent(index);
		//this.nextMusic();
		playPause();
	}
	
}
