/* Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.componentworld.services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;

/**
 * Abstract class that implements the {@link GenericDataService} interface, using
 * Apache Cayenne. Services can extend this class to get Cayenne-based
 * implementations of the most commonly-needed database access methods.
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericDataServiceImpl<T extends CayenneDataObject, ID extends Serializable>
		implements GenericDataService<T, ID>
{

	private static final long serialVersionUID = 99136895883299725L;

	/**
	 * When this is not null, queries should be cached within the named region.
	 * Subclasses may set the region name.
	 */
	protected String queryCacheRegion;

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDataServiceImpl()
	{
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void delete(T entity)
	{
		ObjectContext context = DataContext.getThreadObjectContext();
		context.deleteObject(entity);
		context.commitChanges();
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
			context = DataContext.getThreadObjectContext();
			context.registerNewObject(entity);
		}
		else
		{
			context = entity.getObjectContext();
		}
		
		context.commitChanges();
		return entity;
	}

}