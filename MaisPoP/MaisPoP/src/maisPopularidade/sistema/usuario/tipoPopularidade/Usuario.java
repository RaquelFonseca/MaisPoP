package maisPopularidade.sistema.usuario.tipoPopularidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import maisPopularidade.exception.PostException;
import maisPopularidade.exception.SystemException;
import maisPopularidade.exception.UserException;
import maisPopularidade.sistema.Valida;
import maisPopularidade.sistema.usuario.feedNoticias.FeedNoticias;
import maisPopularidade.sistema.usuario.post.Post;

@SuppressWarnings("serial")
public class Usuario implements Comparable<Usuario>, Serializable{
	
	private static DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String nome;
	private LocalDate dataNasc;
	private String email;
	private String senha;
	private String imagem;
	private ArrayList<Post> posts;
	private ArrayList<String> listaDeSolicitacoes;
	private ArrayList<Usuario> listaDeAmigos;
	private ArrayList<String> notificacoes;
	private TipoPopularidade tipoPopularidade;
	private int pops;
	private FeedNoticias feedNoticias;
	public Valida valida;
	

	/**
	 * Constroi o usuario, considerando todos os atributos que o mesmo ira possuir.
	 * @param nome
	 * @param email
	 * @param senha
	 * @param dataNasc
	 * @param imagem
	 * @throws SystemException
	 */
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem) throws SystemException {
		
		super();
		
		this.valida = new Valida();
		
		if (!valida.nome(nome)) {
			throw new UserException(
					"Erro no cadastro de Usuarios. Nome dx usuarix nao pode ser vazio.");
		}

		if (!valida.email(email)) {
			throw new UserException(
					"Erro no cadastro de Usuarios. Formato de e-mail esta invalido.");
		}

		if (!valida.senha(senha)) {
			throw new UserException(
					"Erro no cadastro de Usuarios. Formato de senha esta invalido.");
		}

		if (!valida.formatoDataNasc(dataNasc)) {
			throw new UserException(
					"Erro no cadastro de Usuarios. Formato de data esta invalida.");
		}

		if (!valida.valorDataNasc(dataNasc)) {
			throw new UserException(
					"Erro no cadastro de Usuarios. Data nao existe.");
		}
		
		formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.feedNoticias = new FeedNoticias();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = LocalDate.parse(dataNasc, formatador);
		this.posts = new ArrayList<Post>();
		this.imagem = imagem;
		this.notificacoes = new ArrayList<String>();
		this.listaDeAmigos = new ArrayList<Usuario>();
		this.listaDeSolicitacoes = new ArrayList<String>();
		this.tipoPopularidade = new NormalPop();
		this.pops = 0;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getDataNasc() {
		return dataNasc.toString();
		
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = LocalDate.parse(dataNasc, formatador);
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	/**
	 * Apos criado, adiciona um post na lista de posts do usuario.
	 * @param novopost
	 */
	public void adicionaPost(Post novoPost) {
		this.posts.add(novoPost);
	}
	
	public ArrayList<Post> getPosts() {
		return posts;
	}
	
	public String getPost(String atributo, int index) {
		if (atributo.equalsIgnoreCase("mensagem")){
			return posts.get(index).getMensagem();
			
		} else if(atributo.equalsIgnoreCase("data")){
			
			return posts.get(index).getDataEHora();
			
		} else if (atributo.equalsIgnoreCase("hashtags")){
			return posts.get(index).getHashtag();
		}
		return null;	
	}
	
	/**
	 * Percorre a lista de posts e retorna o indice do post desejado.
	 * @param index
	 * @return
	 * @throws PostException
	 */
	public Post getPostPeloIndex(int index) throws PostException {
		if (index >= posts.size()) {
			throw new PostException("");
		}
		return (Post) posts.get(index);
	}
	
	public String getConteudoPost(int indice, int post) throws PostException{
		if (indice < 0 || post < 0){
			throw new PostException("Requisicao invalida. O indice deve ser maior ou igual a zero.");
		}
		return this.posts.get(post).getConteudoPost(indice);
				
	}
	
	/**
	 * Permite que o usuario logado curta o post de outro usuario do sistema.
	 * @param amigo
	 * @param indice
	 * @throws PostException
	 */
	public void curtirPost(Usuario amigo, int indice) throws PostException{
		Post umPost = amigo.getPostPeloIndex(indice);
	    umPost.adicionaCurtida();
		amigo.setNotificacoes(this.nome+" curtiu seu post de "+umPost.getDataEHora()+".");
		tipoPopularidade.curtirPost(amigo, indice);
		
	}
	/**
	 * Permite que o usuario logado rejeite o post de outro usuario do sistema.
	 * @param amigo
	 * @param indice
	 * @throws PostException
	 */
	public void rejeitaPost(Usuario amigo, int indice) throws PostException{
		Post umPost = amigo.getPostPeloIndex(indice);
		umPost.adicionaRejeicao();
		amigo.setNotificacoes(getNome()+" rejeitou seu post de "+umPost.getDataEHora()+".");
		tipoPopularidade.rejeitarPost(amigo, indice);
		
	}

	public void setListaDeAmigos(Usuario novoAmigo){
		this.listaDeAmigos.add(novoAmigo);
	}
	
	public ArrayList<String> getListaDeSolicitacoes(){
		return this.listaDeSolicitacoes;
	}
	
	public void setListaDeSolicitacoes(String nomeAmigo){
		this.listaDeSolicitacoes.add(nomeAmigo);
	}
	
	public ArrayList<Usuario> getListaDeAmigos() {
		return listaDeAmigos;
	}

	/**
	 * Adiciona o usuario na lista de amigos do usuario logado.
	 * @param nome
	 */
	public void adicionaAmigo(String nome) {
		setListaDeSolicitacoes(nome);
	}
	
	/**
	 * Remove o usuario da lista de amigos do usuario logado.
	 * @param amigoRemovido
	 * @throws SystemException
	 */
	public void removeAmigo(Usuario amigoRemovido) throws SystemException{
		if (!listaDeAmigos.contains(amigoRemovido)){
			throw new SystemException(amigoRemovido.getNome()+" nao esta na sua lista de amigos.");
		
		}else{
			
		this.listaDeAmigos.remove(amigoRemovido);
		amigoRemovido.setNotificacoes(this.nome+" removeu a sua amizade.");
		}
	}

	public String getNextNotificacao(){
		String notificacao = notificacoes.get(0);
		notificacoes.remove(0);
		return notificacao;
	}
	
	public int getNotificacoes(){
		return this.notificacoes.size();
	}

	public void setNotificacoes(String notificacao) {
		this.notificacoes.add(notificacao);
	}
	
	/**
	 * Aceita a solicitacao de amizade enviada para o usuario logado, 
	 * adicionando o novo amigo na lista de amigos do usuario logado
	 * e removendo-o da lista de solicitacoes.
	 * @param usuario
	 */
	public void aceitaAmizade(Usuario usuario){
		this.listaDeAmigos.add(usuario);
		listaDeSolicitacoes.remove(usuario);
	}
	
	/**
	 * Adiciona o usuario desejado a lista de amigos do usuario logado.
	 * @param usuario
	 */
	public void adicionaAmigo(Usuario usuario){
		this.listaDeAmigos.add(usuario);
	}
	
	/**
	 * Rejeita a solicitacao de amizade enviada pelo usuario qualquer
	 * @param usuario
	 */
	public void rejeitaAmizade(Usuario usuario){
		listaDeSolicitacoes.remove(usuario);
	}
	
	public int getQtdAmigos(){
		return listaDeAmigos.size();
	}
	
	/**
	 * Atribue a devida quantidade de pontos de popularidade ao usuario.
	 * @param pops
	 */
	public void adicionaPops(int pops){
		this.pops += pops;
		atualizaPopularidade();
	}
	
	/**
	 * Remove a devida quantidade de pontos de popularidade do usuario.
	 * @param pops
	 */
	public void removePops(int pops){
		this.pops -= pops;
	}
	
	public int getPops(){
		return this.pops;
	}
	
	public String toString(){
		return this.nome+" - "+this.email;
	}

	/**
	 * Realiza a mudança do estado do usuario para Normal.
	 * @return
	 */
	private TipoPopularidade viraNormal() {
		return this.tipoPopularidade = new NormalPop();
	}
	
	/**
	 * Realiza a mudança do estado do usuario para CelebridadePop.
	 * @return
	 */
	private TipoPopularidade viraCelebridadePop() {
		return this.tipoPopularidade = new CelebridadePop();
	}

	/**
	 * Realiza a mudança do estado do usuario para IconePop.
	 * @return
	 */
	private TipoPopularidade viraIconePop() {
		return this.tipoPopularidade = new IconePop();
	}
	
	/**
	 * De acordo com os pontos de popularidade, efetua uma atribuicao de estado ao usuario.
	 * @return
	 */
	public String atualizaPopularidade(){
		if (this.pops < 500){
			return viraNormal().toString();
		}
		else if (this.pops > 500 & this.pops < 1000){
			return viraCelebridadePop().toString();
		}
		else if (this.pops >= 1000){
			return viraIconePop().toString();
		}
		return null;
	}
	
	/**
	 * Dado o email do usuario desejado, é realizada uma busca pelo mesmo
	 * na lista de amigos do usuario logado.
	 * @param email
	 * @return
	 */
	public Usuario pesquisaAmigo(String email){
		for (Usuario amigo : listaDeAmigos){
			if (amigo.getEmail().equals(email)){
				return amigo;
			}
		}
		return null;
	}
	
	@Override
	public int compareTo(Usuario user) {
		if ( this.pops < user.getPops()){
			return -1;
		}
		if (this.pops > user.getPops()){
			return 1;
		}
		return this.email.compareTo(user.getEmail());
		}
	
	public String getPopularidade(){
		return atualizaPopularidade();
	}
	
	public int getPopsPost(int indicePost) {
		Post post = posts.get(indicePost);
		return post.getPops();
	}
	
	/**
	 * Adiciona a devida quantidade de pontos de popularidade ao post,
	 * dependendo do estado do usuario que curtiu o post.
	 * @param qtdPops
	 * @param indicePost
	 */
	public void adicionaPopsNoPost(int qtdPops, int indicePost){
		posts.get(indicePost).adicionaPops(qtdPops);
	}
	
	/**
	 * Remove a devida quantidade de pontos de popularidade do post,
	 * dependendo do estado do usuario que rejeitou o post.
	 * @param qtdPops
	 * @param indicePost
	 */
	public void removePopsNoPost(int qtdPops, int indicePost){
		posts.get(indicePost).removePops(qtdPops);
	}
	
	/**
	 * Adiciona uma hashtag ao conteudo do post.
	 * @param hashtag
	 * @param indicePost
	 */
	public void adicionaHashtagNoPost(String hashtag, int indicePost){
		posts.get(indicePost).adicionaHashtag(hashtag);
	}

	public List<Post> getPostsRecentes() {
		return tipoPopularidade.getPostsRecentes(posts);
	}
	
	/**
	 * Realiza uma atualizacao do feed de noticias do usuario logado.
	 */
	public void atualizaFeed() {
		feedNoticias.atualizaFeed(listaDeAmigos);
	}
	
	/**
	 * Ordena o feed de noticias pela data, de forma que os primeiro sejam mais recentes.
	 */
	public void ordenaFeedPorData() {
		 feedNoticias.ordenaPorData();
		
	}
	
	/**
	 * Ordena o feed de noticias pela quantidade de popularidade,
	 * de forma que os primeiro sejam os com mais pops.
	 */
	public void ordenaFeedPorPops() {
		feedNoticias.ordenaPorPops();
		
	}
	
	/**
	 * Realiza a ordenacso dos posts de acordo com a lista de posts.
	 * @return
	 */
	public List<Post> ordenaFeed() {
		return feedNoticias.ordena();
	}
	
	/**
	 * Cria uma copia de cada post,
	 * para que sua copia seja ordenado de outra forma posteriormente.
	 * @return
	 */
	public ArrayList<Post> criaCopiaDosPosts(){
		ArrayList<Post> copia = new ArrayList<Post>();
		copia.addAll(posts);
		return copia;
	}
	
}
