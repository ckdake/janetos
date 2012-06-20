/**JavaBrowser.java
  *adapted from ReadServerFile.java from "Deitel & Deitel JHTP3"
  *@author Chris Kelly
  */

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

public class JavaBrowser extends JFrame {
  
  protected JTextField statusField,urlField;
  protected JEditorPane contentPane;
  protected JScrollPane jcCenter;
  public String history[];
  private static final String HOME = "http://www.yahoo.com";
  public JavaBrowser()
    {
      //builds the GUI
      super("JBrowser");

      history = new String[ 50 ];
      history[0]="http://www.yahoo.com";
      history[1]="http://www.go.com";

      
      Container javaBrowserContainer = getContentPane();
      javaBrowserContainer.setLayout(new BorderLayout());
      
      //menu bar
      JMenuBar jcMenuBar = new JMenuBar();
      setJMenuBar( jcMenuBar );

      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic( 'F' );
      JMenuItem aboutItem = new JMenuItem( "About..." );
      aboutItem.setMnemonic( 'A' );
      aboutItem.addActionListener(
				  new ActionListener() {
	public void actionPerformed( ActionEvent e )
	  {
	    JOptionPane.showMessageDialog( JavaBrowser.this,"JavaBrowser version .5beta \n Chris Kelly, Bill Dixon TIP2000","About",JOptionPane.PLAIN_MESSAGE );
	  }
      }
				  );
      fileMenu.add( aboutItem );

      JMenuItem exitItem = new JMenuItem( "Exit" );
      exitItem.setMnemonic( 'x' );
      exitItem.addActionListener(
				 new ActionListener() {
	public void actionPerformed( ActionEvent e )
	  {
	    System.exit( 0 );
	  }
      }
				 );
      fileMenu.add( exitItem );
      jcMenuBar.add( fileMenu );

      JMenu goMenu = new JMenu("Go..");
      goMenu.setMnemonic( 'G' );
      JMenuItem goLocation = new JMenuItem( "Go to Location" );
      goLocation.setMnemonic( 'L' );
      goLocation.addActionListener(new goLocation());
      goMenu.add(goLocation);

      JMenuItem goBack = new JMenuItem( "Go Back" );
      goBack.setMnemonic( 'B' );
      goBack.addActionListener(new backHistory());
      goMenu.add(goBack);

      JMenuItem goForeward = new JMenuItem( "Go Foreward" );
      goForeward.setMnemonic( 'F' );
      goForeward.addActionListener(new foreHistory());
      goMenu.add(goForeward);

      JMenuItem goHome = new JMenuItem( "Go Home" );
      goHome.setMnemonic( 'H' );
      goHome.addActionListener(new goHome());
      goMenu.add(goHome);
      
      jcMenuBar.add(goMenu);
			       
      
      //end menubar


      //north panel
      JPanel jcNorth = new JPanel();
      jcNorth.setLayout(new  GridBagLayout());
      JButton backButton = new JButton("Back");
      JButton foreButton = new JButton("Forward");
      JButton refreshButton = new JButton("Refresh");
      JButton stopButton = new JButton("Stop");
      JButton logoButton = new JButton("JOSB");
      urlField = new JTextField("http://www.",200);

      
      urlField.addActionListener(
				 new ActionListener() {
	public void actionPerformed( ActionEvent e)
	  {
	    getThePage( e.getActionCommand() );
	  }
      }
	);

      refreshButton.addActionListener(
				      new ActionListener(){
	public void actionPerformed( ActionEvent e )
	  {
	    for(int i = 0; i < history.length; i++)
	      if( history[i] == null)
		getThePage( history[i-1] );
	    return;
	  }
      }
	);

      backButton.addActionListener(new backHistory());
      
      foreButton.addActionListener(new foreHistory());


      JLabel addressName = new JLabel("  address : ");

      GridBagConstraints gbConstraints = new GridBagConstraints();
      
      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 1;
      gbConstraints.gridy = 1;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;

      jcNorth.add( backButton, gbConstraints );

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 2;
      gbConstraints.gridy = 1;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;

      jcNorth.add( foreButton, gbConstraints);

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 3;
      gbConstraints.gridy = 1;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;
      jcNorth.add( refreshButton, gbConstraints );

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 4;
      gbConstraints.gridy = 1;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;
      jcNorth.add( stopButton, gbConstraints );

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 6;
      gbConstraints.gridy = 1;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;
      gbConstraints.weightx = 0;
      gbConstraints.weighty = 0;
      jcNorth.add( logoButton, gbConstraints );

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 1;
      gbConstraints.gridy = 2;
      gbConstraints.gridwidth = 1;
      gbConstraints.gridheight = 1;
      gbConstraints.weightx = 10;
      gbConstraints.weighty = 0;
      jcNorth.add( addressName, gbConstraints );

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.gridx = 2;
      gbConstraints.gridy = 2;
      gbConstraints.gridwidth = 5;
      gbConstraints.gridheight = 1;
      gbConstraints.weightx = 100;
      gbConstraints.weighty = 0;
      jcNorth.add( urlField, gbConstraints);
      



      javaBrowserContainer.add(jcNorth,BorderLayout.NORTH);
      //end north panel

 

      //center area
      jcCenter = new JScrollPane(); 
      jcCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      jcCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      contentPane = new JEditorPane();
      contentPane.setEditable(false);
     
      
      contentPane.addHyperlinkListener(
				       new HyperlinkListener(){
	public void hyperlinkUpdate( HyperlinkEvent e )
	  {
	    if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
	      getThePage( e.getURL().toString() );
	  }
      }
	);

      jcCenter.setViewportView(contentPane);
      javaBrowserContainer.add(jcCenter,BorderLayout.CENTER);
      //end center area



      //south area
      statusField = new JTextField();
      statusField.setText("status");
      statusField.setEditable(false);
      javaBrowserContainer.add(statusField,BorderLayout.SOUTH);
      //end south area
	

      //finalizes window
      setVisible(true);
      setSize(500,500);
      setLocation(150,150);
      //end of finalizing
    }
  private void getThePage( String location )
    {
      setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      try {
	//debug fail next line
	contentPane.setPage( location );
	jcCenter.repaint();
	urlField.setText( location );
	addHistory( location );
      }
      catch ( IOException io ) {
	JOptionPane.showMessageDialog( this, "Error retrieving specified URL","Bad URL",JOptionPane.ERROR_MESSAGE );
      }
      setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR) );
    }
  private void addHistory( String location )
    {
      int i;
      for(i = 0; i < history.length; i++)
	if( history[i] == null)
	  history[i] = location;
    }
  private class backHistory implements ActionListener{
    public void actionPerformed( ActionEvent e )
      {
	for(int i = 0; i < history.length; i++)
	  if( history[i] == null)
	    getThePage( history[i-2] );
	return;
      }
  }
  private class foreHistory implements ActionListener{
    public void actionPerformed( ActionEvent e)
      {
	for(int i = 0; i < history.length; i++)
	  if( history[i] == null)
	    //major bug!!!! fix eventually!!!!
	    getThePage( history[i-1] );
	return;
      }
  }
  private class goHome implements ActionListener{
    public void actionPerformed( ActionEvent e )
      {
	getThePage(HOME);
	return;
      }
  }
  private class goLocation implements ActionListener{
    public void actionPerformed( ActionEvent e )
      {
	String goWhere;
	goWhere = JOptionPane.showInputDialog("Enter Location");
	getThePage(goWhere);
	return;
      }
  }




  public static void main(String args[])
    {
      JavaBrowser app = new JavaBrowser();
      
      app.addWindowListener(new WindowCloser());
    }
}
	
