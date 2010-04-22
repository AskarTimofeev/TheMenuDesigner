package ru.lavila.menudesigner.models.events;

import ru.lavila.menudesigner.models.Category;
import ru.lavila.menudesigner.models.Element;

import java.util.Collection;
import java.util.Map;

public interface HierarchyListener
{
    //todo: redesign interface the same way as element listener
    void elementsAdded(Category parent, Element... elements);
    void elementsRemoved(Map<Category, Collection<Element>> elementsMap);
}
