package home.study.entity.manyAndOneBoth;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the t_orders database table.
 * 
 */
@Entity
@Table(name="t_orders")
@NamedQuery(name="TOrdersEntity.findAll", query="SELECT t FROM TOrdersEntity t")
public class TOrdersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int orderid;
	
	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	private Date createdate;
	
	/*
	mappedBy:
	  用于定义类之间的双向关系。如果类之间是单向关系，不需要指定该值；否则，就需要用这个属性进行指定。
	 该属性告诉persistent manager，用于将该关联关系映射到数据表所需的信息  是在  被关联类中定义的。
	该属性值为被关联类中指向自己的成员属性 
	 【这里就是说在TOrderitemEntity.order上定义了与TOrdersEntity的关系】
	
	*/
	@OneToMany(mappedBy="order", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OrderBy("id asc")
	private Set<TOrderitemEntity> orderItems;
	
	public TOrdersEntity() {
	}

	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Set<TOrderitemEntity> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<TOrderitemEntity> orderItems) {
		this.orderItems = orderItems;
	}

}