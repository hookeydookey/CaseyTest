
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import javax.swing.SpringLayout;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

/**
 *
 * @author Martin
 */
public class CaseysTestResults2 extends Frame implements ActionListener, WindowListener, KeyListener
{

    /**
     * @param args the command line arguments
     */
    private int totalY;
    private int totalX;
    private TextField[][] fields;
    //private TextField[][] fields = new TextField[totalY][totalX];
    TextField txtSearchName;
    boolean namefound = false;
    private Button clear, save, close, sort, find, search, raf;
    private String textName = "Answers.csv";

    public static void main(String[] args)
    {
        CaseysTestResults2 frame = new CaseysTestResults2();
        frame.run();
    }

    private void run()
    {
        setBounds(100, 10, 1850, 860);
        setTitle("Java Assignment By Aaron Watters");
        this.addWindowListener(this);
        startLayout();
        setVisible(true);
    }

    private void startLayout()
    {
        csvread();

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        LocateTextFields(layout);
        startData(layout);

        close = new Button("Exit");
        add(close);
        close.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, close, 650, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, close, 690, SpringLayout.NORTH, this);

        clear = new Button("Clear");
        add(clear);
        clear.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, clear, 203, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, clear, 690, SpringLayout.NORTH, this);

        save = new Button("Save");
        add(save);
        save.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, save, 250, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, save, 690, SpringLayout.NORTH, this);

        sort = new Button("Sort");
        add(sort);
        sort.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, sort, 295, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sort, 690, SpringLayout.NORTH, this);

        find = new Button("Find");
        add(find);
        find.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, find, 335, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, find, 690, SpringLayout.NORTH, this);

        search = new Button("Search");
        add(search);
        search.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, search, 554, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, search, 690, SpringLayout.NORTH, this);

        raf = new Button("RAF");
        add(raf);
        raf.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, raf, 610, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, raf, 690, SpringLayout.NORTH, this);

        readFile(textName);
        CalculateCorrectAnswers();
        CalculateAverage();
        CalculateMode();
    }

    public void LocateTextFields(SpringLayout myTextFieldLayout)
    {
        txtSearchName = LocateATextField(myTextFieldLayout, txtSearchName, 15, 380, 690);

    }

    public TextField LocateATextField(SpringLayout myTextFieldLayout, TextField myTextField, int width, int x, int y)
    {
        myTextField = new TextField(width);
        add(myTextField);
        myTextFieldLayout.putConstraint(SpringLayout.WEST, myTextField, x, SpringLayout.WEST, this);
        myTextFieldLayout.putConstraint(SpringLayout.NORTH, myTextField, y, SpringLayout.NORTH, this);
        return myTextField;
    }

    public void searchName()
    {
        int y;
        int index = 0;
        namefound = false;
        for (y = 2; y < totalY - 1; y++)//telling it which column to check
        {
            if (fields[y][0].getText().equals(txtSearchName.getText()))   // checking if the column that contains the names equals the one typed into the search box
            {
                namefound = true;
                index = y;
            }
        }
        if (namefound == true)
        {
            fields[index][0].setBackground(new Color(0, 128, 255));// i want it to highlight the matching name in the column y,0
        }
    }

    public void csvread()
    {
        File file = new File("Answers.csv");
        String[][] TextField = new String[100][100];

        int row = 0;
        int col = 0;

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            //String e=reader.readLine();

            while ((line = reader.readLine()) != null && row < TextField.length)
            {
                String temp[] = line.split(",");
                col = temp.length;
                row++;
            }

            totalY = row + 2;
            totalX = col + 1;

            fields = new TextField[totalY][totalX];

            for (int y = 0; y < totalY; y++)
            {
                for (int x = 0; x < totalX; x++)
                {
                    fields[y][x].setText(TextField[y][x]);
                }
            }
        } catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage()); // print message on error
        }

    }

    private void startData(SpringLayout layout)
    {
        for (int y = 0; y < totalY; y++)
        {
            for (int x = 0; x < totalX; x++)
            {
                if (x == 0)
                {
                    fields[y][0] = new TextField(20);
                }

                fields[y][x] = new TextField(4);    

                if (y == 0)
                {
                    fields[y][x].setText(topLine(x));
                    fields[y][x].setEditable(true);
                }

                if (x == 0)
                {
                    //fields[y][x].setText(timeLine[y]);
                    fields[y][x].setEditable(true);
                }

                if ((x == totalX - 1) && y != 0 && y != 1 && y != totalY - 1)
                {
                    fields[y][x].setBackground(new Color(153, 255, 153));// color Green results column
                    fields[y][x].setEditable(true);
                }

                if ((x == 0) && y != 0 && y != 1 && y != totalY - 1)
                {
                    fields[y][x].setBackground(new Color(255, 255, 153));// color yellow names column
                    fields[y][x].setEditable(true);
                }

                if (y == 0)
                {
                    fields[y][x].setBackground(new Color(0, 128, 255));// color Blue
                    fields[y][x].setEditable(true);
                }

                if ((y == totalY - 1) && x != totalX - 1)
                {
                    fields[y][x].setBackground(new Color(153, 255, 153)); // color Green mode row
                    fields[y][x].setEditable(true);
                }

                if ((y == totalY - 1) && x == totalX - 1)
                {
                    fields[y][x].setBackground(new Color(255, 255, 153)); // color yellow average square
                    fields[y][x].setEditable(true);
                }

                add(fields[y][x]);
                fields[y][x].addKeyListener(this);
                layout.putConstraint(SpringLayout.WEST, fields[y][x], x * 50 + 20, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, fields[y][x], y * 20 + 10, SpringLayout.NORTH, this);
            }
        }

    }

    private String topLine(int line)
    {
        if (line == 0)
        {
            return "Qns:";
        }

        if (line == totalX - 1)
        {
            return "Result";
        }

        //if (line !=0 && line !=totalX)
        return "Q" + line;
    }

    private void clearData()
    {
        for (int y = 2; y < totalY; y++)
        {
            for (int x = 0; x < totalX; x++)
            {
                if (y == 0 || x == -1)
                {
                    continue;
                }
                fields[y][x].setText("");
            }
        }
    }

    private void CalculateMode()
    {
        for (int x = 1; x < totalX - 1; x++)
        {
            int[] modecount = new int[4];

            for (int y = 2; y < totalY - 1; y++)
            {
                switch (fields[y][x].getText())
                {
                    case "A":
                        modecount[0]++;
                        break;
                    case "B":
                        modecount[1]++;
                        break;
                    case "C":
                        modecount[2]++;
                        break;
                    case "D":
                        modecount[3]++;
                        break;
                }
            }

            int mode = 0;
            int current = 0;

            for (int i = 0; i < modecount.length; i++)
            {
                if (modecount[i] > current)
                {
                    current = modecount[i];
                    mode = i;
                }

                switch (mode)
                {
                    case 0:
                        fields[totalY - 1][x].setText("A");
                        break;
                    case 1:
                        fields[totalY - 1][x].setText("B");
                        break;
                    case 2:
                        fields[totalY - 1][x].setText("C");
                        break;
                    case 3:
                        fields[totalY - 1][x].setText("D");
                        break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == close)
        {
            System.exit(0);
        }
        if (e.getSource() == clear)
        {
            clearData();
        }

        if (e.getSource() == save)
        {
            writeFile(textName);
        }

        if (e.getSource() == sort)
        {
            SortNames();
        }

        if (e.getSource() == find)
        {

        }

        if (e.getSource() == search)
        {
            searchName();
        }

        if (e.getSource() == raf)
        {
            Random();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    public void SortNames()
    {
        String[][] Student = ConvertArray();
        System.out.println(Student.length);
        sortArray(Student);
        updateArray(Student);
    }

    private String[][] ConvertArray()
    {
        String[][] Student = new String[totalY - 1][totalX];
        for (int y = 2; y < totalY - 1; y++)
        {
            for (int x = 0; x < totalX; x++)
            {
                Student[y][x] = fields[y][x].getText();
            }
        }
        return Student;
    }
    private int count = 0;

    private void sortArray(String[][] data)
    {
        Arrays.sort(data, new Comparator<String[]>()
        {
            @Override
            public int compare(final String[] first, final String[] second)
            {
                count++;
                System.out.println(count);
                if (validate(first[0]) && validate(second[0]))
                {
                    final String element1 = first[0].toUpperCase();
                    final String element2 = second[0].toUpperCase();
                    return element1.compareTo(element2);
                } else
                {
                    return 0;
                }
            }
        });
    }

    private boolean validate(String val)
    {
        boolean result = false;
        try
        {
            switch (val)
            {
                case "Qns:":
                    result = false;
                    break;

                case "Answer":
                    result = false;
                    break;

                case "MODE:":
                    result = false;
                    break;

                default:
                    result = true;
                    break;
            }
            return result;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

    }

    private void updateArray(String[][] sort)
    {
        for (int y = 2; y < totalY - 1; y++)
        {
            for (int x = 0; x < totalX; x++)
            {
                fields[y][x].setText(sort[y][x]);
            }
        }
    }

    private void readFile(String fileName)
    {
        try
        {

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            for (int y = 0; y < totalY - 1; y++)
            {
                String temp[] = br.readLine().split(",");

                for (int x = 0; x < totalX - 1; x++)
                {
                    fields[y + 1][x + 0].setText(" " + temp[x]);
                    fields[totalY - 1][0].setText("Mode");

                }

            }
        } catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void writeFile(String fileName)
    {
        try
        {
            // Create file and start streams
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

            for (int y = 1; y < totalY - 1; y++)
            {
                for (int x = 0; x < totalX - 2; x++)

                {
                    out.write(fields[y][x].getText() + ",");
                }

                out.write(fields[y][totalX - 2].getText());
                out.newLine();
            }
            out.close(); // close stream
        } catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage()); // print message on error
        }
    }

    private void CalculateCorrectAnswers()
    {
        int count = 0;
        for (int y = 2; y < totalY - 1; y++)
        {

            for (int x = 1; x < totalX - 1; x++)
            {
                if (fields[1][x].getText().equals(fields[y][x].getText()))
                {
                    count++;
                }
            }
            fields[y][totalX - 1].setText(count + "");
            count = 0;
        }
    }

    private void CalculateAverage()
    {
        int students = totalY - 3;
        float total = 0;
        {
            for (int y = 2; y < totalY - 1; y++)
            {
                total += Integer.parseInt(fields[y][totalX - 1].getText());
            }
        }
        float avg = total / students;
        fields[totalY - 1][totalX - 1].setText(avg + "");

    }

    public void Random()
    {
        try
        {

            RandomAccessFile raf = new RandomAccessFile("books.txt", "rw");

            //
            // Let's write some book's title to the end of the file
            //
            String books[] = new String[5];
            books[0] = "Professional JSP";
            books[1] = "The Java Application Programming Interface";
            books[2] = "Java Security";
            books[3] = "Java Security Handbook";
            books[4] = "Hacking Exposed J2EE & Java";

            for (int i = 0; i < books.length; i++)
            {
                raf.writeUTF(books[i]);
            }

            //
            // Write another data at the end of the file.
            //
            raf.seek(raf.length());
            raf.writeUTF("Servlet & JSP Programming");

            //
            // Move the file pointer to the beginning of the file
            //
            raf.seek(0);

            //
            // While the file pointer is less than the file length, read the
            // next strings of data file from the current position of the
            // file pointer.
            //
            while (raf.getFilePointer() < raf.length())
            {
                System.out.println(raf.readUTF());
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }
}
