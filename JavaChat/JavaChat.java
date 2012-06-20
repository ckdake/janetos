/* @author Chris Kelly
 */

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JavaChat extends JFrame {

  protected static String userName,userIP;
  JTextField uName, messageInput;
  Container container;
  GridBagLayout gbLMain;
  GridBagConstraints gbCMain;
  JTextArea conversationArea, ctName;
  boolean connected = false;

  //things for connectivity
  String message = "";
  String outputString;
  Socket client;
  ObjectOutputStream output;
  ObjectInputStream input;  
  //end for things
  
  public JavaChat() 
    {
      super("JavaChat");
      //primary gui

      container = getContentPane();
      gbLMain = new GridBagLayout();
      gbCMain = new GridBagConstraints();
      container.setLayout(gbLMain);
      JLabel userNameName = new JLabel("currently on as: ");
      JLabel connectedToName = new JLabel("connected to: ");
      messageInput = new JTextField(100);
      conversationArea = new JTextArea(10,60);
      JScrollPane conversationAreaScroll = new JScrollPane();
      conversationAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      conversationAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      conversationAreaScroll.setViewportView(conversationArea);
      uName = new JTextField(userName + "@" + userIP,100);
      uName.setEditable(false);
      
      JScrollPane ctNameScroll = new JScrollPane();
      ctName = new JTextArea();
      ctNameScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      ctNameScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      ctNameScroll.setViewportView(ctName);

      ctName.setEditable(false);
      JButton connectionButton = new JButton("Connections....");
      connectionButton.addActionListener(
					 new ActionListener() {
	public void actionPerformed( ActionEvent e )
	  {
	    new Connections();
	  }
      }
					 );
      JButton sendButton = new JButton("Send");
      sendButton.addActionListener( new SendButtonListener());
      JButton clearButton = new JButton("Clear");
      clearButton.addActionListener(
				   new ActionListener() {
	public void actionPerformed( ActionEvent e)
	  {
	    messageInput.setText("");
	  }
      }
				   );
      JButton logOutButton = new JButton("Logout");
      logOutButton.addActionListener(new LogoutListener());

      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(userNameName,1,1,1,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(connectedToName,1,5,1,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(messageInput,1,2,5,1,1,0);
      gbCMain.fill = GridBagConstraints.BOTH;
      addGB(conversationAreaScroll,1,4,5,1,1,1);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(uName,2,1,4,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(ctName,2,5,4,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(connectionButton,1,3,1,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(sendButton,2,3,1,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(clearButton,4,3,1,1,1,0);
      gbCMain.fill = GridBagConstraints.HORIZONTAL;
      addGB(logOutButton,5,3,1,1,1,0);
      //make entire GUI and listeneres 
      
      setVisible(true);
      setLocation(150,150);
      setSize(400,400);

    }
  public static void main(String args[])
    {
      Login loginApplication = new Login();
    }
  class LogoutListener implements ActionListener {
    public void actionPerformed(ActionEvent ae)
      {
	System.exit(0);
      }
  }
  class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ae)
      {
	if ( connected == false )
	  {
	    JOptionPane.showMessageDialog(null,"No Current Connections!");
	    return;
	  }
	//trys to send stuff
       
	outputString = messageInput.getText();
	output.writeObject(outputString);
	output.flush();
	try
	  {
	    try
	      {
		message = (String)(input.readObject());
	      }
	    catch (IOException IOE)
	      {
		JOptionPane.showMessageDialog(null, "IOException");
		return;
	      }
	  }
	catch ( OptionalDataException ODE)
	  {
	    JOptionPane.showMessageDialog(null, "OptionalDataException");
	    return;
	  }
	      
	  
	//ends of try to send stuff

	JOptionPane.showMessageDialog(null,"You pushed the send button");
      }
  }
  private class Connections extends JFrame{
    JTextField remb;
    String ipList[];
    JTextArea ipL;
    JTextField ipadd;
    int ipcounter=0;
    public Connections()
      {
	ipList = new String[10];
	for(int i=0;i<10;i++)
	  {
	    ipList[i] = new String("");
	  }
	JPanel top = new JPanel();
	JPanel middle = new JPanel(new FlowLayout());
	JPanel bottom = new JPanel();
	Container c = getContentPane();
	c.setLayout(new BorderLayout());
	JButton add = new JButton("Add");
	JButton remove = new JButton("Remove");
	JButton ok = new JButton("Ok");
	ipL = new JTextArea(10,15);    
	ipL.setEditable(false);
	remb = new JTextField(2);
	ipadd = new JTextField(20);
	JLabel reml = new JLabel("<<Enter number to Remove");
	middle.add(ok);
	middle.add(add);
	middle.add(remove);
	middle.add(remb);
	middle.add(reml);
	top.add(ipL);
	bottom.add(ipadd);
	c.add(top,BorderLayout.NORTH);
	c.add(middle,BorderLayout.CENTER);
	c.add(bottom,BorderLayout.SOUTH);
	ok.addActionListener(new OkConnectionListener());
	add.addActionListener(new AddConnectionListener());
	remove.addActionListener(new RemoveConnectionListener());
	setLocation(275,250);
	setSize(250,315);
	setVisible(true);
      }
    class OkConnectionListener implements ActionListener
    {
      public void actionPerformed(ActionEvent e)
	{
	  setVisible(false);
	}
      
    }
    class AddConnectionListener implements ActionListener
    {    
      public void actionPerformed(ActionEvent e)
	{
	  if ( ipcounter == 10)
	    {
	      JOptionPane.showMessageDialog(null,"Only 10 connections supported!");
	      return;
	    }

	  //trys connection
	  outputString = messageInput.getText();
	  int soccount = 0;
	  try
	    {
	      try
		{
		  client = new Socket(InetAddress.getByName(ipList[0]),5687);
		}
	      catch ( IOException IOE )
		{
		  JOptionPane.showMessageDialog(null, "IOException");
		  return;
		}
	    }
	  catch (UnknownHostException uhe)
	    {
	      JOptionPane.showMessageDialog(null,"Invalid network address");
	      return;
	    }
	      
	  try
	    {
	      output = new ObjectOutputStream(client.getOutputStream());
	      try
		{
		  input = new ObjectInputStream(client.getInputStream());
		}
	      catch ( StreamCorruptedException SCE )
		{
		  JOptionPane.showMessageDialog(null, "Stream Corrupted Exception");
		  return;
		}
	    }
	  catch ( IOException IOE )
	    {
	      JOptionPane.showMessageDialog(null, "IOException");
	      return;
	    }

	  //end of trying to connect

	  ipList[ipcounter] = ipadd.getText();
	  String show = new String();
	  show = "";
	  for (int i=0;i<10;i++)
	    {
	      show = show + i + ". " + ipList[i] + "\n";
	    }
	  ipL.setText(show);
	  ipcounter++;
	  connected = true;
	  ipadd.setText("");
	}
    }
    class RemoveConnectionListener implements ActionListener
    {
      public void actionPerformed(ActionEvent e)
	{
	  if ( ipcounter == 0)
	    {
	      JOptionPane.showMessageDialog(null,"No current connections!");
	      return;
	    }
	  ipList[Integer.parseInt(remb.getText())] = "";
	  String[] temp = new String[10];
	  for (int i=0;i<10;i++)
	    {
	      temp[i] = new String();
	      temp[i] = ipList[i];
	    }
	  for (int i=0;i<9;i++)
	    {
	      if (temp[i].equals(""))
		{
		  for(int t=i;t<9;t++)
		    {
		      temp[t]=temp[t+1];
		    }
		}
	    }
	  for (int i=0;i<10;i++)
	    {
	      ipList[i] = temp[i];
	    }
	  String show = new String();
	  show = "";
	  for (int i=0;i<9;i++)
	    {
	      show = show + i + ". " + ipList[i] + "\n";
	    }
	  ipL.setText(show);
	  System.out.println(ipList[ipcounter]);


	  //trys to close connection
	  try
	    {
	      input.close();
	      output.close();
	    }
	  catch ( IOException IOE )
	    {
	      JOptionPane.showMessageDialog(null, "IO Exception");
	      return;
	    }
	  //end of try to close connection


	  ipcounter--;
	  if ( ipcounter == 0)
	    connected = false;
	  ipadd.setText("");
	}
    }    
  }
  private void addGB(Component com,int gridx, int gridy, int gridw, int gridh, int weightx,int weighty)
    {
      gbCMain.gridx = gridx;
      gbCMain.gridy = gridy;
      gbCMain.gridwidth = gridw;
      gbCMain.gridheight = gridh;
      gbCMain.weightx = weightx;
      gbCMain.weighty = weighty;
      gbLMain.setConstraints( com, gbCMain );
      container.add( com );
    }
  private static class Login extends JFrame{
    Container c;
    GridBagLayout gbLayout;
    GridBagConstraints gbConstraints;
    JTextField enterName,enterIP;

    public Login()
      {
	super("JChat - login");
	JLabel userNameLabel,computerIP;
	JButton loginButton;
	
	userNameLabel = new JLabel("username:");
	computerIP = new JLabel("computer / IP:");

	enterName = new JTextField(20);
	enterIP = new JTextField(20);

	loginButton = new JButton("Login");
	loginButton.addActionListener(new LoginLoaderListener());

	c = getContentPane();
	gbLayout = new GridBagLayout();
	c.setLayout( gbLayout );
	gbConstraints = new GridBagConstraints();
	
	addGB(userNameLabel,1,1,3,1,0,0);
	addGB(enterName,2,2,3,1,0,0);
	addGB(computerIP,1,3,3,1,0,0);
	addGB(enterIP,2,4,3,1,0,0);
	addGB(loginButton,2,5,2,1,0,0);

	setSize(250,150);
	setLocation(150,150);
	setVisible(true);
      }
    private void addGB(Component com,int gridx, int gridy, int gridw, int gridh, int weightx,int weighty)
      {
	gbConstraints.gridx = gridx;
	gbConstraints.gridy = gridy;
	gbConstraints.gridwidth = gridw;
	gbConstraints.gridheight = gridh;
	gbConstraints.weightx = weightx;
	gbConstraints.weighty = weighty;
	gbLayout.setConstraints( com, gbConstraints );
	c.add( com );
      }
    class LoginLoaderListener implements ActionListener{
      public void actionPerformed( ActionEvent ae)
	{
	  if(enterName.getText().equals("") || enterIP.getText().equals(""))
	    {
	      JOptionPane.showMessageDialog(null,"Invalid name or computer");
	      return;
	    }
	  userName = enterName.getText();
	  userIP = enterIP.getText();
	  setVisible(false);
	  JavaChat chatApplication = new JavaChat();
	  chatApplication.addWindowListener(new WindowCloser());
	}
    }
  }
}
  











