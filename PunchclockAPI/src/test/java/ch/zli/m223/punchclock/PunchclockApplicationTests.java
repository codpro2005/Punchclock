package ch.zli.m223.punchclock;

import ch.zli.m223.punchclock.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PunchclockApplicationTests {

	@PersistenceContext
	EntityManager em;

	@Test
	public void contextLoads() {
		this.test();
	}

	public void test() {
		TypedQuery query = em.createQuery("select u from ch.zli.m223.punchclock.domain.User u where u.username =:danilo", User.class);
		List<User> users = query.getResultList();

		for (User user : users) {
			System.out.println(user.getId());
		}
	}

}
