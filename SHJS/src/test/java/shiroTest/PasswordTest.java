package shiroTest;

import org.junit.Test;

import com.sunwave.app.model.SysUser;
import com.sunwave.framework.realm.PasswordHelper;

public class PasswordTest {
	@Test
	public void printPassword(){
		SysUser user = new SysUser();
		user.setUsername("nzjy");
		user.setPassword("123456");
		PasswordHelper p = new PasswordHelper();
		p.encryptPassword(user);
		System.out.println(user.getSalt());
		System.out.println(user.getPassword());
	}
}
