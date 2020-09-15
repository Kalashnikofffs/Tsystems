package ru.kalashnikofffs.eCare.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import ru.kalashnikofffs.eCare.dao.TariffDAO;
import ru.kalashnikofffs.eCare.model.Tariff;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TariffDAOImpl implements TariffDAO {
    private SessionFactory sessionFactory;
    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {this.jmsTemplate = jmsTemplate;}

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(tariff);
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public void edit(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tariff);
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public void delete(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tariff);
        jmsTemplate.convertAndSend("Обнови тарифы");
}

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Tariff.getAllTariffs", Tariff.class);
        return (List<Tariff>) query.getResultList();

    }

    @Override
    public void deleteAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
        jmsTemplate.convertAndSend("Обнови тарифы");
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Tariff.size").getSingleResult();
    }

    @Override
    public Tariff getTariffById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Tariff.class, id);
    }

}
