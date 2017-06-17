package maisPopularidade.sistema.usuario.feedNoticias;

import java.io.Serializable;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public interface OrdenadorFeed extends Serializable {
	
	public void ordena(List<Post> posts);

}
