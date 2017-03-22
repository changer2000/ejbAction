在双向一对一关联中，需要在关系被维护端（inverse side）的@OneToOne注解中定义mappedBy属性，以指定它是这一关联的被维护端。
同时要在关系维护端（owner side）建立外键列指向被维护端的主键列。

1.
public class TPersonEntity implements Serializable {

	@OneToOne(optional=true, cascade=CascadeType.ALL)
	@JoinColumn(name="idcard_id", unique=true)
	private TIdcardEntity idcard;

}	
optional=true,允许idcard_id/idcard为null，也就是允许获取没有身份证的人员对象。
@JoinColumn(name="idcard_id", unique=true)，指定t_person表指向t_idcard表主键的外键字段为idcard_id。
  unique=true表示指定字段的值是唯一的。
  
  

2.
public class TIdcardEntity implements Serializable {

	@OneToOne(mappedBy="idcard", optional=false, cascade=CascadeType.REFRESH)
	private TPersonEntity persion;
	
}
TIdcardEntity是关系的被维护端（由mappedBy="idcard"标注），
optional=false表示persion属性不可为Null
mappedBy="idcard"设置双向关联，告诉persistent manager，用于该关联关系映射到数据表所需的信息是在Person的idcard属性
	