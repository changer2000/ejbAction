package home.study.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_student database table.
 * 
 */
@Entity
@Table(name="t_student")
@NamedQuery(name="TStudentEntity.findAll", query="SELECT t FROM TStudentEntity t")
public class TStudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int studentid;

	@Column(name="student_name")
	private String studentName;

	public TStudentEntity() {
	}

	public int getStudentid() {
		return this.studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}