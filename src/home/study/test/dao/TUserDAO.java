package home.study.test.dao;

import javax.ejb.Stateless;

import home.study.common.db.dao.AbstractDAO;
import home.study.entity.TUserEntity;

@Stateless
public class TUserDAO extends AbstractDAO<TUserEntity> {

	private static final long serialVersionUID = 8149644681517347143L;

	public TUserDAO() {
		super(TUserEntity.class);
	}
}
