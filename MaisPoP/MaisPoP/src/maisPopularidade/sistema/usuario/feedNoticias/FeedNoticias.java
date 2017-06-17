package maisPopularidade.sistema.usuario.feedNoticias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;
import maisPopularidade.sistema.usuario.tipoPopularidade.Usuario;

public class FeedNoticias implements Serializable{
	

	private static final long serialVersionUID = 2774135479320953243L;
	private List<Post> posts;
	private OrdenadorFeed ordenador;
	
	public FeedNoticias(){
		
	}
	
	public void atualizaFeed(List<Usuario> amigos) {
		posts = new ArrayList<Post>();
		for (Usuario amigo : amigos) {
			posts.addAll(amigo.getPostsRecentes());	
		}
	}
	
	public void ordenaPorData() {
		this.ordenador = new OrdenaPorData();
	}
	
	public void ordenaPorPops() {
		this.ordenador = new OrdenaPorPops();
	}
	
	public List<Post> ordena() {
		this.ordenador.ordena(posts);
		return posts;
	}

}
