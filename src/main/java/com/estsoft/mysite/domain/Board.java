package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "board" )
public class Board {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Long no;
	
	@Column( nullable = false, length = 200 )
	private String title;
	
	@Lob
	@Column( nullable = false )
	private String content;

	@Column( name = "reg_date", nullable = false )
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date regDate;
	
	@Column( name = "group_no", nullable = false )
	private Long groupNo;

	@Column( name = "order_no", nullable = false )
	private Long orderNo;

	@Column( nullable = false )
	private Integer depth;
	
	@Column( nullable = false )
	private Long hits;

	@ManyToOne
	@JoinColumn( name = "user_no" )
	private User user;
	
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "Board [no=" + no + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", groupNo="
				+ groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", user=" + user + "]";
	}
}