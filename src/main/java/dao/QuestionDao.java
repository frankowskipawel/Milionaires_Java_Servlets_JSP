package dao;

import entity.Question;
import org.hibernate.Session;
import util.HibernateUtil;

public class QuestionDao {

    public Question findById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Question user = session.find(Question.class,id);
        session.close();
        return user;
    }

    public void insert (Question user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(user);
        session.flush();
        session.close();
    }

    public void delete(Question user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.flush();
        session.close();
    }

    public void update(Question user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if(session.find(Question.class, user.getId()) != null) {
            session.merge(user);
        }
        session.flush();
        session.close();
    }
}
