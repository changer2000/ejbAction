package home.study.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the t_user database table.
 * 
 */
@Entity
@Table(name="t_user")
@NamedQuery(name="TUserEntity.findAll", query="SELECT t FROM TUserEntity t")
public class TUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="seq")
	private int seq;

	@Temporal(TemporalType.DATE)
	@Column(name="birthday")
	private Date birthday;

	@Column(name="login_times")
	private int loginTimes;

	@Column(name="pwd")
	private String pwd;

	@Column(name="user_cod")
	private String userCod;

	public TUserEntity() {
	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserCod() {
		return this.userCod;
	}

	public void setUserCod(String userCod) {
		this.userCod = userCod;
	}

}