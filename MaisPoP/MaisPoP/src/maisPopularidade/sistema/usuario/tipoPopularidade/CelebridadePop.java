package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import maisPopularidade.exception.PostException;
import maisPopularidade.sistema.usuario.post.Post;

public class CelebridadePop implements TipoPopularidade {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8906470057382036554L;
	private static final int PONTOS_POP = 25;
	private static final int POST_RECENTE = 10;	
	private static DateTimeFormatter formatador = DateTimeFormatter.ofPattern("uuuu-MM-dd");
	LocalDate dataPost;
	
	public CelebridadePop() {
	
	}
	
	@Override
	public void curtirPost(Usuario amigo, int post) throws PostException {
		
		dataPost = LocalDate.parse(amigo.getPostPeloIndex(post).getData(), formatador);
		
		if (dataPost.equals(LocalDate.now())){
			amigo.adicionaPops(POST_RECENTE);
			amigo.adicionaPopsNoPost(POST_RECENTE, post);
		}
		
		amigo.adicionaPopsNoPost(PONTOS_POP, post);
		amigo.adicionaPops(PONTOS_POP);
		
	}
	
	@Override
	public void rejeitarPost(Usuario amigo, int post) throws PostException {
		
		dataPost = LocalDate.parse(amigo.getPostPeloIndex(post).getData(), formatador);
		
		if (dataPost.equals(LocalDate.now())){
			amigo.removePops(POST_RECENTE);
			amigo.removePopsNoPost(POST_RECENTE, post);
		}
		
		amigo.removePopsNoPost(PONTOS_POP, post);
		amigo.removePops(PONTOS_POP);
	}
	
	@Override
	public List<Post> getPostsRecentes(List<Post> posts) {
		 Collections.sort(posts);
		 Collections.reverse(posts);
		 if (posts.size() >= 4) {
			 return posts.subList(0,4);
		 } else {
			 return posts.subList(0, posts.size());
		 }
		 
	}
	
	public String toString(){
		return "Celebridade Pop";
	}
		
}