/** Java Calculator
  * internal name : JanetCalc
  *@author (s) Chriis Kelly & Bill Dixon
  */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class JavaCalc extends JFrame {

  private ButtonGroup radioGroup;
  private JRadioButton radioAdd,radioSub,radioMul,radioDiv;
  private JButton calcButton;
  private String what;
  private JLabel whatLabel;
  JTextField outNum,outDen,inNum1,inDen1,inNum2,inDen2;
  private DrawPanel dp;

  public JavaCalc()
    {
      //builds the entire GUI for JanetCalc
      
      //the Main part of the GUI
      Container c = getContentPane();
      JPanel javaCalcContainer = new JPanel();
      javaCalcContainer.setLayout( new BorderLayout(10,10));

      //background
      Image im;
      im = Toolkit.getDefaultToolkit().getImage("background.jpg");
      MediaTracker mt = new MediaTracker (this);
      mt.addImage(im,0);
      dp = new DrawPanel(im);
      try{mt.waitForAll();}
      catch(InterruptedException ie){}      
      dp.add(javaCalcContainer);
      c.add(dp);
      //end background

      //north panel containing two input layout boxes
      JPanel jcNorth = new JPanel();
      jcNorth.setLayout( new BorderLayout(10,10));
      inNum1 = new JTextField(9);
      inDen1 = new JTextField(9);
      inNum2 = new JTextField(9);
      inDen2 = new JTextField(9);
      JLabel line1 = new JLabel("--------------",SwingConstants.CENTER);
      JLabel line2 = new JLabel("--------------",SwingConstants.CENTER);
      whatLabel = new JLabel("[ + ]",SwingConstants.CENTER);
      what = "addition";
      JPanel jcNorthWest = new JPanel(new BorderLayout());
      JPanel jcNorthCenter = new JPanel(new BorderLayout());
      JPanel jcNorthEast = new JPanel(new BorderLayout());
        //setup NorthWest
      jcNorthWest.add(inNum1,BorderLayout.NORTH);
      jcNorthWest.add(line1,BorderLayout.CENTER);
      jcNorthWest.add(inDen1,BorderLayout.SOUTH);
        //setup NorthCenter
      jcNorthCenter.add(whatLabel,BorderLayout.CENTER);
        //setup NorthEast
      jcNorthEast.add(inNum2,BorderLayout.NORTH);
      jcNorthEast.add(line2,BorderLayout.CENTER);
      jcNorthEast.add(inDen2,BorderLayout.SOUTH);
        //setup North
      jcNorth.add(jcNorthWest,BorderLayout.WEST);
      jcNorth.add(jcNorthCenter,BorderLayout.CENTER);
      jcNorth.add(jcNorthEast,BorderLayout.EAST);
      //end north panel


      //west panel containing radiobuttons
      JPanel jcWest = new JPanel();
      jcWest.setLayout( new GridLayout(4,1));
          //makes the 4radio buttons and adds them to the buttongroup  panel
      radioGroup = new ButtonGroup();
      radioAdd = new JRadioButton("Rational Add                ",true);
      radioGroup.add(radioAdd);
      jcWest.add(radioAdd);
      radioSub = new JRadioButton("Rational Subtract           ");
      radioGroup.add(radioSub);
      jcWest.add(radioSub);
      radioMul = new JRadioButton("Rational Multiply           ");
      radioGroup.add(radioMul);
      jcWest.add(radioMul);
      radioDiv = new JRadioButton("Rational Divide             ");
      radioGroup.add(radioDiv);
      jcWest.add(radioDiv);
      //end west panel


      //east panel containing the calculate button
      JPanel jcEast = new JPanel();
      jcEast.setLayout( new GridLayout(1,1));
          //makes the button and adds it to the panel
      calcButton = new JButton("calc");
      jcEast.add(calcButton);
      //end east panel


      //south panel containing the answer and the about box
      JPanel jcSouth = new JPanel();
      JLabel aboutLabel = new JLabel("JavaCalc By: CK and BD 2000");
      JLabel answerWest = new JLabel("Answer ->");
      JLabel answerEast = new JLabel("<- Answer"); 
      outNum = new JTextField("",SwingConstants.CENTER);
      outNum.setEditable(false);
      outDen = new JTextField("",SwingConstants.CENTER);
      outDen.setEditable(false);
      JLabel answerLine= new JLabel("-----------------",SwingConstants.CENTER);
      JPanel jcSouthWest = new JPanel();
      JPanel jcSouthEast = new JPanel();
      JPanel jcSouthCenter = new JPanel();
      JPanel jcSouthSouth = new JPanel();
         //makes the south center one panel
      jcSouthCenter.setLayout(new BorderLayout(10,1));
      jcSouthCenter.add(outNum,BorderLayout.NORTH);
      jcSouthCenter.add(answerLine,BorderLayout.CENTER);
      jcSouthCenter.add(outDen,BorderLayout.SOUTH);
         //adds all other south panels
      jcSouthWest.add(answerWest);
      jcSouthEast.add(answerEast);
      jcSouthSouth.add(aboutLabel);
         //adds all to jcSouth
      jcSouth.setLayout(new BorderLayout(10,10));
      jcSouth.add(jcSouthWest,BorderLayout.WEST);
      jcSouth.add(jcSouthCenter,BorderLayout.CENTER);
      jcSouth.add(jcSouthEast,BorderLayout.EAST);
      jcSouth.add(jcSouthSouth,BorderLayout.SOUTH);
      //end jcSouth
      
      //adds everything to the layout
      javaCalcContainer.add(jcNorth, BorderLayout.NORTH);
      javaCalcContainer.add(jcWest, BorderLayout.WEST);
      javaCalcContainer.add(jcEast, BorderLayout.EAST);
      javaCalcContainer.add(jcSouth, BorderLayout.SOUTH);
      //end of adding stuff

      //adds and implements the Item and Action listeners and handelers
      ButtonHandler buttonHandler = new ButtonHandler();
      calcButton.addActionListener( buttonHandler );

      RadioHandler radioHandler = new RadioHandler();
      radioAdd.addItemListener( radioHandler );
      radioSub.addItemListener( radioHandler );
      radioMul.addItemListener( radioHandler );
      radioDiv.addItemListener( radioHandler );

      //end of adding thingies

      //set up invisibility
      javaCalcContainer.setOpaque(false);
      jcNorth.setOpaque(false);
      jcWest.setOpaque(false);
      jcEast.setOpaque(false);
      jcSouth.setOpaque(false);
      jcNorthWest.setOpaque(false);
      jcNorthEast.setOpaque(false);
      jcNorthCenter.setOpaque(false);
      jcSouthWest.setOpaque(false);
      jcSouthEast.setOpaque(false);
      jcSouthCenter.setOpaque(false);
      jcSouthSouth.setOpaque(false);
      radioAdd.setOpaque(false);
      radioSub.setOpaque(false);
      radioMul.setOpaque(false);
      radioDiv.setOpaque(false);
      //end of invisibility

      
      //finalizes window
      setVisible(true);
      setLocation(150,150);
      setSize(325,325);
      //end of finalizing
    }
  public static void main(String argv[])
    {
      JavaCalc JanetCalc = new JavaCalc();
      JanetCalc.addWindowListener(new WindowCloser());
    }
  
  private class ButtonHandler implements ActionListener{
    //listens to the button and tells what action is going to be performed
    public void actionPerformed( ActionEvent e )
      {
	//checks for empty denominators
	if (inDen1.getText() .equals(""))
	  {
	    JOptionPane.showMessageDialog(null, "ERROR!!! missing denominator in first input!! \n converting to a 1");
	    inDen1.setText("1");
	  }
	if (inDen2.getText() .equals(""))
	  {
	    JOptionPane.showMessageDialog(null, "ERROR!!! missing denominator in second input!! \n converting to a 1");
	  inDen2.setText("1");
	  }
	//checks for empty numerators
	if (inNum1.getText() .equals(""))
	  {
	    JOptionPane.showMessageDialog(null, "ERROR!!! missing numerator in first imput!! \n converting to a 1");
	    inNum1.setText("1");
	  }
	if(inNum2.getText() .equals(""))
	  {
	    JOptionPane.showMessageDialog(null, "ERROR!!! missing numerator in second input!! \n converting to a 1");
	    inNum2.setText("1");
	  }
	
	//sets up the rationals from the user inputs
	    Rational inputOne = new Rational(
					 Integer.parseInt(inNum1.getText()),
					 Integer.parseInt(inDen1.getText()));
	Rational inputTwo = new Rational(
					 Integer.parseInt(inNum2.getText()),
					 Integer.parseInt(inDen2.getText()));
	if(what == "addition")
	  {
	    //adds
	    inputOne.ratAdd(inputTwo.numerator,inputTwo.denominator);
	  }
	if(what == "subtraction")
	  {
	    //subtracts
	    inputOne.ratSub(inputTwo.numerator,inputTwo.denominator);
	  }
	if(what == "multiplication")
	  {
	    //multiplys
	    inputOne.ratMult(inputTwo.numerator,inputTwo.denominator);
	  }
	if(what == "division")
	  {
	    //divides
	    inputOne.ratDiv(inputTwo.numerator,inputTwo.denominator);
	  }
	//prints the answer
	inputOne.reduceRat(inputOne.numerator,inputOne.denominator);
	outNum.setText("" + inputOne.numerator);
	outDen.setText("" + inputOne.denominator);	
	
	//JOptionPane.showMessageDialog(null, "You pushed the calculate button with rational " + what + " selected");
      }
  }

  private class RadioHandler implements ItemListener {
    public void itemStateChanged( ItemEvent e )
      //decides what radio button is selsectd
      //changes the visible symbol
      //sets the what string for use with the button
      {
	if (e.getSource() == radioAdd)
	  {
	    whatLabel.setText("[ + ]");
	    what = "addition";
	  }
	else if (e.getSource() == radioSub)
	  {
	    whatLabel.setText("[ - ]");
	    what = "subtraction";
	  }
	else if (e.getSource() == radioMul)
	  {
	    whatLabel.setText("[ x ]");
	    what = "multiplication";
	  }
	else if (e.getSource() == radioDiv)
	  {
	    whatLabel.setText("[ / ]");
	    what = "division";
	  };
      }
  }

  private  class Rational{
    
    private int numerator,denominator;
    public Rational(int num, int den)
      {
	numerator = num;
	denominator = den;
      }
    public void ratAdd(int num, int den)
      {
	numerator = ( numerator * den + denominator * num );
	denominator = den * denominator;
      }
    public void ratSub(int num, int den)
      {
	numerator = ( numerator * den ) - (denominator * num );
	denominator = denominator * den;
      }
    public void ratMult(int num,int den)
      {
	numerator = num * numerator;
	denominator = den * denominator;	
      }
    public void ratDiv(int num, int den)
      {
	numerator = numerator * den;
	denominator = denominator * num;
      }
    public void reduceRat(int num,int den)
      //this reduces number
      {
	if ( num > den )
	  {	
	    for (double i = 1; i <= (num/2); i++)
	      {
		if((num%i==0) && (den%i==0))
		  {
		    //numerator
		    numerator  = (int)(num / i);
		    //denomionator
		    denominator = (int)(den / i);
		  }
	      }
	  }
	else if ( num < den )
	  {
	    for (double i = 1; i <= (den/2); i++)
	      {
		if((num%i==0) && (den%i==0))
		  {
		    //numerator
		    numerator = (int)(num / i);
		    //denominator
		    denominator = (int)(den / i);
		  }
	      }
	  }
	else if (num == den)
	  {
	    numerator = 1;
	    denominator = 1;
	  }
	parseRational(numerator,denominator);
      }
    public void parseRational(int numerator,int denominator)
      //parses for errors in input that could cause computation problems
      {
	if (denominator == 0)
	  {
	    JOptionPane.showMessageDialog(null, "ERROR!!! Divide by ZERO.  \n undefined answer converted to 1/0");
	    numerator = 1;
	    denominator = 0;
	  }
	else if (numerator == 0)
	  {
	    numerator = 0;
	    denominator = 0;
	  }
	else if (numerator == denominator)
	  {
	    numerator = 1;
	    denominator = 1;
	  }
      }
    public int getNumerator(Rational rational)
      //this gets the numerator
      {
	return rational.numerator;
      }
    public int getDenominator(Rational rational)
      //this gets the denominator
      {
	return rational.denominator;
      }
    public String toString()
      {
	//this reduces the number
	reduceRat(numerator,denominator);    
	return numerator + "/" + denominator;
      }
    //  public static void main(String args[])
    //    {
    //     Rational num = new Rational(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
    //     num.reduceRat(num.numerator,num.denominator);
    //     Rational pnum = new Rational(num.numerator,num.denominator);
    //     System.out.print(pnum.toString() + "\n");
    
    
  }
  class DrawPanel extends JPanel
  {
    Image im;
    DrawPanel(Image im)
      {
	this.im = im;
      }
    public void paintComponent(Graphics g)
      {
	super.paintComponent(g);
	if (im != null)
	  g.drawImage(im,0,0,this);
      }
  }
}




  

  
			      
