package com.tuyo.produtodata.produtosdata;

import com.tuyo.produtodata.produtosdata.entities.*;
import com.tuyo.produtodata.produtosdata.repository.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)            //Usando SpringRunner em vez de JUnit default.
@SpringBootTest
class ProdutosdataApplicationTests {    // Junit testes

    @Autowired
    ProdutoRepository repository;

    @Autowired
    EntityManager entityManager;        // Esse é o equivalente, da Session do Hibernate, do JPA.


    @Test                                  // Construtor marcado com a annotation Test.
    void contextLoads() {                // Esse teste ele vai correr e procurar por uma classe que tiver marcada, no classpath, com a @SprinBootApplication.
    }

    @Test
    void testCreate() {                // Método que insere dados no banco através do teste.
        Produto produto = new Produto();
        produto.setId(1);
        produto.setName("Iphone");
        produto.setDesc("Awesome");
        produto.setPrice(1000d);

        repository.save(produto);
    }

    @Test
    public void testRead() {
        Produto produto = repository.findById(1).get();
        assertNotNull(produto);
        assertEquals("Smartphone", produto.getName());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + produto.getDesc());
    }

    @Test
    public void testUpdate() {
        Produto produto = repository.findById(1).get();
        produto.setPrice(1200d);
        repository.save(produto); // usar o mesmo método usado em testCreate
    }

	/*@Test
	public void testDelete() {
		repository.deleteById(1);
		}*/


    @Test
    public void testDelete() {
        if (repository.existsById(1)) {
            System.out.println("Deletando um produto");
            repository.deleteById(1);
        }
    }

    @Test
    public void testCount() {
        System.out.println("Total de Gravações===============>>>>>>>>>>>>>>>" + repository.count());
    }

    @Test
    public void testFindByName() { // permite testar para vermos os precos dos smartphones cadastrados através do console. Ou seja, achar métodos sem escrever nenhum código sql.
        List<Produto> produtos = repository.findByName("Smartphone");
        produtos.forEach(p -> System.out.println(p.getPrice()));            // realizando iteração (listagem) do produto usando java 8 syntazx
        // usando expressao lambda -> arrow. O "p" representa cada produto na lista de produto.
        // p.getPrice = imprimir o preço do produto.
        List<Produto> produtos1 = repository.findByName("Smartphone");
        produtos1.forEach(p -> System.out.println(p.getPrice()));
    }

    @Test
    public void testFindByNameAndDesc() {
        List<Produto> produtos = repository.findByNameAndDesc("Iphone", "Apple");
        produtos.forEach(p -> System.out.println(p.getPrice()));                        // Exemplo com uso de outra palavra-teste
    }

 /*   @Test
    public void testFindByPriceGreaterThan() { // Verifica por nome.
        List<Produto> produtos = repository.findByPriceGreaterThan(1000d);
        produtos.forEach(p -> System.out.println(p.getName()));
    }*/

    @Test
    public void testFindByPriceGreaterThan() { // Verificar por preço
        List<Produto> produtos = repository.findByPriceGreaterThan(1000d);
        produtos.forEach(p -> System.out.println(p.getPrice()));
    }

    @Test
    public void testFindByDescContains() { // desc = description = descricao e não decrescente.
        List<Produto> produtos = repository.findByDescContains("Apple");
        produtos.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByPriceBetween() {
        List<Produto> produtos = repository.findByPriceBetween(500d, 2500d);
        produtos.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByDescLike() {
        List<Produto> produtos = repository.findByDescLike("%Garmin%");
        produtos.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByIdsIn() {
        // Pageable pageable = new PageRequest(0, 2);
        Pageable pageable = PageRequest.of(0, 2);
        List<Produto> produtos = repository.findByIdIn(Arrays.asList(1, 2, 3), pageable);
        produtos.forEach(p -> System.out.println(p.getName()));
    }

    /*@Test
    public void testFindAllPaging() {                               // Criando Paging
        // FindAll methods só apareceram apra serem utilizado depois de ser implementado em ProdutoRepository: PagingAndSortingRepository
        Pageable pageable = PageRequest.of(0, 1);         // Usando Pageable de Spring Data e não do java.awt. Pageable é uma Interface e por isso não é possível criar uma instância (instanceOf)
        Iterable<Produto> results = repository.findAll(pageable);   // Tem que criar uma PageRequest: Um Objeto PageRequest é uma implementação do pageable do heat control.
        results.forEach(p -> System.out.println(p.getName()));      // Vamos utilizar o PageRequest com dois parâmetros: page e size.
        // page: é a página que eu quero acessar. Por exemplo, nós temos 5 gravações no database:
        // E podemos recuperar somente uma gravação por página. E nesse caso, só podemos acessar as páginas 0 ou 1 ou 2 ou 3.
        // size: é o tamanho de quantas gravações queremos por página.
        // page index: começa com 0 e não com 1. Então, "0", é a primeira página que queremos acesar.
        // size: vamos começar com "1", indicando que apenas uma gravação apareça por página se eu acessar a página "0"(primeira página).
        // Iterable<Produto> ... = estamos assinando um estado comportamental, sequencial, para uma nova local variável com a Interface Iterable.
        // Isso retorna uma página exclusiva de produtos onde poderemos interar ( listar em sequência ).
        // forEach: para usar uma expressão lambda (economia de código numa listagem).
        // forEach: eu pego os resultados passados em cada forEach e os uso na expressao lambda para cada produto.
        // Em Iterable, que funciona igual a uma lista ( para isso ocorrer, eu usei a expressão Lambda que mostra o nome da página.
        // Acessar Page: só mudar em page a que deseja mostrar: 0, 1, 2, 3 ou 4. (equivalente a 5 páginas)
        // Size: Quantidade de páginas: como temos apenas 5 páginas, podemos colocar no máximo 2 em size. Se tivéssemos 6, aí conseguiríamos e assim por diante.
    }

    @Test
    public void testFindAllSorting() {      // Criando Sorting
        // Quando se utiliza o Sorting, temos que pensar em qual tipo queremos utilizar:
        // 1. properties: colunas no database. Deve se usar apenas quando quiser que o construtor pegue um número de propriedades da nossa entidade.
        // ficaria algo do tipo passando a propriedade "name" que está na entidade Produto: new Sort("name").
        //E caso não especifique a ordem da listagem, ela aparecerá como ascendente.
        // 2. orders: direção
        repository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "name"), new Sort.Order(null, "price"))) // Order: é uma Innerclasse de Sort Class (escolher Order() Anonymous Inner Type.
                // Sort está "sorteando" por nome de forma decrescente.
                .forEach(p -> System.out.println(p.getName()));

        // repository.findAll(Sort.by("name", "price")).forEach(p -> System.out.println(p.getName()));                  // Ao utilizarmos aqui o .forEach estamos eliminando esse pedaço de código: Iterable<Produto>findAll = ... porque ele elimina o Iterable.
        // Ele sorteia em ordem alfabética.
        // repository.findAll(new Sort(Direction.DESC, "name", "price")).forEach(p -> System.out.println(p.getName())); // Em ordem decrescente.
    }

    @Test
    public void testFindAllPagingAndSorting() {                                                         //Paging e Sorting sendo usados juntos
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.DESC, "name");
        repository.findAll(pageable).forEach(p -> System.out.println(p.getName()));

    }*/
                                        // Nível 1 - Cache - Session - Armazenando em Cache
    /*@Test                             // Teste nível 1 - session é public por default
    @Transactional                      //@Transactional = Porque o Spring trata o nível de Caching - Session ou o Spring Session está associado com transaction e para funcionar, é necessário anotar. É obrigatório para funcionar o Nível 1 - Session.
    public void testCaching() {         // Select, como mostrado no console, é executado uma vez, embora estejamos lendo o Produto algumas vezes, será lido usando o Select Query do database.
        repository.findById(1);         // O primeiro findById é lido do Database, por uma instrução Select. Nesse ponto armazenará no nível Session do Hibernate que é o nível 1 de Cache.
        repository.findById(1);         // O segundo findById é lido do Cache.
                                        // Se removermos @Transactional, e rodarmos o teste, veremos que o nível 1 - Session do Hibernate não funcionará. Ele estará buscando diretamente do database.
    }*/

    @Test
    @Transactional                                              // Teste nível 1 - usando " evict " ( desalojar = remover do armazenamento de Cache )
    public void testCaching() {                                 // É necessário usar EntityManager. Esse é o equivalente, da Session do Hibernate, do JPA.
        Session session = entityManager.unwrap(Session.class);  // Para acessar a Session do Hibernate. "unwrap"(desempacota). Session é o objeto que queremos "desempacotar"(unwrap) do heap.
        Produto produto = repository.findById(1).get();         //  Corre (RUN) a primeira Query (consulta). * Observar: Variável Local (Objeto existente) = Produto.

        repository.findById(1).get();                           // Aqui ele não rodará (RUN) a Query porque o objeto ainda estará em Cache.

        session.evict(produto);                                 // Desaloja = remove do armazenamento. O objeto "produto" é passado como parâmetro e removerá o produto que está no Nível 1 do Cache.
                                                                // Se o método evict for comentado, o cache volta a funcionar.
        repository.findById(1).get();                           // roda a segunda query ( consulta ) mais uma vez. No console aparecerá as duas "Select" "queries", ou seja, buscou diretamente do database.

    }
}
