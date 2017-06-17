########################################################################## Universidade Federal de Campina Grande – UFCG 
###################################################################### Centro de Engenharia Eletrica e Informatica – CEEI 
############################################################################# Departamento de Sistemas e Computacao – DSC 
 


#    Relatorio do projeto da disciplina Laboratorio de Programacao II
                                
# # # # # # # # # # # # # # #  +Popolaridade # # # # # # # # # # # # # # # # # # # #




##Introducao

O projeto da disciplina, neste periodo, foi a implementacao de uma rede social, a +Pop. Foi usada uma
arquitetura considerando as entidades Fachada e Controle. E nao foi necessario a implementacaoo de 
Interface Grafica do Usuario. No lugar da mesma, foram realizados testes de aceitacao automaticos por 
meio do EasyAccept.


###### Observacoes: 
###### em alguns casos, sistema sera usado como sinonimo de controle.
###### ao final dos casos de uso eh aprensentado um resumo do design 

##Caso de uso 1: Cadastrar usuarios, longin, logout

Para cadastrar os usuarios no sistema foram usados dois metodos no Controle de mesmo nome - cadastraUsuaio -
com parametros diferentes,  um recebendo nome, email, senha, data de nascimento e imagem e o outro recebendo
os mesmos parametros exceto a imagem, para o caso do usuario nao especificar uma foto, nesse caso ele sera 
cadastrado no sistema com uma imagem padrao.

Se os dados passados forem validos , o usuario eh cadastrado e salvo (adicionado na lista de usuarios 
cadastrados) no sistema e pode usar seu e-mail e senha para acessar sua conta do sistema, ou seja, fazer o 
login, assim como, ele pode realizar logout para sair do sistema.
 

##Caso de uso 2: Pesquisar e atulizar informacoes de usuarios

Para o sistema pesquisar as informacoes dos usuarios foram usados dois metodos de mesmo nome - getInfoUsuario- 
com parametros diferentes, um recebendo a informacao que se deseja pesquisar, (o nome do usuario, emai, data 
de nascimento, ou imagem) e nome do usuario o qual pertence essa informacao, e o outro recebendo apenas a 
informacao, sendo essa informacao pertencente ao usuario logado no sistema. O sistema so nao tinha acesso a senha 
do usuario pois a mesma era protegida.

Para o usuario atualizar suas informacoes foi usado o metodo - atualizaPerfil - recebendo a informacao que se 
desejar atualizar (nome, email, data de nascimento, imagem ou senha) e o novo valor que essa informacao ia assumir. 
Para mudar a senha, o usuario teria que informar a sua velha senha. Se as informacoes passadas forem validas,
as informacoes sao atualizadas e serao usadas para realizar o login novamente.


##Caso de uso 3: Postar mensagens no mural

Para o usuario postar foi usado o metodo - criaPost - no controle recebendo o post e a data. Entao o post eh 
adiciona no mural (lista de posts) do usuario. Se as informacoes passadas forem validas  o post eh criado 
possuindo uma pontuacao nula, mas ao ser curtido ganha pontos positivos, e ao ser rejeitado perde pontos. 


##Caso de uso 4: Adicionar e remover amigos

Para o usuario adicionar um amigo foi usado o metodo - adicionaAmigo - que recebe o nome do usuario que sera 
notificado com uma solicitacao de amizade caso esse esteja cadastrado no sitema. Entao o usuario que enviou a 
solicitacao recebe uma notificao avisando se o solicitado aceitou ou rejeitou sua solicitacao de amizade. Se o 
solicitado aceitar a solicitacao, a amizade eh criada, e a quantidade de amigos dos dois usuarios aumenta em 1, 
e eles podem curtir os posts um do outro.

Para o usuario remover um amigo foi usado o metodo - removeAmigo - que recebe o nome do amigo a ser removido da
 lista de amigos do usuario.


##Caso de uso 5: Popularidade

Para definir o tipo de popularidade do usuario eh preciso saber a quantidade de pontos que ele possui, pontos 
esses, que podem ser ganhos ou perdidos, de acordo com a quantidade de curtidas e de rejeitoes respectivamente 
de seus posts. Entao, quando um amigo curtir ou rejeitar um post de um usuario, automaticamente a quantidade de
 pontos desse usuario eh atualizada, assim como o seu tipo de popularidade.


##Caso de uso 6: Mudança nos tipos de usuarios

Para realizar a mudanca do tipo de popularidade foi usado o metodo - atualizaPopularidade - que eh chamado nos
 metodos - curtirPost - e - rejeitarPost - esse metodo, verifica a quantidade de pontos, se for menor que 500 o 
 usuario se torna do tipo normal pop, se for entre 500 e 1000 o usuario se torna do tipo tipo celebridade pop, 
 e se for maior ou igual a 100 o usuario se torna do tipo icone pop. 


##Caso de uso 7: Ranking de usuarios e Trending Topics

Para o usuario ver o ranking dos demais usuarios (quais usuarios sao mais populares e quais usuarios sao mais 
populares) do sistema foi usado o metodo - atualizaRanking -.

Para o usuario ver as 3 hashtags mais presentes foio usado o metodo - atualizaTrendTopics - 


##Caso de uso 8: Feed de noticias

Para o feed do usuario foi criada classe - FeedNoticias - para armazenar os posts dos amigos do usuario. O feed
era por padrao ordenado por data, mas ele poderia ser ordenado por popularidade dos posts.


##Caso de uso 9: Exportar e importar posts por arquivos

Para o usuario salvar as informacoes dos seus posts foi usado o metodo - baixaPosts -. Que salva as informacoes 
dos posts como um arquivo de texto.


##Caso de uso 10: Persistencia de arquivos

Sempre que o usuario realizar login e logout suas informacoes sao mantidas.


###################################################################################################################################

O projeto foi dividido em varios package's para melhor encapsulamento visibilidade de cada entidade. 

Em todas as classes foi minimizada a acessibilidade dos seus atributos declarando-os como private, porem foram 
colocados os metodos acessores (get's).

Foi usado os pacotes java.util para usar as colecoes. Principalmente a List, que eh indexavel, e permite ordenacao.

Para validar as informacoes do Usuario foi usada a Valida que lanca excecoes caso as informacoes sejam invalidas.

Para excecoes foi usado heranca, onde UserException e PostException herdao da SystemException e essa de Exception. 

Para o determinar a popularidade do usuario foi usado composicao com interface (Strategy), onde um Usuario compoe um 
TipoPopularidade, que pode ser: NormalPop, CelebridadePop, ou IconePop. Foi escolhido usar interface porque cada
tipo de usuario tem um comportamento (implementacao) especifico, ou seja, eh de um tipo.

Para o feed de noticias, foi usado composicao com inferface, onde um FeedNoticias compoe um  OrdenadorFeed, que pode
ser OrdenaPorData ou OrdenaPorPops. Foi escolhido usar interface pelo mesmo motivo do Usuario, cada odenacao eh de
um tipo.

Para o post foi usado composicao com heranca, onde um Post compoe Midia, que pode ser: Mensagem, Audio e Imagem, e 
composicao simples, onde um Post compoe Hashtag. Foi escolhido usar heranca no caso das midias porque elas nao 
aprensentao comportamentos especificos,sendo assim podem fazer o reuso.

Foi usado Comparable para permitir que os posts fossem comparados e ordenados no FeedNoticias.

*observacao : as sentencas escritas com as iniciais maiusculas e que nao estao no inicio da frase representao as classes.

