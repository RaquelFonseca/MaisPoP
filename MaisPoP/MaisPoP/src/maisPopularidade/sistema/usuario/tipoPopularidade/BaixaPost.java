package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import maisPopularidade.sistema.usuario.post.Post;

public class BaixaPost {
	
	private ObjectOutputStream output;
	
	public BaixaPost() {
	}
	
	public void abreArquivo(File arquivo) throws IOException {
		output = new ObjectOutputStream(new FileOutputStream(arquivo));
	}
	
	public void escrevePostArquivo(List<Post> posts) throws IOException {
		for (int i = 0; i < posts.size(); i++) {
			Post post = posts.get(i);
			output.writeObject(post);	
		}
	}
	
	public void fechaArquivo() throws IOException {
		if (output != null) {
			output.close();
		}
	}
	
}
