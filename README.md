# Caching ou Cache (memória transitória)
## A. O que é caching?
## Precisamos entender antes de responder a pergunta:
### 1. Conceito de Cache em Computação: é um dispositivo de acesso rápido, interno a um sistema, que serve de intermediário entre um operador de um processo e o dispositivo de armazenamento ao qual esse operador acede (wikipedia)
### 2. Entender como o ORM ( Object Relational Mapping = processo de mapeamento de uma classe java à tabela do database e seus campos ou membros para as colunas das tabelas existentes) lê os dados. Veja este exemplo:
#### a. Client: ele acessa a aplicação
#### b. Aplicacao: e essa usa as ferramentas do ORM igual ao Hibernate para ler os dados. 
#### c. ORM: executa um "select query" internamente executa uma consulta de seleção no banco de dados e obtém os dados. E converte os dados em objetos e os entrega a applicação. E os envia de volta ao Client como solicitado. Isso pode ser em HTML ou poderia ser em algum tipo de outra representação.
### 3. Em todo o tempo o Client ler algum dado da Aplicacao e a Aplicacao ou o ORM executarão um "Select" ou uma grande quantidade de "Selects" a uma Tabela que usam Joins a Joins à múltiplas Tabelas.
### 4. Caching/Cache: É o processo de realizar as mesmas operações de leitura no database inúmeras vezes sem precisar de repetição de instruções SQL.
### 5. O Caching armazena dados ou objetos em um local temporário.
### 6. Isso é o Processo de Caching: Quando uma ( ou muitas) requisição ( request ) é feita pela primeira vez, ela vem ao ORM (hibernate) ou as frameworks (estruturas) do cache lerão os dados, convertendo-o em objeto e o guardando numa memória temporária ou até mesmo num disco.
### 7. A próxima requisição (request) vão às Frameworks ORM ou as Frameworks Caching, onde essas, se assim ocorrer, checarão primeiro se esse dado é para uma particular requisição existente no Cache. Se ele estiver nesse, não haverá consultas de seleção de banco de dados executadas e nenhuma comunicação com o mesmo será feita de igual maneira. Ele simplesmente pegará o objeto e o enviará de volta ao Client, melhorando o desempenho da aplicação.
### 8. E este Cache pode ser atualizado ou será atualizado. E todas às vezes que esse objeto for atualizado, a gravação no database do dado ocorrerá automaticamente. Essas Frameworks(estruturas) Caching ou as do hibernate atualizarão o Cache. E lerá o dado armazenado novamente no Cache que foi atualizado.
### 9. Se esse dado for deletado, também será deletado do Cache.
### 10. O Caching também pode "desalojar" os dados. Para isso, basta chamar métodos "Evict ( despejo )" nas sessões do hibernate.
## B. Os dois níveis de Caching que o Hibernate suporta.
### 1. Nível I (N1)
#### a. Session
#### b. O nível 1 de Cache que é livre e que está sempre habilitado por Default acontece na " Session ".
#### c. O nível 1 acontece quando um Client acessa a Aplicacao e internamente uma Session do Hibernate é usada pelo JPA através do Spring Data.
#### d. A primeira vez, o dado é lido do database e é colocado no Cache e na próxima vez que o Client buscar o mesmo dado, a Session o buscará do Cache em vez de buscar do database.
#### e. E se outro Client acessar a Aplicacao e se uma Session do Hibernate é utilizada, o Cache nunca será o mesmo de outro Client, mas cada Client tem o seu próprio Cache.
#### f. E cada Client acessará o seu Cache correspondente.
#### g. Então, a diferença entre o Cache do nível 1 para o nível 2, é que esse último será buscado através de Sessions com algo em comum, como no exemplo acima, Caches de mais de um Client, porque ele acontece no nível SessionFactory.
### 2. Nível II (N2)
#### a. SessionFactory
#### b. Necessita de passos adicionais para funcionar.
#### c. Como o próprio nome diz " Factory (fábrica) ", ele cria múltiplas sessões do hibernate.
#### d. Essa é a forma que que eles compartilham dados através de sessões do hibernate.
#### e. Se múltiplos usuários estão acessando a aplicação e várias sessões do hibernate, os objetos armazenados ( "cached" ) também compartilharão através das sessões dos usuários.
#### f. Tanto o Session quanto o SessionFactory são objetos do hibernate de baixo nível que são usados internamente por ele.
#### g. JPA: Quando não havia tanto o JPA quanto o Spring Data, estes dois níveis, Session e SessionFactory, eram usados diretamente na Aplicacao.
#### h. A SessionFactory é responsável por criar Sessions do Hibernate de forma diferente, ou seja, os Caches terão algo em comum. E todas as Sessions compartilharão este Cache.
#### i. Quando o Client acessa a Aplicacao, a Session ler e executa um Select na Query. Ler o Objeto e o armazena no Cache e então ele é enviado de volta ao Client.
#### j. E daí outro Client ao acessar a Aplicacao, uma Session diferente é utilizada mas a Session checará primeiro para ver se o dado está no nível 2 de Cache.
#### k. Se o dado estiver no nível 2 como descrito acima, ele então estará nas Queries do Database e estes dados serão enviados de volta ao Client.
#### l. Então, o nível 2 é muito poderoso por causa que os dados aqui são armazenados em Cache através de Sessions.
#### m. Mas ele precisa de configurações adicionais. O Hibernate não dá suporte para essas. Por isso é necessário utilizar Provedores de Caching ( Providers ) como:
##### i. ehcache - este é o mais popular e também o mais utilizado.
##### ii. swaram cache
##### iii. Jboss Tree cache
##### iv. OS cache
##### v. Tangosol Cache
