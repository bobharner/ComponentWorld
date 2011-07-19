/**
 * $Id:$
 */
package org.apache.tapestry.finder.services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.testng.Assert;

/**
 * Abstract class that implements the {@link GenericService} interface, using
 * Apache Cayenne. Services can extend this class to get Cayenne-based
 * implementations of the most commonly-needed database access methods.
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericServiceImpl<T extends CayenneDataObject, ID extends Serializable>
		implements GenericService<T, ID>
{

	// protected final Log log = LogFactory.getLog(getClass().getName());

	private static final long serialVersionUID = 99136895883299725L;

	/**
	 * When this is not null, queries should be cached within the named region.
	 * Subclasses may set the region name.
	 */
	protected String queryCacheRegion;

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl()
	{
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void delete(T entity)
	{
		Assert.assertNotNull(entity, "No Entity Specified");

		ObjectContext context = DataContext.getThreadObjectContext();
		context.deleteObject(entity);
		context.commitChanges();
	}

	/**
	 * Flush the hibernate session to the database.
	 * 
	 * @throws Exception
	 */
	public void flush()
	{
		// session.flush();
	}

	public Class<T> getPersistentClass()
	{
		return persistentClass;
	}

	public String getQueryCacheRegion()
	{
		return queryCacheRegion;
	}

	/**
	 * Performs a commit to the database of an instance of
	 * {@link CayenneDataObject}.
	 * <p>
	 * IMPORTANT NOTE: Cayenne commits all changes in the context, not just the
	 * changes in this one entity. Most of the time this is what you want, but
	 * if not, then you need to ensure that what you want to commit is in a
	 * different context from what you don't.
	 * 
	 * @param entity
	 */
	public T save(T entity)
	{

		ObjectContext context;
		if (entity.getObjectContext() == null)
		{
			System.out.println("In save(), new entity=" + entity + "\n");
			context = DataContext.getThreadObjectContext();
			context.registerNewObject(entity);
		}
		else
		{
			System.out.println("In save(), existing entity=" + entity + "\n");
			context = entity.getObjectContext();
		}
		
		context.commitChanges();
		return entity;
	}

	/**
	 * 
	 */
	public T xsave(T entity)
	{
		Assert.assertNotNull(entity, "No Entity Specified");
		T saved = null; // (T) session.merge(entity);
		flush();
		return saved;
	}

}