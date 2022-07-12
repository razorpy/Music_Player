package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inicializador {
	
	File pasta_mem;
	File pasta_musica;
	File pasta_playlist;
	HashMap<String, Integer> playlistMap;
	ArrayList<Organizador> lib; // pos[0] = biblioteca
	ObservableList<Organizador> libshow;
	ArrayList<MusicaInfo> info_musica;
	HashMap<String, Integer> map_playlist;
	Controller controller;
	
	Inicializador(Controller c){
		this.controller = c;
		pasta_mem = new File(System.getProperty("user.dir"));
		playlistMap = new HashMap<String, Integer>();
		info_musica = new ArrayList<MusicaInfo>();
		
		for (File arq : pasta_mem.listFiles()) {
			if (arq.isDirectory() && arq.getName().equals("mem")) {
				pasta_mem = arq;
				for (File f : pasta_mem.listFiles()) {
					if (f.isDirectory() && f.getName().equals("playlists")) {
						pasta_playlist = f;
						break;
					}
				}
			}
			
			// PARA TESTE, APAGAR DEPOIS
			if (arq.isDirectory() && arq.getName().equals("Music")) {
				pasta_musica = arq;
			}
		}
		
		readPlaylists();
		
	}
	
	private void iniciaMusicas(){
		System.out.println(pasta_musica);
		lib.add(new Organizador(pasta_musica.getAbsolutePath(), controller));
		lib.get(0).setNome_playlist("Principal");
	}
	
	public Organizador getSuperOrg() {
		return this.lib.get(0);
	}
	
	public void readPlaylists() {
		lib = new ArrayList<Organizador>();
		libshow = FXCollections.observableArrayList();
		map_playlist = new HashMap<String, Integer>();
		iniciaMusicas();
		int i = 1, num;
		String s;
		ArrayList<Musica> lista;
		File pasta_playlists = this.pasta_playlist;
		try {
			for(File playlist : pasta_playlists.listFiles()) {
				FileReader _fr = new FileReader(playlist);
				BufferedReader fr = new BufferedReader(_fr);
				lista = new ArrayList<Musica>();
				num = 0;
				while((s = fr.readLine()) != null) {
					num = Integer.parseInt(s);
					System.out.println(lib.get(0).getMusica(num));
					lista.add(lib.get(0).getMusica(num));
				}
				fr.close();
				_fr.close();
				lib.add(new Organizador(lista, controller));
				lib.get(lib.size()-1).setNome_playlist(playlist.getName());
				libshow.add(lib.get(lib.size()-1));
				map_playlist.put(playlist.getName(), i);
				i++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Organizador getPlaylistOrganizer(String arquivo) {
		
		if (map_playlist.containsKey(arquivo))
			return lib.get(map_playlist.get(arquivo));
		return null;
	}

	public void criarPlaylist(String nome_arq) {
		// TODO Auto-generated method stub
		File pl = new File(pasta_playlist, nome_arq + ".txt");
		try {
			pl.createNewFile();
		}
		catch(IOException e) {
			System.out.println("Erro ao criar o arquivo");
		}
		this.readPlaylists();
	}
	
	public void apagarPlaylist(String nome_arq) {
		if (!nome_arq.contains(".txt")) {
			nome_arq = nome_arq.concat(".txt");
		}
		for (File f : pasta_playlist.listFiles()) {
			if (f.getName().equals(nome_arq)) {
				f.delete();
			}
		}
		this.readPlaylists();
	}
	
	public void addMusica(String playlist, int musica) {
		File pl = pegaPlaylist(playlist);
		System.out.println(pl);
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileWriter(pl, true)); // true indica escrita no final
			pw.println(Integer.toString(musica));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			System.out.println("erro ao abrir PrintWritter");
		}
		this.readPlaylists();
	}
	
	public void removeMusica(String playlist, int musica) {
		File pl = pegaPlaylist(playlist);
		if (pl == null) return;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pl));
		} catch(IOException e) {
			System.out.println("erro ao remover musica (nao achou file)");
		}
		if (br == null) return;
		ArrayList<String> vai_escrever = new ArrayList<String>();
		String linha = "";
		int i = -1;
		try {
			while ((linha = br.readLine())!= null) {
				++i;
				if (i == musica) {
					System.out.println(linha );
					continue;
				}
				if (linha.contains("\n")) {
					linha = linha.replace('\n', ' ');
					linha = linha.strip();
				}
				vai_escrever.add(linha);
			}
		br.close();
		} catch (IOException e) {
			System.out.println("erro ao ler linha da playlist");
		}
		
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileWriter(pl, false)); // false indica reescrita
			for (String ind : vai_escrever) {
				pw.println(ind);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			System.out.println("erro ao abrir PrintWritter");
		}
		this.readPlaylists();
	}
	
	private File pegaPlaylist(String playlist_nome) {
		if (!playlist_nome.contains(".txt")) {
			playlist_nome = playlist_nome.concat(".txt");
		}
		for (File f : pasta_playlist.listFiles()) {
			if (f.getName().equals(playlist_nome)) {
				return f;
			}
		}
		return null;
	}
}
