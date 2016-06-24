public interface TodoItemDao {

    public TodoItem retreieveAll();

    public boolean delete(int id);

    public TodoItem save(TodoItem item);
}
