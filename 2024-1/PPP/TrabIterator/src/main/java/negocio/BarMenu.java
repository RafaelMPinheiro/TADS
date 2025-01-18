package negocio;

import java.util.Vector;

class BarMenu {
  private Vector<MenuItem> menuItems;

  public BarMenu() {
    this.menuItems = new Vector<MenuItem>();
    this.menuItems.add(new MenuItem("Cerveja", "Cerveja de Trigo", 5, false));
    this.menuItems.add(new MenuItem("Vinho", "Vinho Suave", 10, false));
  }

  public Vector<MenuItem> getMenuItems() {
    return menuItems;
  }
}
