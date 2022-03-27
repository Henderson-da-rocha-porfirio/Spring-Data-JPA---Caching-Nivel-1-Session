package com.tuyo.produtodata.produtosdata.repository;

import com.tuyo.produtodata.produtosdata.entities.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.*;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {           // É preciso prover genérica informação que este repositório CRUD espera.
                                                                                         // <T, Id> = Tipo: Entidade Produto. E Id: Integer. Lá está como tipo primitivo "int", colocamos aqui com a classe " Integer ".
    List<Produto> findByName(String name);                                              // Fazendo uso de métodos Finder
                                                                                        // 1. nesse caso, ele pega uma string name de Produto e retorna uma lista de produto e executa a ação internamente onde uma query será executada.
                                                                                        // 2. O name será substituído no lugar do nome do produto e retorna resultados que são automaticamente convertidos numa
                                                                                        // lista de produtos em que cada linha do database é convertida num objeto produto e esse objeto é colocado numa lista.
    List<Produto> findByNameAndDesc(String name,String desc);

    List<Produto> findByPriceGreaterThan(Double price);

    List<Produto> findByDescContains(String apple);

    List<Produto> findByPriceBetween(Double price1, Double price2);

    List<Produto> findByDescLike(String desc);

   // List<Produto> findByIdIn(List<Integer> ids);

    List<Produto> findByIdIn(List<Integer> ids, Pageable pageable); // trabalhando com PagingAndSorting o método acima

}
