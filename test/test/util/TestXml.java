package test.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.um.dao.IUMUserDAO;
import com.um.domain.UMUser;
import com.um.util.TransactionNestUtil;

public class TestXml<T> {

    @Resource(name = IUMUserDAO.SERVICE_NAME)
	private IUMUserDAO user_dao;
	
	public static void main(String args[]) {
		String ref = "";
		System.out.println("123");
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext("spring-hibernate.xml");
			IUMUserDAO user_dao = (IUMUserDAO) ac.getBean(IUMUserDAO.SERVICE_NAME);
			System.out.println("user_dao = " + user_dao);
			List<UMUser> users = new ArrayList<UMUser>();
			ref = TransactionNestUtil.reference();
			
			users = (List<UMUser>) user_dao.getBeansByBean(new UMUser(), MatchMode.ANYWHERE);
			System.out.println(users.size());
			
			TransactionNestUtil.releaseRef(ref);
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionNestUtil.releaseRef(ref);
		} finally {
			if (!TransactionNestUtil.isReference()) {
				/*locCodeTypeTableDao.getSessionFactory().getCurrentSession().close();*/
			}
		}
	}
}
