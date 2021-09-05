package com.hm.pruebanisum.app.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime created;
	private LocalDateTime modified;
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	private boolean active;
	@Builder.Default
	@JoinColumn(name = "phone_id")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Phone> phones = new ArrayList<>();
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usuario_id"),
			inverseJoinColumns=@JoinColumn(name="role_id"),
			uniqueConstraints= {@UniqueConstraint(columnNames= {"usuario_id","role_id"})})
	private List<Role> roles = new ArrayList<>();

	@PrePersist
	void prePersist() {
		this.created = LocalDateTime.now();
		this.active = true;
	}

	public void addPhones(List<Phone> phones) {
		this.phones.addAll(phones);
	}

}
