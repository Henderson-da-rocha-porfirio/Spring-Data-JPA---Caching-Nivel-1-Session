package com.tuyo.produtodata.produtosdata;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 			//Usando SpringRunner em vez de JUnit default.
@SpringBootTest
class ProdutosdataApplicationTests { 	// Junit testes

	@Test 								// Construtor marcado com a annotation Test.
	void contextLoads() {				// Esse teste ele vai correr e procurar por uma classe que tiver marcada, no classpath, com a @SprinBootApplication.
	}

}
