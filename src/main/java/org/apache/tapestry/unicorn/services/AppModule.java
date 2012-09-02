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
package org.apache.tapestry.unicorn.services;

import java.io.IOException;

import org.apache.tapestry.unicorn.encoders.EntryEncoder;
import org.apache.tapestry.unicorn.encoders.EntryTypeEncoder;
import org.apache.tapestry.unicorn.encoders.LicenseEncoder;
import org.apache.tapestry.unicorn.encoders.SourceTypeEncoder;
import org.apache.tapestry.unicorn.encoders.TapestryVersionEncoder;
import org.apache.tapestry.unicorn.entities.Entry;
import org.apache.tapestry.unicorn.entities.EntryType;
import org.apache.tapestry.unicorn.entities.License;
import org.apache.tapestry.unicorn.entities.SourceType;
import org.apache.tapestry.unicorn.entities.TapestryVersion;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
public class AppModule
{
    public static void bind(ServiceBinder binder)
    {        
        // Make bind() calls on the binder object to define our IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.

    	binder.bind(TapestryVersionService.class, TapestryVersionServiceImpl.class);
    	binder.bind(EntryService.class, EntryServiceImpl.class);
		binder.bind(UserService.class, UserServiceImpl.class);
		binder.bind(RoleService.class, RoleServiceImpl.class);
		binder.bind(EntryTypeService.class, EntryTypeServiceImpl.class);
		binder.bind(SourceTypeService.class, SourceTypeServiceImpl.class);
		binder.bind(LicenseService.class, LicenseServiceImpl.class);
    }
    
    /**
     * Contribute to ApplicationDefaults
     * 
     * @param configuration
     */
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {
        // These contributions override any contributions to FactoryDefaults
        // (with the same key). Here we're restricting the supported locales to
        // just "en" (English). You can extend this list of locales (it's a
    	// comma separated series of locale names; the first locale name is
    	// the default when there's no reasonable match).
        
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

        // The application version number is incorporated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions.
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");
    }
    

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * 
     * <p>
     * Service builder methods are useful when the implementation is inline as an inner class
     * (as here) or require some other kind of special initialization. In most cases,
     * use the static bind() method instead. 
     * 
     * <p>
     * If this method was named "build", then the service id would be taken from the 
     * service interface and would be "RequestFilter".  Since Tapestry already defines
     * a service named "RequestFilter" we use an explicit service id that we can reference
     * inside the contribution method.
     * 
     * @param log
     */    
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
			public boolean service(Request request, Response response,
					RequestHandler handler) throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
					// The responsibility of a filter is to invoke the
					// corresponding method in the handler. When you chain
					// multiple filters together, each filter received a handler
					// that is a bridge to the next filter.
                    
                    return handler.service(request, response);
                }
                finally
                {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

	/**
	 * Contribute to the RequestHandler service configuration. This
	 * is how we extend Tapestry using the timing filter. A common use for this
	 * kind of filter is transaction management or security. The @Local
	 * annotation selects the desired service by type, but only from the same
	 * module. Without @Local, there would be an error due to the other
	 * service(s) that implement RequestFilter (defined in other modules).
	 * 
	 * @param configuration
	 * @param filter
	 */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
            @Local
            RequestFilter filter)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.
        
        configuration.add("Timing", filter);
    }

	/**
	 * Contribute ValueEncoders for our most important entities so that Tapestry
	 * will automatically use them every time a ValueEncoder is needed for items
	 * of these types (e.g. for the Select component and when using
	 * @PageActivationContext)
	 */
    public static void contributeValueEncoderSource(MappedConfiguration<Class, ValueEncoderFactory> configuration)
	{
		configuration.addInstance(Entry.class, EntryEncoder.class);
		configuration.addInstance(EntryType.class, EntryTypeEncoder.class);
		configuration.addInstance(SourceType.class, SourceTypeEncoder.class);
		configuration.addInstance(TapestryVersion.class, TapestryVersionEncoder.class);
		configuration.addInstance(License.class, LicenseEncoder.class);
	}

    /**
     * Add type coercions from Character to String and vice-versa. This is
     * needed by the index page's A..Z links.
     */
    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration)
    {
		configuration.add(new CoercionTuple<Character, String>(Character.class,
				String.class, new Coercion<Character, String>() {
					public String coerce(Character input) {
						return input.toString();
					}
				}));
		configuration.add(new CoercionTuple<String, Character>(String.class,
				Character.class, new Coercion<String, Character>() {
					public Character coerce(String str) {
						return Character.valueOf((str.charAt(0)));
					}
				}));
    }

}
