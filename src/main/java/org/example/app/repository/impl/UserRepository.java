package org.example.app.repository.impl;

import org.example.app.config.HibernateConfig;
import org.example.app.entity.User;
import org.example.app.repository.AppRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.example.app.utils.Message;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepository implements AppRepository<User> {

    @Override
    public String create(User user) {
        Transaction transaction = null;
        try (Session session =
                     HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "INSERT INTO User " +
                    "(firstName, lastName, email) " +
                    "VALUES (:firstName, :lastName, :email)";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("email", user.getEmail());
            query.executeUpdate();
            transaction.commit();
            return Message.DATA_INSERT_MSG.getMessage();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Session session =
                     HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction;
            transaction = session.beginTransaction();
            List<User> list =
                    session.createQuery("FROM User", User.class)
                            .list();
            transaction.commit();
            return Optional.of(list);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        if (!isEntityWithSuchIdExists(user)) {
            return Message.DATA_ABSENT_MSG.getMessage();
        } else {
            Transaction transaction = null;
            try (Session session =
                         HibernateConfig.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                String hql = "UPDATE User " +
                        "SET firstName = :firstName, lastName = :lastName, " +
                        "email = :email WHERE id = :id";
                MutationQuery query = session.createMutationQuery(hql);
                query.setParameter("firstName", user.getFirstName());
                query.setParameter("lastName", user.getLastName());
                query.setParameter("email", user.getEmail());
                query.setParameter("id", user.getId());
                query.executeUpdate();
                transaction.commit();
                return Message.DATA_UPDATE_MSG.getMessage();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                return e.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        if (readById(id).isEmpty()) {
            return Message.DATA_ABSENT_MSG.getMessage();
        } else {
            Transaction transaction = null;
            try (Session session =
                         HibernateConfig.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                String hql = "DELETE FROM User WHERE id = :id";
                MutationQuery query = session.createMutationQuery(hql);
                query.setParameter("id", id);
                query.executeUpdate();
                transaction.commit();
                return Message.DATA_DELETE_MSG.getMessage();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                return e.getMessage();
            }
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        Transaction transaction = null;
        Optional<User> optional;
        try (Session session =
                     HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = " FROM User c WHERE c.id = :id";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("id", id);
            optional = query.uniqueResultOptional();
            transaction.commit();
            return optional;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }

    private boolean isEntityWithSuchIdExists(User user) {
        try (Session session =
                     HibernateConfig.getSessionFactory().openSession()) {
            user = session.get(User.class, user.getId());
            if (user != null) {
                Query<User> query =
                        session.createQuery("FROM User", User.class);
                query.setMaxResults(1);
                query.getResultList();
            }
            return user != null;
        }
    }

}
