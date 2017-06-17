package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.io.Serializable;
import java.util.List;

import maisPopularidade.exception.PostException;
import maisPopularidade.sistema.usuario.post.Post;

public interface TipoPopularidade  extends Serializable{
	
	
	public void curtirPost(Usuario user, int post) throws PostException;
	
	public void rejeitarPost(Usuario user, int post) throws PostException;

	public List<Post> getPostsRecentes(List<Post> posts);
}