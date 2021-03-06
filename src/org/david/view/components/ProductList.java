package org.david.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import org.david.controller.RepositoryManager;
import org.david.model.impl.SalableProduct;
import org.david.view.miscs.Utils;

public class ProductList extends JList<Integer> {

  private static final long serialVersionUID = -4973167272491005453L;

  public ProductList(RepositoryManager repositoryManager, boolean load) {
    super();
    setOpaque(false);
    setModel(new DefaultListModel<Integer>());
    setCellRenderer(new ListCellRenderer<Integer>() {
      @Override
      public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
          boolean isSelected, boolean cellHasFocus) {

        if (value == null)
          return null;

        String name = repositoryManager.getProductRepository().get(value, SalableProduct::getName);
        double price = repositoryManager.getProductRepository().get(value, SalableProduct::getPrice);

        JLabel label = new JLabel();
        label.setBorder(new EmptyBorder(0, 5, 0, 0));
        label.setOpaque(isSelected);
        label.setPreferredSize(new Dimension(100, 45));
        label.setFont(new FontUIResource("Segoe UI", Font.TRUETYPE_FONT, 12));
        label.setBackground(Color.MAGENTA.darker());
        label.setForeground(isSelected ? Color.WHITE : Color.BLACK);

        StringBuffer buffer = new StringBuffer();
        buffer.append("<html>");
        buffer.append("<b>" + value + "</b> ").append("<u>" + name + "</u>  <br><i>")
            .append(Utils.toBRL(price) + "</i>");
        buffer.append("</html>");

        label.setText(buffer.toString().replace(".", ","));

        return label;
      }
    });
    if (load) {
      loadAll(repositoryManager);
    }
  }

  public void loadAll(RepositoryManager repositoryManager) {
    ((DefaultListModel<Integer>) getModel()).removeAllElements();
    repositoryManager.getProductRepository().stream().forEach(product -> {
      ((DefaultListModel<Integer>) getModel()).addElement(product.getUniqueId());
    });
    repaint();
  }

}
