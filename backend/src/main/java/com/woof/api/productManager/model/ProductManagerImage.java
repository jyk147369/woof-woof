package com.woof.api.productManager.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProductManagerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String filename;

    @ManyToOne
    @JoinColumn(name = "ProductManager_idx")
    private ProductManager productManager;

}
