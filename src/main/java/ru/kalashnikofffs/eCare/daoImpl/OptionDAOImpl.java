package ru.kalashnikofffs.eCare.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import ru.kalashnikofffs.eCare.dao.OptionDAO;
import ru.kalashnikofffs.eCare.model.Option;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OptionDAOImpl implements OptionDAO {

    private SessionFactory sessionFactory;
    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {this.jmsTemplate = jmsTemplate;}

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(option);
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public void edit(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.update(option);
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public void delete(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(option);
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public List<Option> getAllOptions() {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Option.getAllOptions", Option.class).getResultList();
    }

    @Override
    public void deleteAllOptions() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Option.deleteAllOptions").executeUpdate();
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Option.size").getSingleResult();
    }

    @Override
    public Option getOptionById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Option.class, id);
    }

    @Override
    public List<Option> getAllOptionsForTariff(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Option.getAllOptionsForTariff", Option.class);
        query.setParameter("id", id);
        return (List<Option>) query.getResultList();
    }

    @Override
    public void deleteAllOptionsForTariff(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Option.deleteAllOptionsForTariff")
                .setParameter("id", id)
                .executeUpdate();
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public Option findOptionByTitleAndTariffId(String title, Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Option.findOptionByTitleAndTariffId", Option.class)
                .setParameter("title", title)
                .setParameter("id", id)
                .getSingleResult();
    }
}
