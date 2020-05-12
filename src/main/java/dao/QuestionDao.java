package dao;

import entity.Question;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;


import java.util.List;

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

    public List<Question> getAllQuestions(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Question> query = session.createQuery("SELECT E FROM Question E");
        query.setReadOnly(true);
        List<Question> foundQuestions = query.getResultList();
        session.close();
        return foundQuestions;
    }
}
