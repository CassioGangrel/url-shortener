package br.com.cassiofiuza.tiny_url;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
public class TinyUrl extends PanacheEntityBase {
    @Id public String id;
    public String url;
    public LocalDateTime expirteAt;
}
