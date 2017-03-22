package home.study.test.managedbean;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import home.study.entity.TUserEntity;
import home.study.test.blc.HsTest01BLC;

@Named
@ConversationScoped
public class TestMBN implements Serializable {
	
	private static final long serialVersionUID = 4934791370718248721L;

	private static Logger logger = Logger.getLogger(TestMBN.class);

	@Inject
	private Conversation conv;
	
	@Inject
	private HsTest01BLC test01Blc;
	
	public void init() {
		System.out.println(">>> init");
		if(FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (conv.isTransient()) {
			conv.begin();
		}
		
		TUserEntity userEnt = test01Blc.findUser(1);
		if (userEnt!=null)
			System.out.println(">>> user.cod=" +userEnt.getUserCod());
		else
			System.out.println(">>> not found user");
	}
	
	
	public String doTest() {
		
		
		return null;
	}
	
}
