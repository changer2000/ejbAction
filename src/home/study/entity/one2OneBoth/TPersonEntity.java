package home.study.entity.one2OneBoth;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_person database table.
 * 
 */
@Entity
@Table(name="t_person")
@NamedQuery(name="TPersonEntity.findAll", query="SELECT t FROM TPersonEntity t")
public class TPersonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="person_id")
	private int personId;

	private short age;

	@Temporal(TemporalType.DATE)
	private Date birthdy;


	@Column(name="person_name")
	private String personName;

	private byte sex;
	
	/* 参考readme.txt中的说明，非常重要 */
	@OneToOne(optional=true, cascade=CascadeType.ALL)
	@JoinColumn(name="idcard_id", unique=true)
	private TIdcardEntity idcard;
	
	public TPersonEntity() {
	}

	public int getPersonId() {
		return this.personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public short getAge() {
		return this.age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public Date getBirthdy() {
		return this.birthdy;
	}

	public void setBirthdy(Date birthdy) {
		this.birthdy = birthdy;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public byte getSex() {
		return this.sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public TIdcardEntity getIdcard() {
		return idcard;
	}

	public void setIdcard(TIdcardEntity idcard) {
		this.idcard = idcard;
	}

}