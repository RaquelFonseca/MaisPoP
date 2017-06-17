package maisPopularidade.sistema.usuario.post.midia;

import java.io.Serializable;

public abstract class Midia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2624627286361401797L;
	private String caminho;
	
	public Midia(String caminho){
		this.caminho = caminho;
	}
	
	public String toString(){
		return caminho;
	}
	
	public String getCaminho(){
		return this.caminho;
	}
}
