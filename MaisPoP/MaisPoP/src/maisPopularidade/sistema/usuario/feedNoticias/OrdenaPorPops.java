package maisPopularidade.sistema.usuario.feedNoticias;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public class OrdenaPorPops implements OrdenadorFeed {

	/**
	 * 
	 */
	private static final long serialVersionUID = -441659517510142208L;

	@Override
	public void ordena(List<Post> posts) {
		Collections.sort(posts, new Comparator<Post>() {
			@Override
			public int compare(Post umPost, Post outroPost) {
				if (umPost.getPops() < outroPost.getPops()){
					return -1;
				}
				else if (umPost.getPops() > outroPost.getPops()){
					return 1;
				}
				else {
					return 0;
				}
			}
		});
	}
}
