package interfaces;

import java.util.List;

public interface DAOListener<T> {
	
	public boolean save(T object);
	public boolean update(T object);
	public T findById(Long id);
	public List<T> findAll();
	public void removeAll();
	
}
