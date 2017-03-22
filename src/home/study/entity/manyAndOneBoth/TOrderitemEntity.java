package home.study.entity.manyAndOneBoth;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the t_orderitem database table.
 * 
 */
@Entity
@Table(name="t_orderitem")
@NamedQuery(name="TOrderitemEntity.findAll", query="SELECT t FROM TOrderitemEntity t")
public class TOrderitemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal price;

	@Column(name="product_name")
	private String productName;
	
	/*
	optional:指定关联方是否可以为空，默认为true，如果将其设为false，则要求双方必须存在【即t_oders和t_orderitem表中都要有关联的数据】
	  在执行find()查询TOrderitemEntity实体时，t_oders和t_orderitem之间是进行inner join，当optional=false时，t_oders和t_orderitem之间是进行left join
	  
	@JoinColumn(name="order_id"): 指定t_orderitem表指向t_oders表主键的外键字段名，为order_id
	
	*/
	//@Transient
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	@JoinColumn(name="order_id")
	private TOrdersEntity order;

	public TOrderitemEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public TOrdersEntity getOrder() {
		return order;
	}

	public void setOrder(TOrdersEntity order) {
		this.order = order;
	}

}