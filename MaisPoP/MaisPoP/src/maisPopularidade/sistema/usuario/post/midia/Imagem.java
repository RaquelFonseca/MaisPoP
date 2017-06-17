package maisPopularidade.sistema.usuario.post.midia;


public class Imagem extends Midia{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8789844543107818799L;

	public Imagem(String caminho) {
		super(caminho);
	}

	public String toString(){
		return "$arquivo_imagem:"+super.toString();
	}
	
}
