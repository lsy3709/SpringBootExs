package com.pgm.board.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bno")
	private Long id;
	
	private String title;
	private String writer;
	@Lob
	private String content;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate;
	
	private Long hitcount;
	
	@OneToMany(mappedBy="board",
			fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Reply> reply;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@PrePersist
	public void prePersist() {
		this.hitcount= this.hitcount==null? 0 : this.hitcount;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
