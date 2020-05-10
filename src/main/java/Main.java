import dao.GenericDao;
import entity.User;
import org.hibernate.Session;
import util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        User user = new User();
        user.setName("pawel");
        user.setPassword("blaipblaip");
        GenericDao<User> genericDaoPerson = new GenericDao<>(User.class);
        genericDaoPerson.insert(user);
    }
}
