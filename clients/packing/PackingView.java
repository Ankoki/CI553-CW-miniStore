package clients.packing;

import catalogue.Basket;
import clients.Theme;
import clients.customer.CustomerView;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.
 */

public class PackingView implements Observer {
    private static final String PACKED = "Packed";

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JLabel themeToggle = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtPack = new JButton(PACKED);
    private final Container cp;
    private OrderProcessing theOrder = null;

    private PackingController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factor to deliver order and stock objects
     * @param x   x-cordinate of position of window on screen
     * @param y   y-cordinate of position of window on screen
     */
    public PackingView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try                                           //
        {
            theOrder = mf.makeOrderProcessing();        // Process order
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
                Theme.toggleTheme(PackingView.class);
                PackingView.this.update0();
            }
        });
        cp.add(themeToggle);                           //  Add to canvas

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Packing Bought Order");
        cp.add(pageTitle);

        theBtPack.setBounds(16, 50, 80, 35);   // Check Button
        theBtPack.setBorder(null);
        theBtPack.setOpaque(true);
        theBtPack.addActionListener(                   // Call back code
                e -> cont.doPacked());
        cp.add(theBtPack);                          //  Add to canvas

        theAction.setBounds(110, 25, 270, 20);       // Message area
        theAction.setText("");                        // Blank
        cp.add(theAction);                            //  Add to canvas

        theSP.setBounds(110, 55, 270, 205);           // Scrolling pane
        theOutput.setText("");                        //  Blank
        theOutput.setFont(f);                         //  Uses font
        theOutput.setEditable(false);
        cp.add(theSP);                                //  Add to canvas
        theSP.getViewport().add(theOutput);           //  In TextArea
        this.update0();
        rootWindow.setVisible(true);                  // Make visible
    }

    public void setController(PackingController c) {
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
        PackingModel model = (PackingModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        Basket basket = model.getBasket();
        if (basket != null) {
            theOutput.setText(basket.getDetails());
        } else {
            theOutput.setText("");
        }
    }

    private void update0 () {
        cp.setBackground(Theme.getCurrentTheme(PackingView.class).getBackground());
        pageTitle.setForeground(Theme.getCurrentTheme(PackingView.class).getText());
        theBtPack.setForeground(Theme.getCurrentTheme(PackingView.class).getText());
        theBtPack.setBackground(Theme.getCurrentTheme(PackingView.class).getHighlight());
        theAction.setForeground(Theme.getCurrentTheme(PackingView.class).getText());
        theOutput.setForeground(Theme.getCurrentTheme(PackingView.class).getText());
        theOutput.setBackground(Theme.getCurrentTheme(PackingView.class).getHighlight());
    }

}

