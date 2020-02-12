package type.connect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Multimap <T, U> {
	
	Map<T, Set<U>> map;
	
	Multimap(){
		map = new HashMap<>();
	}
	
	void add(T t, U u) {
		if (map.containsKey(t)) {
			map.get(t).add(u);
		}
		else {
			Set<U> set = new HashSet<>();
			set.add(u);
			map.put(t, set);
		}
	}
	void addAll(T t, Set<U> su) {
		if (map.containsKey(t)) {
			map.get(t).addAll(su);
		}
		else {
			Set<U> set = new HashSet<>();
			set.addAll(su);
			map.put(t, set);
		}
	}
	
	Set<Map.Entry<T, Set<U>>> entrySet(){
		return map.entrySet();
	}
	
	boolean isEmpty(){
		return map.isEmpty();
	}
	
	boolean containsKey(T t){
		return map.containsKey(t);
	}
	
	Set<U> getSet(T t) {
		return map.get(t);
	}
	
	int getSetSize(T t){
		if (map.containsKey(t)) {
			return map.get(t).size();
		}
		else {
			return 0;
		}
	}
	
	@Override public String toString() {
		return map.toString();
	}
}
