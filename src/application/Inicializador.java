package application;


import java.io.File;
import java.util.ArrayList;


public class Inicializador {
	// TODO: estrutura para representar configurações salvas
	File pasta_mem;
	File pasta_musica;
	ArrayList<Organizador> lib; // pos[0] = biblioteca
	ArrayList<MusicaInfo> map_musica;
	
	
	Inicializador(){
		
		pasta_mem = new File(System.getProperty("user.dir"));
		lib = new ArrayList<Organizador>();
		map_musica = new ArrayList<MusicaInfo>();
		
		for (File arq : pasta_mem.listFiles()) {
			
			if (arq.isDirectory() && arq.getName().equals("mem")) {
				pasta_mem = arq;
				// TODO: readConfig();
			}
			
			// PARA TESTE, APAGAR DEPOIS
			if (arq.isDirectory() && arq.getName().equals("Music")) {
				pasta_musica = arq;
			}
		}
		
		iniciaMusicas();
		
		
		
		
		// COLOCAR FUNÇÂO DE SELECIONAR DIRETORIO DE MUSICA
		
	}
	
	private void iniciaMusicas(){	
		lib.add(new Organizador(this.pasta_musica.getName()));
		Organizador superOrg = lib.get(0);
		int i = 0;
		for (Musica m : superOrg.getListaInicial()) {
			m.setId(i);
			i++;
		}
		/*
		 */
	}
	
	public Organizador getSuperOrg() {
		return this.lib.get(0);
	}
	
	/* TODO:
	public readConfig() {
		
	}
	
	public iniciaPlaylists() {
		
	}
	
	public atualizaBanco() { // vai ser chamada no controller
		lib.get(0).get
	}
	*/
	
	
	
}