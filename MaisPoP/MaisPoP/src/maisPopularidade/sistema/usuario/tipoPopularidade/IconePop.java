package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.util.Collections;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public class IconePop implements TipoPopularidade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5733247326228277747L;
	private static final int PONTOS_POP = 50;
	private static final String HASHTAG_CURTIR = "#epicwin";
	private static final String HASHTAG_REJEITAR = "#epicfail";
	
	public IconePop() {
	
	}
	
	@Override
	public void curtirPost(Usuario amigo, int post) {
		amigo.adicionaPops(PONTOS_POP);
		amigo.adicionaPopsNoPost(PONTOS_POP, post);
		amigo.adicionaHashtagNoPost(HASHTAG_CURTIR, post);
	}
	@Override
	public void rejeitarPost(Usuario amigo, int post) {
		amigo.removePops(PONTOS_POP);
		amigo.removePopsNoPost(PONTOS_POP, post);
		amigo.adicionaHashtagNoPost(HASHTAG_REJEITAR, post);
	}
	
	public List<Post> getPostsRecentes(List<Post> posts) {
		 Collections.sort(posts);
		 Collections.reverse(posts);
		 if(posts.size() >= 6) {
			 return posts.subList(0, 6); 
		 } else {
			 return posts.subList(0, posts.size());
		 }
		 
	}
	
	public String toString(){
		return "Icone Pop";
	}
		
}