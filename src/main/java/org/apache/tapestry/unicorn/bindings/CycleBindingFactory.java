package org.apache.tapestry.unicorn.bindings;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.BindingSource;

/**
 * Implementation of the cycle: binding prefix -- we parse a comma-separated list of bindings
 * and generate delegate bindings for each element. The default binding is literal,
 * but other bindings can be used by specifying prefix.
 * <p>
 * Example: "cycle:prop:name,prop:lastName,sth,sth else"
 */
public class CycleBindingFactory implements BindingFactory {
    private final BindingSource _bindingSource;

    public CycleBindingFactory(BindingSource source){
        this._bindingSource = source;
    }
    
    public Binding newBinding(String description, ComponentResources container, ComponentResources component,
            String expression, Location location)
    {
        List<Binding> delegates = new ArrayList<Binding>();
        String[] bindingNames = expression.split(",");

        for (String bindingName : bindingNames){
            String defaultBinding = BindingConstants.LITERAL;

            Binding binding = _bindingSource.newBinding(description, container, component, defaultBinding, bindingName, location);
            delegates.add(binding);
        }
        
        CycleBinding cycleBinding = new CycleBinding(delegates);
        container.addPageLifecycleListener(cycleBinding);
            
        return cycleBinding;
    }
}
