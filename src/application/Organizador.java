package application;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Organizador {
	ArrayList<Musica> fila;
	ArrayList<Musica> historico;
	ArrayList<Musica> listaInicial;
	File pasta;
	// File arquivo;
	final int ATUAL = 0;
	final int TAMANHO = 0;
	
	public Organizador(String path) {
		this.fila = new ArrayList<Musica>();
		this.listaInicial = new ArrayList<Musica>();
		this.historico = new ArrayList<Musica>(TAMANHO);
		this.setPasta(path);
		// this.arquivo = new File(path);
		this.encontrarMusica();
	}
		
	public Organizador(File pasta) {
		this.fila = new ArrayList<Musica>();
		this.listaInicial = new ArrayList<Musica>();
		this.historico = new ArrayList<Musica>(TAMANHO);
		this.setPasta(pasta);
		// this.arquivo = new File(path);
		this.encontrarMusica();
	}
	
	/* TODO:
	public Organizador(File playlist){
		
	}
	*/
	
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
	
	public Musica getMusica(int index) {
		if(index >= 0 && index < fila.size()) {
			return this.fila.get(index);
		}
		else
			return null;
	}
	
	public String getMusica_path(int id) {
		for (Musica m : listaInicial) {
			if (m.getId() == id) return m.getPath();
		}
		return "";
	}
		
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
	
	public void adicionarMusica(Musica m) {
		fila.add(m);
	}
	
	public void filaNormal() {
		Collections.sort(fila, new Comparator<Musica>() {
			@Override
			public int compare(Musica a, Musica b) {
				return a.getNome_musica().compareTo(b.getNome_musica());
			}
		});
	}
	
	public void next(boolean repeat) {
		if(size() >= 1) {
			ArrayList<Musica> aux = new ArrayList<Musica>(TAMANHO);
			aux.add(fila.get(ATUAL));
			aux.addAll(historico);
			fila.remove(ATUAL);
			historico = aux;
			historico.trimToSize();
		}
		else if (size() == 0 && repeat) {
			fila.clear();
			fila.addAll(listaInicial);
		}		
	}
	
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
	
	public void filaRandom() {
		if(!fila.isEmpty()) {
			ArrayList<Musica> aux = new ArrayList<Musica>();
			aux.addAll(fila);
			aux.remove(ATUAL);
			fila.removeAll(aux);
			Collections.shuffle(aux);
			fila.addAll(aux);
		}
	}
	
	public void limparFila() {
		fila.clear();
	}
	
	public void organizaDados() {
		for (Musica m : listaInicial) {
			m.organizaDados();
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
}