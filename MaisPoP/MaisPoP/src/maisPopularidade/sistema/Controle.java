package maisPopularidade.sistema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import maisPopularidade.exception.PostException;
import maisPopularidade.exception.SystemException;
import maisPopularidade.sistema.usuario.post.Hashtag;
import maisPopularidade.sistema.usuario.post.Post;
import maisPopularidade.sistema.usuario.tipoPopularidade.Usuario;

public class Controle implements Serializable {


	private static final long serialVersionUID = -1182662643557580533L;
	private ArrayList<Usuario> usuariosCadastrados;
	private Usuario usuarioLogado;
	private ArrayList<Hashtag> hashtagsErro = new ArrayList<Hashtag>(); 

	public Controle() {
		this.usuariosCadastrados = new ArrayList<Usuario>();
		this.usuarioLogado = null;
	}
	
	/**
	 * Cadastra o usuario no sistema.
	 * @param nome
	 * @param email
	 * @param senha
	 * @param dataNasc
	 * @param imagem
	 * @return
	 * @throws SystemException
	 */
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws SystemException {
		
		Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
		armazenaUsuario(novoUsuario);
		return novoUsuario.getEmail();
	}

	/**
	 * Verifica primeiramente se o usuario já esta cadastrado,
	 * caso nao esteja, adiciona na lista de usuarios cadastrados.
	 * @param novoUsuario
	 */
	public void armazenaUsuario(Usuario novoUsuario) {
		if (!this.usuariosCadastrados.contains(novoUsuario)) {
			this.usuariosCadastrados.add(novoUsuario);
		}
	}
	
	public ArrayList<Usuario> getUsuariosCadastrados() {
		return usuariosCadastrados;
	}

