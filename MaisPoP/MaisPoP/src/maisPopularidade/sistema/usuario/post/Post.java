package maisPopularidade.sistema.usuario.post;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import maisPopularidade.exception.PostException;
import maisPopularidade.exception.SystemException;
import maisPopularidade.sistema.Valida;
import maisPopularidade.sistema.usuario.post.midia.Audio;
import maisPopularidade.sistema.usuario.post.midia.Imagem;
import maisPopularidade.sistema.usuario.post.midia.Mensagem;
import maisPopularidade.sistema.usuario.post.midia.Midia;

public class Post implements Comparable<Post>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -965091221763962017L;
	private static final int MAXIMO_CARACTERES = 200;
	private List<Midia> midias;
	private List<Hashtag> hashtags;
	private LocalDate data;
	private LocalTime hora;
	private Valida valida;
	private int curtidas;
	private int pops;
	private static DateTimeFormatter formatador;
	private int rejeicoes;
	
	public Post(String mensagem, String data) throws SystemException {
		this.midias = new ArrayList<>();
		this.hashtags = new ArrayList<Hashtag>();
		this.valida = new Valida();
		
		if (!valida.conteudoPost(mensagem)) {
			throw new PostException("Conteudo do post nao pode ser nulo ou vazio.");
		}
		
		if (mensagem.contains("#")){
			if (separaMensagem(mensagem, "#").length()  >= MAXIMO_CARACTERES){
				throw new PostException("Nao eh possivel criar o post. O limite maximo da mensagem sao 200 caracteres.");	
			}
			String hashtagErro = valida.hashtags(mensagem);
			if (hashtagErro != null) {
				throw new PostException("Nao eh possivel criar o post. As hashtags devem comecar com '#'."
						+  " Erro na hashtag: '" + hashtagErro + "'.");
			}
			
			capturaHashtagsDoPost(mensagem);
		}
	
		midias.add(new Mensagem(separaMensagem(separaMensagem(mensagem, ("#")), "<")));
		formatador = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		this.pops = 0;
		this.data = LocalDate.parse(data.substring(0,data.indexOf(" ")), formatador);
		this.hora = LocalTime.parse(data.substring(data.indexOf(" ")+1));
		this.curtidas = 0;
		adicionaMidia(mensagem);
	}
	
	
	/**
	 * Separa o conteudo do post.
	 * @param conteudo
	 * @param atributo
	 * @return
	 */
	private String separaMensagem(String conteudo, String atributo) {
			ArrayList<String> mensagem = new ArrayList<String>();
			
			if (conteudo.contains(atributo)){
				String[] palavras = conteudo.split(" ");
				for (String palavra : palavras) {
					if (!palavra.startsWith(atributo)){
						mensagem.add(palavra);
					}
				}
				return String.join(" ", mensagem); 
			}else{
				return conteudo;
			}
		}
		
	/**
	 * Percorre o conteudo do post para verificar se há hashtags 
	 * @param conteudo
	 */
	private void capturaHashtagsDoPost(String conteudo) {
			String[] palavras = conteudo.split(" ");
			boolean controle = true;
			
			for (String palavra : palavras) {
				if (palavra.startsWith("#")){
					for (Hashtag outraTag : this.hashtags){
						if (outraTag.getNome().equals(palavra)){
							outraTag.incrementaOcorrencias();
							controle = false;
						}
					}
					if (controle == true){
						this.hashtags.add(new Hashtag(palavra, 1));
					}
				}				
			}
		}
		
	/**
	 * Caso o conteudo do post se trate de uma midia, 
	 * adiciona o mesmo na lista de midias.
	 * @param conteudo
	 */
	public void adicionaMidia(String conteudo){
			String[] palavras = conteudo.split(" ");
			
			if (conteudo.contains("<imagem>") || conteudo.contains("<audio>")){
				for (String palavra : palavras) {
					if (palavra.startsWith("<imagem>")) {
						 String caminho = palavra.substring(8, palavra.length()-9);
						 midias.add(new Imagem(caminho));
					
					}else if(palavra.startsWith("<audio>")){
						String caminho = palavra.substring(7, palavra.length()-8);
						midias.add(new Audio(caminho));
					}
				}
			}
		}

	public String getMensagem() {
			String mensagem = "";
			for (Midia midia : midias){
				if (midia instanceof Imagem){
					mensagem += " <imagem>"+midia.getCaminho()+"</imagem>";
				}else if(midia instanceof Audio){
					mensagem += " <audio>"+midia.getCaminho()+"</audio>";
				}else{
					mensagem += midia.getCaminho();
				}
			}
			return mensagem;
		}

	public String getDataEHora() {
			formatador = DateTimeFormatter.ofPattern("uuuu-MM-dd");
			return this.data.format(formatador)+" "+getHora();
		}
		public String getData(){
			formatador = DateTimeFormatter.ofPattern("uuuu-MM-dd");
			return this.data.format(formatador);
		}
		
	public String getHora() {
			this.formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
			return this.hora.format(formatador);
		}
		
	public void setData(LocalDate data) {
			this.data = data;
		}
		
	public String getHashtag(){
			String nomeHashtags = "";
			if(this.hashtags.size() > 0){
				nomeHashtags = this.hashtags.get(0).getNome();
				for (int i = 1; i < this.hashtags.size(); i++){
					nomeHashtags += ","+this.hashtags.get(i).getNome();
				}
			}
			return nomeHashtags;
		}
	
	/**
	 * Adiciona a hashtag na lista de hashtags e verifica suas ocorrencias.
	 * @param hashtag
	 */
	public void adicionaHashtag(String hashtag){
			boolean controle = true;
			for (Hashtag outraTag : this.hashtags){
				if(outraTag.getNome().equals(hashtag)){
					outraTag.incrementaOcorrencias();
					controle = false;
				}
			}
			if (controle == true){
				this.hashtags.add(new Hashtag(hashtag, 1));
			}
		}

	@Override				  
	public String toString() {
			String[] hashtags = getHashtag().split(",");
			return this.getMensagem()  + " " + String.join(" ", hashtags) + " (" + getDataEHora() + ")";
		}

	public int getCurtidas() {
			return curtidas;
		}

	/**
	 * Adiciona uma curtida ao post
	 */
	public void adicionaCurtida() {
			this.curtidas += 1;
		}
		
	public int getRejeicoes(){
			return this.rejeicoes;
		}
	
	/**
	 * Adiciona uma rejeicao ao post.
	 */
	public void adicionaRejeicao() {
			this.rejeicoes += 1;
		}
		
	public String getConteudoPost(int indice) throws PostException {
			if (indice >= midias.size()){
				throw new PostException("Item #"+indice+" nao existe nesse post, ele possui apenas "+midias.size()+" itens distintos.");
			}
			return midias.get(indice).toString();
		}
	
	/**
	 * Remove a quantidade pre-determinada de pontos de popularidade do usuario
	 * quando o mesmo recebe uma rejeicao no post.
	 * @param pops
	 */
	public void removePops(int pops){
			this.pops -= pops;
		}
	
	/**
	 * Adiciona uma  quantidade pre-determinada de pontos de popularidade do usuario
	 * quando o mesmo recebe uma curtida no post.
	 * @param pops
	 */
	public void adicionaPops(int pops){
			this.pops += pops;
		}
		
	public int getPops(){
			return this.pops;
		}
	
	/**
	 * Realiza a formatacao necessaria do post para que o mesmo saia conforme 
	 * o esperado nos testes.
	 * @return
	 */
	public String formataPost(){
			formatador = DateTimeFormatter.ofPattern("dd/MM/uuuu");
			String formatoSaida = "";
			formatoSaida += this.data.format(formatador) + " " + getHora() + "\n" +
					        "Conteudo:" + "\n";
			
			for(Midia midia : midias){
				if(midia instanceof Imagem){
					formatoSaida += "<imagem>"+midia.getCaminho() + "</imagem>"+"\n";
				}else if (midia instanceof Audio){
					formatoSaida += "<audio>"+midia.getCaminho() + "</audio>"+"\n";
				}else{
					formatoSaida += midia.toString() + "\n";
				}
			}
			
			if(getHashtag() != ""){
				formatoSaida += String.join(" ",getHashtag().split(",")) + "\n"; 
			}
			formatoSaida += "+Pop: " + getPops();
			 
			return formatoSaida ;
		}
		
	public LocalDate getDate() {
			return this.data;	
		}
		
	public LocalTime getHour() {
			return this.hora;
		}
		
	@Override
	public int compareTo(Post post) {
			if (this.data.equals(post.getDate())) {
				return this.hora.compareTo(post.getHour());
			} else {
				return this.data.compareTo(post.getDate());
			}
		}
		
}