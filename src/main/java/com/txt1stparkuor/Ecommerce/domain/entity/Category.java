package com.txt1stparkuor.Ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Category> children;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public String getFullPath() {
        List<String> path = new ArrayList<>();
        Category current = this;
        
        while (current != null) {
            path.add(current.getName());
            current = current.getParent();
        }
        
        Collections.reverse(path);
        
        return String.join("|", path);
    }
}
