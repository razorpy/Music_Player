package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {

	// TODO: ver se um construtor cai bem aqui
	Controller c;
	Inicializador init;
	Organizador org;
	Musica atual;
	MediaPlayer mediaPlayer;
	private double vol;
	private boolean rand;
	private boolean repeat;
	private boolean repeatSingle;

	public Player(Controller c){
		this.c = c;
		init = new Inicializador();
		org = init.getSuperOrg();
		vol = 0.1;
		rand = false;
		repeat = false;
		repeatSingle = false;
		
	}
	
	public void volUp(){
		vol += 0.1;
		if(vol > 1.0)
			vol = 1.0;
		this.setVolume(vol);
	}

	public void volDown(){
		vol -= 0.1;
		if(vol < 0.0)
			vol = 0.0;
		this.setVolume(vol);
	}
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

	public void nextMusic() {

		if(!repeatSingle)
			org.next(repeat);
		setCurrentSong();
		playPause();
	}

	public void prevMusic() {

		org.prev();
		setCurrentSong();
		playPause();
	}

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

	public void playPause(){

		if(mediaPlayer == null) {
			setCurrentSong();
		}
		else if(mediaPlayer.getStatus().toString() == "PLAYING") {
			mediaPlayer.pause();
		}
		else
			mediaPlayer.play();
	}

	public void setCurrentSong(){

		if(org.size() == 0){
			releasePlayer();
			return;
		}

		atual = org.getMusica(org.ATUAL);
		atual.organizaDados();
		c.setImage();

		releasePlayer();
		System.out.println("TOCANDO: " + atual.getNome_musica());
		Media media = atual.getMedia();
		System.out.println(atual.getMedia().getMetadata());

		mediaPlayer = new MediaPlayer(media);
		this.setVolume(this.getVolume());
		mediaPlayer.setCycleCount(1);

		// Autoplay
		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				nextMusic();
			}
		});
	}

	private void releasePlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}
	}

	private void setVolume(double vol) {
		mediaPlayer.setVolume(vol);
		this.vol = vol;
	}

	private double getVolume() {
		return this.vol;
	}

	public Musica getCurrent(){

		if(org.size() == 0) {
			return null;
		}
		else
			return atual;
	}
}