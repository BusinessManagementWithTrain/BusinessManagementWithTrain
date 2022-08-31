package model.classes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * Classe creata appositamente per gestire una coda di elementi unici
 * 
 * @author Rinaldi Simone
 */

public class UniqueQueue<T> implements Queue<T> {

	private final Queue<T> queue = new LinkedList<T>();
	private final Set<T> set = new HashSet<T>();
	 
	
	// se il set non conteneva l'elemento, gli viene aggiunto
	//ed in seguito lo aggiungo alla queue
	@Override 
	public boolean add(T t) {
	    if (set.add(t)) {
	    	 queue.add(t);
	    	 return true;
	    }
	    return false;   
	}
	
	//aggiunta di una colloection di elementi al set ed alla queue
	@Override 
	public boolean addAll(Collection<? extends T> arg0) {
	    boolean ret = false;
	    for (T t: arg0)
	        if (set.add(t)) {
	            queue.add(t);
	            ret = true;
	        }
	    return ret;
	}
	
	//rimozione di un elemento
	@Override 
	public T remove() throws NoSuchElementException {
	    T ret = queue.remove();
	    set.remove(ret);
	    return ret;
	}
	
	//rimozione di un oggetto dalle collection
	@Override 
	public boolean remove(Object arg0) {
	    boolean ret = queue.remove(arg0);
	    set.remove(arg0);
	    return ret;
	}
	
	//rimuozione di una collection di elementi
	@Override 
	public boolean removeAll(Collection<?> arg0) {
	    boolean ret = queue.removeAll(arg0);
	    set.removeAll(arg0);
	    return ret;
	}
	
	
	@Override 
	public void clear() {
	    set.clear();
	    queue.clear();
	}
	
	@Override 
	public boolean contains(Object arg0) {
	    return set.contains(arg0);
	}
	
	@Override 
	public boolean containsAll(Collection<?> arg0) {
	    return set.containsAll(arg0);
	}
	
	@Override 
	public boolean isEmpty() {
	    return set.isEmpty();
	}
	
	@Override 
	public Iterator<T> iterator() {
	    return queue.iterator();
	}
	
	@Override 
	public boolean retainAll(Collection<?> arg0) {
	    throw new UnsupportedOperationException();
	}
	
	@Override 
	public int size() {
	    return queue.size();
	}
	
	@Override 
	public Object[] toArray() {
	    return queue.toArray();
	}
	
	
	@Override 
	public T element() {
	    return queue.element();
	}
	
	@Override 
	public boolean offer(T e) {
	    return queue.offer(e);
	}
	
	@Override 
	public T peek() {
	    return queue.peek();
	}
	
	@Override
	public T poll() {
		return queue.poll();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	@Override
	public String toString() {
		return " UniqueQueue " + queue ;
	}
}