package home.study.entity.study;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

@Table(name="t_user", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	//type 1
//	@TableGenerator(
//			name="user_generator", 
//			table="t_id_generator", 	//生成ID的表
//			pkColumnName="table_name", 	//主键列的名字
//			pkColumnValue="t_user",		//主键列的值（定位某条记录）
//			valueColumnName="id_value", //存放生成的ID的列名
//			allocationSize=1)	//缓存主键值数量	
//	@GeneratedValue(strategy=GenerationType.TABLE, generator="user_generator")
	
	//type 2
	//@SequenceGenerator(name="user_seq", sequenceName="seq_t_user")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)
	
	//type 3
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private String content;
	
	
	@Embedded
	@AttributeOverride(name="content1", column=@Column(length=100))
	private MyInfo myInfo;
	
	@Embedded
	@AttributeOverrides(
			{
				@AttributeOverride(name="content21", column=@Column(length=100)),
				@AttributeOverride(name="content22", column=@Column(length=100))
			}
			)
	private MyInfo2 myInfo2;
}
