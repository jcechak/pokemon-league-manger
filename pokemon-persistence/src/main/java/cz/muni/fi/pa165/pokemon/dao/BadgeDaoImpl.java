package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.stereotype.Repository;

/**
 * Implements CRUD operations with Badge entity.
 *
 * @author Jaroslav Cechak
 */
@Repository
@Transactional
public class BadgeDaoImpl implements BadgeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Badge badge) {
        try {
            em.persist(badge);
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to create pokemon due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to create pokemon due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to create object, because received object is not an entity.", iae);
        }
    }

    @Override
    public void update(Badge badge) {
        try {
            em.merge(badge);
            em.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to update pokemon due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity or this entity has been already removed.", iae);
        }
    }

    @Override
    public void delete(Badge badge) {
        try {
            em.remove(em.find(Badge.class, badge.getId()));
            em.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to delete pokemon due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to delete pokemon due to error in database layer.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to delete object, because received object is not an entity.", iae);
        }
    }

    @Override
    public Badge findById(Long id) {
        try {
            return em.find(Badge.class, id);
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve pokemon due to database access failure.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve pokemon, because the given key is not a valid primary key.", iae);
        }
    }

    @Override
    public List<Badge> findAll() {
        try {
            return em.createQuery("SELECT b FROM Badge b", Badge.class)
                    .getResultList();
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve pokemons due to database access failure.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve pokemons due to internal error.", iae);
        }
    }

    @Override
    public List<Badge> findAllWithTrainer(Trainer trainer) {
        try {
            return em.createQuery("SELECT b FROM Badge b "
                    + "WHERE b.trainer = :t", Badge.class)
                    .setParameter("t", trainer)
                    .getResultList();
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve pokemons due to database access failure.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve pokemons due to internal error.", iae);
        }
    }

    @Override
    public List<Badge> findAllWithStadium(Stadium stadium) {
        try {
            return em.createQuery("SELECT b FROM Badge b "
                    + "WHERE b.stadium = :s", Badge.class)
                    .setParameter("s", stadium)
                    .getResultList();
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve pokemons due to database access failure.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve pokemons due to internal error.", iae);
        }
    }

    @Override
    public Badge findByTrainerAndStadium(Trainer trainer, Stadium stadium) {
        try {
            return em.createQuery("SELECT b FROM Badge b "
                    + "WHERE b.trainer = :t "
                    + "AND b.stadium = :s", Badge.class)
                    .setParameter("t", trainer)
                    .setParameter("s", stadium)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve pokemons due to database access failure.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve pokemons due to internal error.", iae);
        }
    }

}
