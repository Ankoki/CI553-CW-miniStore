package clients.backDoor;

import clients.Theme;
import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer {
    private static final String RESTOCK = "Add";
    private static final String CLEAR = "Clear";
    private static final String QUERY = "Query";

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JLabel themeToggle = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextField theInputNo = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtClear = new JButton(CLEAR);
    private final JButton theBtRStock = new JButton(RESTOCK);
    private final JButton theBtQuery = new JButton(QUERY);
    private final Container cp;

    private StockReadWriter theStock = null;
    private BackDoorController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factor to deliver order and stock objects
     * @param x   x-cordinate of position of window on screen
     * @param y   y-cordinate of position of window on screen
     */
    public BackDoorView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try                                             //
        {
            theStock = mf.makeStockReadWriter();          // Database access
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                     // Size of Window
        rootWindow.setLocation(x, y);

        themeToggle.setBounds(16, 16, 10, 10);   // Picture area
        themeToggle.setIcon(new ImageIcon("images/buttons/theme_toggle.png"));
        themeToggle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Theme.toggleTheme(BackDoorView.class);
                BackDoorView.this.update0();
            }
        });
        cp.add(themeToggle);

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Staff check and manage stock");
        cp.add(pageTitle);

        theBtQuery.setBounds(16, 50, 80, 35);    // Buy button
        theBtQuery.addActionListener(                   // Call back code
                e -> cont.doQuery(theInput.getText()));
        theBtQuery.setBorder(null);
        theBtQuery.setOpaque(true);
        cp.add(theBtQuery);                           //  Add to canvas

        theBtRStock.setBounds(16, 90, 80, 35);   // Check Button
        theBtRStock.setBorder(null);
        theBtRStock.setOpaque(true);
        theBtRStock.addActionListener(                  // Call back code
                e -> {
                    String input = theInputNo.getText();
                    int quantity;
                    try {
                        quantity = Integer.parseInt(input);
                    } catch (NumberFormatException ex) {
                        theAction.setText("Invalid quantity. Must be greater than 0.");
                        return;
                    }
                    if (quantity > 0)
                        cont.doRStock(theInput.getText(), theInputNo.getText());
                    else
                        theAction.setText("Invalid quantity. Must be greater than 0.");
                });
        cp.add(theBtRStock);                          //  Add to canvas

        theBtClear.setBounds(16, 220, 80, 35);    // Buy button
        theBtClear.setBorder(null);
        theBtClear.setOpaque(true);
        theBtClear.addActionListener(                   // Call back code
                e -> cont.doClear());
        cp.add(theBtClear);                           //  Add to canvas


        theAction.setBounds(110, 25, 270, 20);       // Message area
        theAction.setText("");                        // Blank
        cp.add(theAction);                            //  Add to canvas

        theInput.setBounds(110, 50, 120, 40);         // Input Area
        theInput.setText("Product Number");                           // Blank
        cp.add(theInput);                             //  Add to canvas

        theInputNo.setBounds(260, 50, 120, 40);       // Input Area
        theInputNo.setText("Quantity");                        // The Quantity
        cp.add(theInputNo);                           //  Add to canvas

        theSP.setBounds(110, 100, 270, 160);          // Scrolling pane

        theOutput.setText("");                        //  Blank
        theOutput.setFont(f);                         //  Uses font
        theOutput.setEditable(false);                 //  Make it so the user cannot change the output.

        cp.add(theSP);                                //  Add to canvas
        theSP.getViewport().add(theOutput);           //  In TextArea
        rootWindow.setVisible(true);                  // Make visible
        theInput.requestFocus();                      // Focus is here
        this.update0();
    }

    public void setController(BackDoorController c) {
        cont = c;
    }

    /**
     * Update the view, called by notifyObservers(theAction) in model,
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        this.update0();
        BackDoorModel model = (BackDoorModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();
    }

    private void update0 () {
        cp.setBackground(Theme.getCurrentTheme(BackDoorView.class).getBackground());
        pageTitle.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theBtClear.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theBtClear.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
        theBtRStock.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theBtRStock.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
        theBtQuery.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theBtQuery.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
        theAction.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theInput.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theInput.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
        theInputNo.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theInputNo.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
        theOutput.setForeground(Theme.getCurrentTheme(BackDoorView.class).getText());
        theOutput.setBackground(Theme.getCurrentTheme(BackDoorView.class).getHighlight());
    }


}