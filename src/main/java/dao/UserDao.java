package dao;


import entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;


public class UserDao{

    public User findById(String id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = session.find(User.class,id);
        session.close();
        return user;
    }


    public void insert (User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(user);
        session.flush();
        session.close();
    }

    public void delete(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.flush();
        session.close();
    }

    public void update(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if(session.find(User.class, user.getLogin()) != null) {
            session.merge(user);
        }
        session.flush();
        session.close();
    }

    public List<User> findAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("SELECT E FROM User E WHERE login NOT LIKE 'admin' ORDER BY sumOfCorrectAnswers DESC");
        query.setReadOnly(true);
        List<User> foundQuestions = query.getResultList();
        session.close();
        return foundQuestions;
    }
}
