package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.util.Collections;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public class NormalPop implements TipoPopularidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8891643587640850294L;
	private static final int PONTOS_POP = 10;
	
	public NormalPop(){
		
	}
	
	@Override
	public void curtirPost(Usuario amigo, int post) {
		amigo.adicionaPops(PONTOS_POP);
		amigo.adicionaPopsNoPost(PONTOS_POP, post);
	}

	@Override
	public void rejeitarPost(Usuario amigo, int post) {
		amigo.removePops(PONTOS_POP);
		amigo.removePopsNoPost(PONTOS_POP, post);
	}
	
	@Override
	public List<Post> getPostsRecentes(List<Post> posts) {
		 Collections.sort(posts);
		 Collections.reverse(posts);
		 if (posts.size() >= 2) {
			 return posts.subList(0,2);
		 } else {
			 return posts.subList(0, posts.size());
		 }
	}
	
	public String toString(){
		return "Normal Pop";
	}
		
}