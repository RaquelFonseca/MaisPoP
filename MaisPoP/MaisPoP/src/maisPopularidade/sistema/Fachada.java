package maisPopularidade.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import maisPopularidade.exception.SystemException;
import maisPopularidade.sistema.usuario.post.Post;
import easyaccept.EasyAccept;


public class Fachada {
	
	private Controle controle;
	
	public Fachada() {
		this.controle = new Controle();
	}
	
	public static void main(String[] args) {
		args = new String[] {"maisPopularidade.sistema.Fachada",
				"./testes" + "/usecase_1.txt",
				"./testes" + "/usecase_2.txt",
				"./testes" + "/usecase_3.txt",
				"./testes" + "/usecase_4.txt",
				"./testes" + "/usecase_5.txt",
				"./testes" + "/usecase_6.txt",
				"./testes" + "/usecase_7.txt",
				"./testes" + "/usecase_8.txt",
				"./testes" + "/usecase_9.txt",
				"./testes" + "/usecase_10.txt"
			};
		
		EasyAccept.main(args);
	}
	
	public void iniciaSistema() {
		try{
		File pasta= new File("arquivos");
		pasta.mkdir();
		File arquivo = new File(pasta, "sistema.dat");
		boolean criou = arquivo.createNewFile();
		
		if (!criou) {
			FileInputStream fi = new FileInputStream(arquivo);
			ObjectInputStream oi = new ObjectInputStream(fi);
			this.controle = (Controle) oi.readObject();	
			oi.close();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) throws SystemException {
		return controle.cadastraUsuario(nome, email, senha, dataNasc, imagem);	
	}
	
	public String cadastraUsuario(String nome, String dataNasc, String email, String senha) throws SystemException {
		return controle.cadastraUsuario(nome, dataNasc, email, senha, "resources/default.jpg");
	}
	
	public void login(String email, String senha) throws SystemException {
		 controle.login(email, senha);
	}
	
	public String getInfoUsuario(String atributo, String usuario) throws SystemException {
		return controle.getInfoUsuario(atributo, usuario);
	}
	
	public String getInfoUsuario(String atributo) throws SystemException {
		return controle.getInfoUsuario(atributo);
	}
	
	public void atualizaPerfil(String atributo, String valor) throws SystemException {
		controle.atualizaPerfil(atributo, valor);
	}
	
	public void atualizaPerfil(String atributo, String valor, String velhaSenha) throws SystemException {
		controle.atualizaPerfil(atributo, valor, velhaSenha);
	}
	
	public void criaPost(String mensagem, String data) throws SystemException {
		controle.criaPost(mensagem, data);
	}
	
	public Post getPost(int index) throws SystemException {
		return controle.getPost(index);
	}
	
	public String getPost(String atributo, int index){
		return controle.getPost(atributo, index);
	}
	
	public String getConteudoPost(int indice, int post) throws SystemException{

		return controle.getConteudoPost(indice, post);
	}
	
	public void curtirPost(String email, int index) throws SystemException{
		controle.curtirPost(email, index);		
	}
	
	public int qtdCurtidasDePost(int post) throws SystemException{
		return controle.qtdCurtidasDePost(post);
	}
	
	public void rejeitarPost(String email, int indice) throws SystemException{
		controle.rejeitarPost(email, indice);
	}
	
	public int qtdRejeicoesDePost(int post) throws SystemException{
		return controle.qtdRejeicoesDePost(post);
	}
	
	public int getPopsPost(int indicePost){
		return controle.getPopsPost(indicePost);
	}
	
	public void adicionaAmigo(String email) throws SystemException {
		controle.adicionaAmigo(email);
	}
	
	public void aceitaAmizade(String email) throws SystemException{
		controle.aceitaAmizade(email);
	}
	
	public void rejeitaAmizade(String email) throws SystemException{
		controle.rejeitaAmizade(email);
	}
	
	public void removeAmigo(String email) throws SystemException {
		controle.removeAmigo(email);
	}
	
	public int getQtdAmigos(){
		return controle.getQtdAmigos();
	}
	
	
	public int getNotificacoes(){
		return controle.getNotificacoes();
	}
	
	public String getNextNotificacao() throws SystemException{
		return controle.getNextNotificacao();
	}

	public int getPops(){
		return controle.getPops();
	}
	
	public void adicionaPops(int qtdPops){
		controle.adicionaPops(qtdPops);
	}
	
	public String getPopularidade(){
		return controle.getPopularidade();
	}
	
	public int getPopsUsuario(String email) throws SystemException{
		return controle.getPopsUsuario(email);
	}
	
	public int getPopsUsuario(){
		return controle.getPops();
	}
	
	public String atualizaRanking(){
		return controle.atualizaRanking();	
	}
	
	public String atualizaTrendingTopics(){
		return controle.atualizaTrendingTopics();
	}
	
	public void atualizaFeed() {
		controle.atualizaFeed();
		}
	
	public Post getPostFeedNoticiasRecentes(int index) {
		return controle.getPostFeedNoticiasRecentes(index);
	}
	
	public Post getPostFeedNoticiasMaisPopulares(int index) {
		return controle.getPostFeedNoticiasMaisPopulares(index);
	}
	
	public void baixaPosts() throws IOException, SystemException{
		controle.baixaPosts();
	}
	
	public int getTotalPosts() {
		return controle.getTotalPost();
	}
	
	public void logout() throws SystemException, IOException {
		controle.logout();
	}
	
	public void removeUsuario(String usuario) {
		controle.removeUsuario(usuario);
	}
	
	public void fechaSistema() throws SystemException {
		controle.fechaSistema();
		try{
			File pasta= new File("arquivos");
			pasta.mkdir();
			File arquivo = new File(pasta, "sistema.dat");
			arquivo.createNewFile();
			FileOutputStream fo = new FileOutputStream(arquivo);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(controle);
			oo.flush();
			oo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

