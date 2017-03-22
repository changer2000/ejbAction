package home.study.entity.one2OneBoth;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_idcard database table.
 * 
 */
@Entity
@Table(name="t_idcard")
@NamedQuery(name="TIdcardEntity.findAll", query="SELECT t FROM TIdcardEntity t")
public class TIdcardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String cardno;
	
	/* 参考readme.txt中的说明，非常重要 */
	@OneToOne(mappedBy="idcard", optional=false, cascade=CascadeType.REFRESH)
	private TPersonEntity persion;

	public TIdcardEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public TPersonEntity getPersion() {
		return persion;
	}

	public void setPersion(TPersonEntity persion) {
		this.persion = persion;
	}

}