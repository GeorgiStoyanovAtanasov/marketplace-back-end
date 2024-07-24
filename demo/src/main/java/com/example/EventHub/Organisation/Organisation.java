package com.example.EventHub.Organisation;





@Entity
@Table(name = "organisations")
@Data
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String organisation_name;



}
