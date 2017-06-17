package maisPopularidade.sistema.usuario.feedNoticias;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public class OrdenaPorData implements OrdenadorFeed {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1014275521468476232L;

	@Override
	public void ordena(List<Post> posts) {
		Collections.sort(posts);
		
	}

}
