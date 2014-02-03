package cz.muni.fi.anatomytutor.backend.dao;

/**
 * DAO super-interface Extending interfaces will inherit basic CRUD operations
 * on their entities.
 *
 * @author Jan Kucera
 *
 * @param <T> Generic type of entity
 */
public interface GenericDao<T> {

    /**
     * Create the entity
     *
     * @throws IllegalArgumentException if parameter is null or invalid
     */
    T create(T entity);

    /**
     * Return the entity
     *
     * @throws IllegalArgumentException if parameter is null or invalid
     */
    T get(Long id);

    /**
     * Update the entity
     *
     * @throws IllegalArgumentException if parameter is null, invalid or
     * non-existent in the DB
     */
    T update(T entity);

    /**
     * Remove the entity
     *
     * @throws IllegalArgumentException if parameter is null or invalid. Does
     * not throw this exception if parameter is valid but given entity is
     * nonexistent.
     */
    void remove(Long id);
}
