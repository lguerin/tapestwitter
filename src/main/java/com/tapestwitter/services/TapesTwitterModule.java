package com.tapestwitter.services;

import java.io.IOException;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdvice;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.internal.services.ClasspathResourceSymbolProvider;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 * 
 * @author lguerin
 * @author karesti
 */
public class TapesTwitterModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(DataSetLoaderService.class, DemoDataSetLoaderService.class).eagerLoad();
        binder.bind(URLResolver.class, DefaultURLResolver.class);

    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "fr,es,en");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
    }

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * <p>
     * Service builder methods are useful when the implementation is inline as an inner class (as
     * here) or require some other kind of special initialization. In most cases, use the static
     * bind() method instead.
     * <p>
     * If this method was named "build", then the service id would be taken from the service
     * interface and would be "RequestFilter". Since Tapestry already defines a service named
     * "RequestFilter" we use an explicit service id that we can reference inside the contribution
     * method.
     */
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler) throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
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
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security. The @Local annotation selects the desired service by type, but only
     * from the same module. Without @Local, there would be an error due to the other service(s)
     * that implement RequestFilter (defined in other modules).
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration, @Local
    RequestFilter filter)
    {
        configuration.add("Timing", filter);
    }

    public static void contributeSymbolSource(OrderedConfiguration<SymbolProvider> providers)
    {
        providers.add("springSecurity", new ClasspathResourceSymbolProvider("config/security.properties"));
    }

    @SuppressWarnings("unchecked")
    @Match("ClientInfrastructure")
    public static void adviseClientInfrastructure(MethodAdviceReceiver receiver, final AssetSource source) throws SecurityException, NoSuchMethodException
    {

        MethodAdvice advice = new MethodAdvice()
        {
            public void advise(Invocation invocation)
            {
                invocation.proceed();
                List<Asset> jsStack = (List<Asset>) invocation.getResult();
                jsStack.add(source.getClasspathAsset("context:js/tapestwitter.js"));
            }
        };

        receiver.adviseMethod(receiver.getInterface().getMethod("getJavascriptStack"), advice);
    };

}
