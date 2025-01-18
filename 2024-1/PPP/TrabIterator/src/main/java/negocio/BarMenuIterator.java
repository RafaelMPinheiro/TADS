package negocio;

public class BarMenuIterator implements IteratorDoIgor<MenuItem> {
  private BarMenu barMenu;
  private int pos;

  public BarMenuIterator() {
    this.barMenu = new BarMenu();
    this.pos = 0;
  }

  @Override
  public boolean hasNext() {
    return this.pos < this.barMenu.getMenuItems().size();
  }

  @Override
  public MenuItem next() {
    MenuItem menuItem = this.barMenu.getMenuItems().get(pos);
    pos++;
    return menuItem;
  }

  @Override
  public void add(MenuItem menuItem) {
    this.barMenu.getMenuItems().add(menuItem);
  }

  @Override
  public void remove(int itemIndex) {
    this.barMenu.getMenuItems().remove(itemIndex);
  }
}