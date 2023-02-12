package com.tuyo.produtodata.produtosdata.entities;

import javax.persistence.*;

@Entity // this is mandatory annotation the next one at table:
@Table // Optional - só é necessária apenas se o nome da tabela no database for diferente do da classe.
public class Produto {
    private static final long serialVersionUID = 1L;

    @Id // this is mandatory = anotação necessária.
    private int id;                      // primary key
    private String name;
    @Column(name = "description")       // Só é necessária se o nome da coluna for diferente. Nesse caso é. POrque está como description.
    private String desc;
    private Double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
