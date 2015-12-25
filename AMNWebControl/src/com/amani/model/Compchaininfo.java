package com.amani.model;

/**
 * Compchaininfo entity. @author MyEclipse Persistence Tools
 */

public class Compchaininfo implements java.io.Serializable {

	// Fields

	private CompchaininfoId id;

	// Constructors

	/** default constructor */
	public Compchaininfo() {
	}

	/** full constructor */
	public Compchaininfo(CompchaininfoId id) {
		this.id = id;
	}

	// Property accessors

	public CompchaininfoId getId() {
		return this.id;
	}

	public void setId(CompchaininfoId id) {
		this.id = id;
	}

}