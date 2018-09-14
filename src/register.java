import javax.swing.*;
import com.mongodb.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class register {
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JPanel main;
    private JTextField txtName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton confirmButton;
    private JButton cancelButton;
    static MongoClientURI uri;
    static MongoClient mongo;
    static DB db;
    static DBCollection user;
    static DBObject checkName;
    static DBObject checkUsername;
    static String gender;

    public register() {
        maleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMaleRadioButton();
            }
        });
        femaleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFemaleRadioButton();

            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();

;
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetText();


            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        register from = new register();
        frame.setContentPane(from.main);
        from.connectDB();

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setVisible(true);

    }
    public  JPanel getMain(){return main;}
    public  void resetText(){
        txtName.setText("");
        txtUser.setText("");
        txtPassword.setText("");
    }
    public void connectDB(){
        try {
            uri = new MongoClientURI("mongodb://admin:88634159sd@ds245532.mlab.com:45532/ox");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("user");
        }catch (Exception e){

        }
    }
    public  void submit() {
        BasicDBObject searchQuery1 = new BasicDBObject();
        BasicDBObject searchQuery2 = new BasicDBObject();
        searchQuery1.put("username", txtUser.getText());
        searchQuery2.put("name", txtName.getText());
        checkUsername = user.findOne(searchQuery1);
        checkName = user.findOne(searchQuery2);
        if (txtName.getText().isEmpty() || txtPassword.getPassword().length == 0 || txtUser.getText().isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(null, "ใส่ข้อมูลให้ครบ");
        } else if (checkUsername != null) {
            JOptionPane.showMessageDialog(null, "Username ซ้ำ");
        } else if (checkName != null) {
            JOptionPane.showMessageDialog(null, "Name ซ้ำ");
        } else {
            BasicDBObject add = new BasicDBObject();
            add.put("name", txtName.getText());
            add.put("username", txtUser.getText());
            add.put("password", new String(txtPassword.getPassword()));
            if (maleRadioButton.isSelected()) {
                add.put("gender", "male");
            } else if (femaleRadioButton.isSelected()) {
                add.put("gender", "female");
            }
            user.insert(add);
            JOptionPane.showMessageDialog(null, "สัครสมาชิกสำเร็จ");
            resetText();

        }
    }
    public void setMaleRadioButton(){
        if(maleRadioButton.isSelected()){
            femaleRadioButton.setSelected(false);
            maleRadioButton.updateUI();
            gender="male";
        }
    }
    public void setFemaleRadioButton(){
        if(femaleRadioButton.isSelected()){
            maleRadioButton.setSelected(false);
            femaleRadioButton.updateUI();
            gender="female";
        }
    }



}
