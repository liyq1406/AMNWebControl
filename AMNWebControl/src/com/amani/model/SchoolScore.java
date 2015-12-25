package com.amani.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 职位学分信息
 */
@Entity
@Table(name="schoolscore")
/*@AttributeOverrides({
	@AttributeOverride(name="id", column=@Column(name="credit_no")),
	@AttributeOverride(name="uid", column=@Column(name="postion")),
})*/
public class SchoolScore implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//编号
	@EmbeddedId
	private SchoolScorePK pk;
	//学分
	private Integer score;
	
	public SchoolScorePK getPk() {
		return pk;
	}
	public void setPk(SchoolScorePK pk) {
		this.pk = pk;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
