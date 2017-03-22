package home.study.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_teacher database table.
 * 
 */
@Entity
@Table(name="t_teacher")
@NamedQuery(name="TTeacherEntity.findAll", query="SELECT t FROM TTeacherEntity t")
public class TTeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int teacherid;

	@Column(name="teacher_name")
	private String teacherName;

	public TTeacherEntity() {
	}

	public int getTeacherid() {
		return this.teacherid;
	}

	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
	}

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}