	public void login(String email, String senha) throws SystemException {
		Usuario usuarioProcurado = procuraUsuarioPeloEmail(email);
		if (usuarioLogado == null) {
			if (usuarioProcurado != null) {
				if (usuarioProcurado.getEmail().equals(email) && usuarioProcurado.getSenha().equals(senha)) {
					setUsuarioLogado(usuarioProcurado);
					
				} else if (senha != usuarioProcurado.getSenha()) {
					throw new SystemException(
							"Nao foi possivel realizar login. Senha invalida.");
				
				} else if (email != usuarioProcurado.getEmail()) {
					throw new SystemException(
							"Nao foi possivel realizar login. Um usuarix com email "
									+ email + " nao esta cadastradx.");
					}
			} else {
				throw new SystemException(
						"Nao foi possivel realizar login. Um usuarix com email "
								+ email + " nao esta cadastradx.");
				}
		} else {
			throw new SystemException(
					"Nao foi possivel realizar login. Um usuarix ja esta logadx: "
							+ "" + usuarioLogado.getNome() + ".");
		}
		
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	/**
	 * Busca o usuario na lista de usuarios cadastrados.
	 * @param usuario
	 * @return
	 */
	public Usuario procuraUsuario(Usuario usuario) {
		if (this.usuariosCadastrados.contains(usuario)) {
			return usuario;
		}
		return null;
	}

	/**
	 * Procura na lista o usuario desejado pelo email e retorna o indice do usuario procurado.
	 * @param email
	 * @return
	 */
	public int retornaIndiceDoUsuario(String email) {
		for (Usuario usuario : usuariosCadastrados) {
			if (usuario.getEmail().equals(email)) {
				return this.usuariosCadastrados.indexOf(usuario);
			}
		}
		return -1;
	}

	/**
	 * Realiza uma busca pelo usuario na lista utilizando seu email.
	 * @param email
	 * @return
	 */
	public Usuario procuraUsuarioPeloEmail(String email) {
		for (Usuario usuario: usuariosCadastrados){
			if (usuario.getEmail().equals(email)){
				return usuario;
			}
		}
		return null;
	}
	
	/**
	 * Usuario sai de sua conta.
	 * @throws SystemException
	 */
	public void logout() throws SystemException{
		if (usuarioLogado != null) {
				
			setUsuarioLogado(null);
		} else {
			throw new SystemException(
					"Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
		}
	}

	/**
	 * Faz uma busca pelo usuario e o remove da lista de usuarios do sistema.
	 * @param email
	 */
	public void removeUsuario(String email) {
		Usuario usuarioParaRemover = procuraUsuarioPeloEmail(email);
		if(usuarioParaRemover != null) {
			usuariosCadastrados.remove(usuarioParaRemover);
		}
	}

	/**
	 * Retorna as informacoes de qualquer usuario do sistema.
	 * @param atributo
	 * @param email
	 * @return
	 * @throws SystemException
	 */
	public String getInfoUsuario(String atributo, String email) throws SystemException {
		Usuario usuarioProcurado = procuraUsuarioPeloEmail(email);
		if (usuarioProcurado != null) {
			if (atributo.equalsIgnoreCase("email")) {
				return usuarioProcurado.getEmail();
				
			} else if (atributo.equalsIgnoreCase("nome")) {
				return usuarioProcurado.getNome();
				
			} else if (atributo.equalsIgnoreCase("Data de Nascimento")) {
				String[] data = usuarioProcurado.getDataNasc().split("/");
				String dataUsuario = "";
				for (int i = data.length - 1; i >= 0; i--) {
					if (i == 0) {
						dataUsuario = dataUsuario + data[i];
					} else {
						dataUsuario = dataUsuario + data[i] + "-";
					}
				}
				return dataUsuario;
				
			} else if (atributo.equalsIgnoreCase("foto")
					|| atributo.equalsIgnoreCase("imagem")) {
				return usuarioProcurado.getImagem();
				
			} else if (atributo.equalsIgnoreCase("senha")) {
				throw new SystemException("A senha dx usuarix eh protegida.");
			}
		} else {
			throw new SystemException("Um usuarix com email " + email
					+ " nao esta cadastradx.");
		}
		return null;
	}

	/** 
	 * Retorna as informacoes do usuario que esta logado atualmente no sistema.
	 * @param atributo
	 * @return
	 * @throws SystemException
	 */
	public String getInfoUsuario(String atributo) throws SystemException {
		if (usuarioLogado != null) {
			if (atributo.equalsIgnoreCase("email")) {
				return usuarioLogado.getEmail();
				
			} else if (atributo.equalsIgnoreCase("nome")) {
				return usuarioLogado.getNome();
				
			} else if (atributo.equalsIgnoreCase("data de nascimento")) {
				String[] data = usuarioLogado.getDataNasc().split("/");
				String dataUsuario = "";
				for (int i = data.length - 1; i >= 0; i--) {
					if (i == 0) {
						dataUsuario = dataUsuario + data[i];
					} else {
						dataUsuario = dataUsuario + data[i] + "-";
					}
				}
				return dataUsuario;
				
			} else if (atributo.equalsIgnoreCase("foto") || atributo.equalsIgnoreCase("imagem")) {
				return usuarioLogado.getImagem();
				
			} else if (atributo.equalsIgnoreCase("senha")) {
				throw new SystemException("A senha dx usuarix eh protegida.");
			}
		}
		return null;
	}
	
	/**
	 * Sai do sistema.
	 * @throws SystemException
	 */
	public void fechaSistema() throws SystemException {
		if (usuarioLogado != null) {
			throw new SystemException("Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
		}
	}
	
	/**
	 * Atualiza as informacoes do perfil do usuario.
	 * @param atributo
	 * @param valor
	 * @throws SystemException
	 */
	public void atualizaPerfil(String atributo, String valor) throws SystemException {
		if (usuarioLogado != null) {
			if (atributo.equalsIgnoreCase("email") || atributo.equalsIgnoreCase("E-mail")) {
				if(usuarioLogado.valida.email(valor)){
					usuarioLogado.setEmail(valor);
				} else {
					throw new SystemException("Erro na atualizacao de perfil. Formato de e-mail esta invalido.");
			}
			
		} else if (atributo.equalsIgnoreCase("nome")) {
			if (usuarioLogado.valida.nome(valor)) {
				usuarioLogado.setNome(valor);
			} else {
				throw new SystemException("Erro na atualizacao de perfil. Nome dx usuarix nao pode ser vazio.");
			}
			
		} else if (atributo.equalsIgnoreCase("data de nascimento")) {
			if (!usuarioLogado.valida.formatoDataNasc(valor)) {
				throw new SystemException("Erro na atualizacao de perfil. Formato de data esta invalida.");
			}else if (!usuarioLogado.valida.valorDataNasc(valor)) {
				throw new SystemException("Erro na atualizacao de perfil. Data nao existe.");
			} else{
				usuarioLogado.setDataNasc(valor);
				}
			
		} else if (atributo.equalsIgnoreCase("foto") || atributo.equalsIgnoreCase("imagem")) {
			usuarioLogado.setImagem(valor);
		}
			}else {
				throw new SystemException("Erro na atualizacao de perfil. Nenhum usuarix esta logadx no +pop.");
			}
	}
	
	/**
	 * Atualiza as informacoes do perfil do usuario.
	 * @param atributo
	 * @param valor
	 * @param velhaSenha
	 * @throws SystemException
	 */
	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws SystemException {
		if (usuarioLogado != null) {
				if (usuarioLogado.getSenha().equals(velhaSenha) & usuarioLogado.valida.senha(valor)) {
					usuarioLogado.setSenha(valor);
			} else  {
				throw new SystemException("Erro na atualizacao de perfil. A senha fornecida esta incorreta.");
		}	
	}else {
		throw new SystemException("Nao eh possivel atualizar um perfil. Nenhum usuarix esta logadx no +pop.");
	}
				}

	/**
	 * Cria um novo post relacionado ao usuario.
	 * @param mensagem
	 * @param data
	 * @throws SystemException
	 */
	public void criaPost(String mensagem, String data) throws SystemException {
		if (usuarioLogado == null) {
			throw new SystemException("Nao eh possivel criar post. Nenhum usuarix esta logadx no +pop");
			
		}else {
						
			Post novoPost = new Post(mensagem, data);
			usuarioLogado.adicionaPost(novoPost);
			if (mensagem.contains("#")){
				adicionaHashtag(novoPost.getHashtag());
			}
		}
	}

	/**
	 * Adiciona uma hashtag ao post.
	 * @param hashtags
	 */
	private void adicionaHashtag(String hashtags) {
		boolean verificador = true;
		String[] tags = hashtags.split(",");

		for (String tag : tags){
			for (Hashtag outraTag : hashtagsErro){
				if (outraTag.getNome().equals(tag)){
					outraTag.incrementaOcorrencias();
					verificador = false;
				}
			}
			if (verificador == true){
				this.hashtagsErro.add(new Hashtag(tag, 1));
			}
		}
	}

	public Post getPost(int index) throws SystemException {
		return usuarioLogado.getPostPeloIndex(index);
	}
	
	public String getPost(String atributo, int index){
		return usuarioLogado.getPost(atributo, index);
	}
	
	public String getConteudoPost(int indice, int post) throws SystemException {
		return usuarioLogado.getConteudoPost(indice, post);
	}
	
	/**
	 * Permite que o usuario logado curta o post de outro usuario do sistema.
	 * @param email
	 * @param indice
	 * @throws PostException
	 */
	public void curtirPost(String email, int indice) throws PostException{
		Usuario tempUser = procuraUsuarioPeloEmail(email);
		usuarioLogado.curtirPost(tempUser, indice);
		if(tempUser.getPopularidade().equals("Icone Pop")){
			adicionaHashtag("#epicwin,");
		}
	}
	
	
	/**
	 * Permite que o usuario logado rejeite o post de outro usuario do sistema.
	 * @param email
	 * @param indice
	 * @throws PostException
	 */
	public void rejeitarPost(String email, int indice) throws PostException{
		Usuario tempUser = procuraUsuarioPeloEmail(email);
		usuarioLogado.rejeitaPost(tempUser, indice);
		if (tempUser.getPopularidade().equals("Icone Pop")){
			adicionaHashtag("#epicfail,");
		}
	}

	/**
	 * Procura por um usuario cadastrado e o adiciona na lista de amigos do usuario logado.
	 * @param email
	 * @throws SystemException
	 */
	public void adicionaAmigo(String email) throws SystemException{
		Usuario tempUser = procuraUsuarioPeloEmail(email);
		if(procuraUsuarioPeloEmail(email) == null){
			throw new SystemException("Um usuarix com email "+ email +" nao esta cadastradx.");
		
		}else{
			tempUser.setNotificacoes(usuarioLogado.getNome()+" quer sua amizade.");
			tempUser.adicionaAmigo(usuarioLogado.getNome());
		}
	}
	
	/**
	 * Remove um usuario cadastrado da lista de amigos do usuario logado.
	 * @param email
	 * @throws SystemException
	 */
	public void removeAmigo(String email) throws SystemException{
		Usuario amigoRemovido = procuraUsuarioPeloEmail(email);
		if(amigoRemovido == null){
			throw new SystemException("Um usuarix com email "+ email +" nao esta cadastradx.");
		
		}else{
			usuarioLogado.removeAmigo(amigoRemovido);
			amigoRemovido.removeAmigo(usuarioLogado);
		}
	}
	
	public int getNotificacoes(){
		return usuarioLogado.getNotificacoes();
	}
	
	public String getNextNotificacao() throws SystemException{
		if (usuarioLogado.getNotificacoes() == 0){
			throw new SystemException("Nao ha mais notificacoes.");
		
		}else{
			return usuarioLogado.getNextNotificacao();
			
		}
	}
	
	/**
	 * Usuario logado rejeita a solicitacao de amizade enviada por um usuario cadastrado.
	 * @param email
	 * @throws SystemException
	 */
	public void rejeitaAmizade(String email) throws SystemException{
		Usuario usuarioProcurado = procuraUsuarioPeloEmail(email);
		
		if(usuarioProcurado == null){
			throw new SystemException("Um usuarix com email "+email+" nao esta cadastradx.");
		
		}else if (!usuarioLogado.getListaDeSolicitacoes().contains(usuarioProcurado.getNome())){
			throw new SystemException(usuarioProcurado.getNome()+" nao lhe enviou solicitacoes de amizade."); 
			
		}else{
		usuarioProcurado.setNotificacoes(usuarioLogado.getNome()+" rejeitou sua amizade.");;	
		usuarioLogado.rejeitaAmizade(usuarioProcurado);
		}
	}
	
	/**
	 * Usuario logado aceita a solicitacao de amizade de amizade de um usuario cadastrado.
	 * @param email
	 * @throws SystemException
	 */
	public void aceitaAmizade(String email) throws SystemException{
		Usuario usuarioProcurado = procuraUsuarioPeloEmail(email);
		
		if(usuarioProcurado == null){
			throw new SystemException("Um usuarix com email "+ email +" nao esta cadastradx.");
		
		}else if(!usuarioLogado.getListaDeSolicitacoes().contains(usuarioProcurado.getNome())){
			throw new SystemException(usuarioProcurado.getNome()+" nao lhe enviou solicitacoes de amizade.");
		
		}else{
			usuarioProcurado.setNotificacoes(usuarioLogado.getNome()+ " aceitou sua amizade.");
			usuarioLogado.aceitaAmizade(usuarioProcurado);
			usuarioProcurado.adicionaAmigo(usuarioLogado);
		}
	}
	
	public int getQtdAmigos(){
		return usuarioLogado.getQtdAmigos();
	}
	
	public int getPops(){
		return usuarioLogado.getPops();
	}
	
	public int getPopsUsuario(String email) throws SystemException{
		Usuario tempUser = procuraUsuarioPeloEmail(email);
		if (usuarioLogado != null){
			throw new SystemException("Erro na consulta de Pops. Um usuarix ainda esta logadx.");
		}
		
		return tempUser.getPops();
	}
	
	public int getPopsPost(int indicePost){
		return usuarioLogado.getPopsPost(indicePost);
	}

	/** 
	 * Adiciona os devidos pontos de popularidade a variavel qtdPops.
	 * @param qtdPops
	 */
	public void adicionaPops(int qtdPops) {
		usuarioLogado.adicionaPops(qtdPops);
	}

	public String getPopularidade() {
		return usuarioLogado.getPopularidade();
	}

	/**
	 * Contabiliza a quantidade de curtidas de um post.
	 * @param post
	 * @return
	 * @throws PostException
	 */
	public int qtdCurtidasDePost(int post) throws PostException {
		if (post >= usuarioLogado.getPosts().size()){
			throw new PostException("Post #"+post+" nao existe. Usuarix possui apenas "+usuarioLogado.getPosts().size()+" post(s).");
		}
		return usuarioLogado.getPostPeloIndex(post).getCurtidas();
	}

	/** 
	 * Retorna a quantidade de rejeicoes que um post teve.
	 * @param post
	 * @return
	 * @throws PostException
	 */
	public int qtdRejeicoesDePost(int post) throws PostException {
		if (post >= usuarioLogado.getPosts().size()){
			throw new PostException("Post #"+post+" nao existe. Usuarix possui apenas "+usuarioLogado.getPosts().size()+" post(s).");
		}
		return usuarioLogado.getPostPeloIndex(post).getRejeicoes();
	}
	
	/**
	 * Ordena a lista de pontos de popularidade em ordem crescente, depois em decrescente e 
	 * apos isso retorna respectivamente os usuarios menos populares e mais populares. 
	 * @return
	 */
	public String atualizaRanking(){
		Collections.sort(usuariosCadastrados);
		String menosPopulares = "Menos Populares:";
		String maisPopulares = "Mais Populares:";
		for(int i=0;i<3;i++){
			if (i < usuariosCadastrados.size())
			menosPopulares +=  " (" +  (i+1) + ") " + usuariosCadastrados.get(i).getNome() 
			+ " " +usuariosCadastrados.get(i).getPops() + ";";
		}
		Collections.reverse(usuariosCadastrados);
		for(int i=0;i<3;i++){
			if (i < usuariosCadastrados.size())
			maisPopulares +=  " (" +  (i+1) + ") " + usuariosCadastrados.get(i).getNome() 
			+ " " + usuariosCadastrados.get(i).getPops() + ";" ;
		}
		return maisPopulares + " | " + menosPopulares;
	}

	/**
	 * Atualiza o ranking das 3 hashTags que aparecem mais vezes nos posts dos usuarios.
	 * @return
	 */
	public String atualizaTrendingTopics() {
		Collections.sort(hashtagsErro);
		Collections.reverse(hashtagsErro);
		String trendingTopics = "Trending Topics: ";
				
		trendingTopics += " ("+(1)+") "+this.hashtagsErro.get(0).toString()+";";
		trendingTopics += " ("+(2)+") "+this.hashtagsErro.get(1).toString()+";";
		trendingTopics += " ("+(3)+") "+this.hashtagsErro.get(2).toString()+";";
		return trendingTopics;
	}
	
	/**
	 * Atualiza as informacoes do feed de noticias do usuario que está logado.
	 */
	public void atualizaFeed(){
		usuarioLogado.atualizaFeed();
	}

	public Post getPostFeedNoticiasRecentes(int index) {
		usuarioLogado.ordenaFeedPorData();
		return usuarioLogado.ordenaFeed().get(index);
	}
	
	public Post getPostFeedNoticiasMaisPopulares(int index) {
		usuarioLogado.ordenaFeedPorPops();
		return usuarioLogado.ordenaFeed().get(index);
	}
	
	/** 
	 * Modifica o email do usuario para ser utilizado no arquivo.
	 * @return
	 */
	private String mudaEmailParaArquivo(){
		String[] email = usuarioLogado.getEmail().split("@");
		email = String.join("[at]",email).split("");
		int i = 0;
		String novoEmail = "";
		
		do{
			if(!email[i].equals(".")){
				novoEmail += email[i];
			}
			i++;
		}while(i<email.length);
		
		return novoEmail;
	}
	
	/**
	 * 
	 * Armazena o conteudo dos posts dos usuarios em arquivos.
	 * @throws IOException
	 * @throws SystemException
	 */
	public void baixaPosts() throws IOException,SystemException{
		
		if(usuarioLogado.getPosts().size() == 0){
			throw new SystemException("Erro ao baixar posts. O usuario nao possui posts.");
		}
		
		ArrayList<Post> copiaPosts = usuarioLogado.criaCopiaDosPosts();
		
		Collections.sort(copiaPosts);
		
		File novoArquivo = new File("./arquivos/posts_" + mudaEmailParaArquivo() + ".txt");
	
		FileWriter fileWriter = new FileWriter(novoArquivo);
		BufferedWriter escrevedor = new BufferedWriter(fileWriter);
		
		escrevedor.write("Post #"+(1)+" - "+copiaPosts.get(0).formataPost());
		
		for (int i = 1; i < copiaPosts.size(); i++){
			escrevedor.write("\n");
			escrevedor.write("\n"+
							 "\n"+
							"Post #"+(i+1)+" - "+copiaPosts.get(i).formataPost());
		}
		escrevedor.close();
		fileWriter.close();
		
	}
	
	public int getTotalPost() {
		return usuarioLogado.getPosts().size();
	}
}
