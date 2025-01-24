package clients.customer;

import clients.Picture;
import clients.Theme;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class CustomerView implements Observer {
    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels
    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JLabel themeToggle = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtCheck = new JButton(Name.CHECK);
    private final JButton theBtClear = new JButton(Name.CLEAR);
    private final Picture thePicture = new Picture(80, 80);
    private final Container cp;
    private StockReader theStock = null;
    private CustomerController cont = null;
    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factor to deliver order and stock objects
     * @param x   x-cordinate of position of window on screen
     * @param y   y-cordinate of position of window on screen
     */

    public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try                                             //
        {
            theStock = mf.makeStockReader();             // Database Access
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
                Theme.toggleTheme(CustomerView.class);
                CustomerView.this.updateTheme();
            }
        });
        cp.add(themeToggle);                           //  Add to canvas

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Search products");
        cp.add(pageTitle);

        theBtCheck.setBounds(16, 50, 80, 35);    // Check button
        theBtCheck.setBorder(null);
        theBtCheck.setOpaque(true);
        theBtCheck.addActionListener(                   // Call back code
                e -> cont.doCheck(theInput.getText()));
        cp.add(theBtCheck);                           //  Add to canvas

        theBtClear.setBounds(16, 90, 80, 35);    // Clear button
        theBtClear.setBorder(null);
        theBtClear.setOpaque(true);
        theBtClear.addActionListener(                   // Call back code
                e -> cont.doClear());
        cp.add(theBtClear);                           //  Add to canvas

        theAction.setBounds(110, 25, 270, 20);       // Message area
        theAction.setText(" ");                       // blank
        cp.add(theAction);                            //  Add to canvas

        theInput.setBounds(110, 50, 270, 40);         // Product no area
        theInput.setText("Product Number");                           // Input Requirement
        cp.add(theInput);                             //  Add to canvas

        theSP.setBounds(110, 100, 270, 160);          // Scrolling pane
        theOutput.setText("");                        //  Blank
        theOutput.setFont(f);                         //  Uses font
        theOutput.setEditable(false);
        cp.add(theSP);                                //  Add to canvas
        theSP.getViewport().add(theOutput);           //  In TextArea

        thePicture.setBounds(16, 170, 80, 80);   // Picture area
        cp.add(thePicture);                           //  Add to canvas
        thePicture.clear();

        rootWindow.setVisible(true);                  // Make visible);
        theInput.requestFocus();                        // Focus is here
        updateTheme();
    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     *
     * @param c The controller
     */

    public void setController(CustomerController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */

    public void update(Observable modelC, Object arg) {
        updateTheme();
        CustomerModel model = (CustomerModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        ImageIcon image = model.getPicture();  // Image of product
        if (image == null) {
            thePicture.clear();                  // Clear picture
        } else {
            thePicture.set(image);             // Display picture
        }
        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();              // Focus is here
    }

    private void updateTheme() {
        cp.setBackground(Theme.getCurrentTheme(CustomerView.class).getBackground());
        pageTitle.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theBtCheck.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theBtCheck.setBackground(Theme.getCurrentTheme(CustomerView.class).getHighlight());
        theBtClear.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theBtClear.setBackground(Theme.getCurrentTheme(CustomerView.class).getHighlight());
        theAction.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theInput.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theInput.setBackground(Theme.getCurrentTheme(CustomerView.class).getHighlight());
        theOutput.setForeground(Theme.getCurrentTheme(CustomerView.class).getText());
        theOutput.setBackground(Theme.getCurrentTheme(CustomerView.class).getHighlight());
        thePicture.setBackground(Theme.getCurrentTheme(CustomerView.class).getHighlight());
    }

    class Name                              // Names of buttons
    {
        public static final String CHECK = "Check";
        public static final String CLEAR = "Clear";
    }

}
