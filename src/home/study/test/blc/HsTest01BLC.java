package home.study.test.blc;

import javax.ejb.Stateless;
import javax.inject.Inject;

import home.study.common.blc.AbstractBLC;
import home.study.entity.TUserEntity;
import home.study.test.dao.TUserDAO;

@Stateless
public class HsTest01BLC extends AbstractBLC {

	private static final long serialVersionUID = 7953918040736495301L;
	
	@Inject
	private TUserDAO userDao;
	
	public TUserEntity findUser(Integer seq) {
		TUserEntity ent = userDao.find(seq, false);
		
		return ent;
	}
	
}
