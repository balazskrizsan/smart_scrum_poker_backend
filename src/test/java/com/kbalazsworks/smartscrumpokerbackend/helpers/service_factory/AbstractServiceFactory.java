package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractServiceFactory
{
    Map<String, Map<String, Object>> oneTimeMocks = new HashMap<>();

    protected  <T> T getDependency(Class<?> newClass, Class<T> dependencyClass, T mock, T diObject)
    {
        var newClassFound = oneTimeMocks.getOrDefault(newClass.getName(), null);
        if (newClassFound != null && !newClassFound.isEmpty())
        {
            var dependencyClassFound = (T) newClassFound.getOrDefault(dependencyClass.getName(), null);
            if (dependencyClassFound != null)
            {
                newClassFound.remove(dependencyClass.getName());

                return dependencyClassFound;
            }
        }

        return Optional.ofNullable(mock).orElse(diObject);
    }
}
