package by.resliv.citybot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity (name = "cities")
public class City {

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "recommendations", length = 5000)
    private String recommendations;
}
