package clients.cashier;

import catalogue.Basket;
import clients.Picture;
import clients.Theme;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model
 */
public class CashierView implements Observer {
    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private static final String CHECK = "Check";
    private static final String BUY = "Add";
    private static final String REMOVE = "Remove";
    private static final String BOUGHT = "Buy";

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JLabel themeToggle = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtCheck = new JButton(CHECK);
    private final JButton theBtBuy = new JButton(BUY);
    private final JButton theBtBought = new JButton(BOUGHT);
    private final Container cp;
    private final Container rootWindow;
    private StockReadWriter theStock = null;
    private OrderProcessing theOrder = null;
    private CashierController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factor to deliver order and stock objects
     * @param x   x-coordinate of position of window on screen
     * @param y   y-coordinate of position of window on screen
     */

    public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Database access
            theOrder = mf.makeOrderProcessing();        // Process order
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        cp = rpc.getContentPane();    // Content Pane
        rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                     // Size of Window
        rootWindow.setLocation(x, y);

        Font f = new Font("Monospaced", Font.PLAIN, 10);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Thank You for Shopping at MiniStore");
        cp.add(pageTitle);

        themeToggle.setBounds(16, 16, 10, 10);   // Picture area
        themeToggle.setIcon(new ImageIcon("images/buttons/theme_toggle.png"));
        themeToggle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Theme.toggleTheme(CashierView.class);
                CashierView.this.update0();
            }
        });
        cp.add(themeToggle);                           //  Add to canvas

        theBtCheck.setBounds(16, 50, 80, 35);    // Check Button
        theBtCheck.setBorder(null);
        theBtCheck.setOpaque(true);
        theBtCheck.addActionListener(                   // Call back code
                e -> cont.doCheck(theInput.getText()));
        cp.add(theBtCheck);                           //  Add to canvas

        theBtBuy.setBounds(16, 90 , 80, 35);      // Buy button
        theBtBuy.setBorder(null);
        theBtBuy.setOpaque(true);
        theBtBuy.addActionListener(                     // Call back code
                e -> cont.doBuy());
        cp.add(theBtBuy);                             //  Add to canvas

        theBtBought.setBounds(16, 220, 80, 35);   // Bought Button
        theBtBought.setBorder(null);
        theBtBought.setOpaque(true);
        theBtBought.addActionListener(                  // Call back code
                e -> cont.doBought());
        cp.add(theBtBought);                          //  Add to canvas

        theAction.setBounds(110, 25, 270, 20);       // Message area
        theAction.setText("");                        // Blank
        cp.add(theAction);                            //  Add to canvas

        theInput.setBounds(110, 50, 270, 35);         // Input Area
        theInput.setText("Product Number");                           // Blank
        cp.add(theInput);                             //  Add to canvas

        theSP.setBounds(110, 100, 270, 160);          // Scrolling pane
        theOutput.setText("");                        //  Blank
        theOutput.setFont(f);                         //  Uses font
        theOutput.setEditable(false);
        cp.add(theSP);                                //  Add to canvas
        theSP.getViewport().add(theOutput);           //  In TextArea
        rootWindow.setVisible(true);                  // Make visible
        theInput.requestFocus();                        // Focus is here
    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     *
     * @param c The controller
     */

    public void setController(CashierController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        update0();
        CashierModel model = (CashierModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        Basket basket = model.getBasket();
        if (basket == null)
            theOutput.setText("Customers order");
        else
            theOutput.setText(basket.getDetails());
        theInput.requestFocus();               // Focus is here
    }

    private void update0 () {
        cp.setBackground(Theme.getCurrentTheme(CashierView.class).getBackground());
        pageTitle.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theBtCheck.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theBtCheck.setBackground(Theme.getCurrentTheme(CashierView.class).getHighlight());
        theBtBuy.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theBtBuy.setBackground(Theme.getCurrentTheme(CashierView.class).getHighlight());
        theBtBought.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theBtBought.setBackground(Theme.getCurrentTheme(CashierView.class).getHighlight());
        theAction.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theInput.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theInput.setBackground(Theme.getCurrentTheme(CashierView.class).getHighlight());
        theOutput.setForeground(Theme.getCurrentTheme(CashierView.class).getText());
        theOutput.setBackground(Theme.getCurrentTheme(CashierView.class).getHighlight());
    }

}
