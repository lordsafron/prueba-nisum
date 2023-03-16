package com.hm.pruebanisum.app.models.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name = "usuarios")
public class Usuario extends AuditEntity {
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	private String name;
	private String email;
	private String password;
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

	public void addPhones(List<Phone> phones) {
		this.phones.addAll(phones);
	}

}
