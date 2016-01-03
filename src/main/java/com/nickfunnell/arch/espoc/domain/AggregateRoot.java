package com.nickfunnell.arch.espoc.domain;


import com.nickfunnell.arch.espoc.domain.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nick on 19/12/2015.
 */
public abstract class AggregateRoot {


    private List<Event> changes = new ArrayList<Event>();

    public abstract UUID getId();
    public int getVersion(){
        return 0;
    }

    public List<Event> getUncommittedChanges(){
        return changes;
    }

    public void markChangesAsCommitted(){
        changes.clear();
    }

    public void loadsFromHistory(List<Event> history){
        for (Event event : history) {
            applyChange(event,false);
        }
    }

    private void applyChange(Event event, boolean isNew) {
        //Apply the event to the aggregate
        this.apply(event);
        //add the event to the list of changes
        if(isNew) changes.add(event);
    }

    protected void applyChange(Event event){
        applyChange(event,true);
    }

    protected abstract void apply(Event event);
////public abstract class AggregateRoot
//{
//    private readonly List<Event> _changes = new List<Event>();
//
//    public abstract Guid Id { get; }
//    public int Version { get; internal set; }
//
//    public IEnumerable<Event> GetUncommittedChanges()
//    {
//        return _changes;
//    }
//
//    public void MarkChangesAsCommitted()
//    {
//        _changes.Clear();
//    }
//
//    public void LoadsFromHistory(IEnumerable<Event> history)
//    {
//        foreach (var e in history) ApplyChange(e, false);
//    }
//
//    protected void ApplyChange(Event @event)
//    {
//        ApplyChange(@event, true);
//    }
//
//    // push atomic aggregate changes to local history for further processing (EventStore.SaveEvents)
//    private void ApplyChange(Event @event, bool isNew)
//    {
//        this.AsDynamic().Apply(@event);
//        if(isNew) _changes.Add(@event);
//    }
//}
}
