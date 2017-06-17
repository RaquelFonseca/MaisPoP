package maisPopularidade.sistema.usuario.post;

import java.io.Serializable;

public class Hashtag implements Comparable<Hashtag>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1482361373984919939L;
	private String nome;
	private int ocorrencias;
	
	public Hashtag(String nome, int ocorrencias) {
		this.nome = nome;
		this.ocorrencias = ocorrencias;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getOcorrencias() {
		return ocorrencias;
	}

	public void incrementaOcorrencias() {
		this.ocorrencias ++;
	}
	
	public String toString(){
		return this.nome+": "+this.ocorrencias;
	}

	@Override
	public int compareTo(Hashtag outraHashtag) {
		if(this.ocorrencias < outraHashtag.getOcorrencias()){
			return -1;
		}else if(this.ocorrencias > outraHashtag.getOcorrencias()){
			return 1;
		}	
		return this.nome.compareToIgnoreCase(outraHashtag.getNome());	
		
	}
}
