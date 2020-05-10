package dao;


import entity.Question;
import entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;


public class GenericDao<T>{
    private final Class<T> classParameter;

    public GenericDao(Class<T> classParameter) {
        this.classParameter = classParameter;
    }

    public T findById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        T objectDAO = session.find(classParameter,id);
        session.close();
        return objectDAO;
    }

    public T findById(String id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        T objectDAO = session.find(classParameter,id);
        session.close();
        return objectDAO;
    }


    public void insert (T objectDAO){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(objectDAO);
        session.flush();
        session.close();
    }



    public List<Question> findByMark(String mark){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Question> query = session.createQuery("select o from Car o where o.mark=:mark", Question.class)
                .setParameter("mark", mark);
        List<Question> foundQuestions = query.getResultList();
        session.close();
        return foundQuestions;
    }

    public User findByEmail(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("select o from User o where o.email=:email", User.class)
                .setParameter("email", email);
        List<User> foundUsers = query.getResultList();
        User user =null;
        if (foundUsers.size()>0){
            user = foundUsers.get(0);}
        System.out.println("------"+ user);
        session.close();
        return user;
    }

    public List<Question> findAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Question> query = session.createQuery("SELECT E FROM Car E");


        query.setReadOnly(true);
        List<Question> foundQuestions = query.getResultList();
        session.close();
        return foundQuestions;
    }

}
