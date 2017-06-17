package maisPopularidade.sistema.usuario.post.midia;


public class Audio extends Midia{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8872550569879535011L;

	public Audio(String caminho) {
		super(caminho);
	}
	
	public String toString(){
		return "$arquivo_audio:"+super.toString();
	}

}
